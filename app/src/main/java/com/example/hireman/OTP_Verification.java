package com.example.hireman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hireman.Admin.AdminMainActivity;
import com.example.hireman.Staff.StaffMainActivity;
import com.example.hireman.modelclass.AllUserData;
import com.example.hireman.modelclass.UserData;
import com.example.hireman.modelclass.User_Driver_Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {
    EditText no1,no2,no3,no4,no5,no6;
    TextView textView;
    String getotpbackend;
    Button verifybuttonclick;
    TextView resendotp;
    String firstname,lastname,dob,address,mobile,profileimage;
    StorageReference storageReference;
    FirebaseFirestore firestore,firebaseFirestore,getFirestore;

    //Staff Reg
    String edphoneno,staffOtp,edname,edlastname,eddob,edaddrass,edStaffUid,edPassword,crimage,catogary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p__verification);

        final ProgressBar progressBarverifyotp=findViewById(R.id.progressbar_verify_otp);
        no1=findViewById(R.id.inputopt1);
        no2=findViewById(R.id.inputopt2);
        no3=findViewById(R.id.inputopt3);
        no4=findViewById(R.id.inputopt4);
        no5=findViewById(R.id.inputopt5);
        no6=findViewById(R.id.inputopt6);
        resendotp=findViewById(R.id.textresendotp);
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        getFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        verifybuttonclick=findViewById(R.id.otpverificationbutton);
        textView=findViewById(R.id.textmobileshownnumber);

        mobile="+91"+getIntent().getStringExtra("mobile");
        getotpbackend=getIntent().getStringExtra("backendotp");
        firstname=getIntent().getStringExtra("firstname");
        lastname=getIntent().getStringExtra("lastname");
        dob=getIntent().getStringExtra("dob");
        address=getIntent().getStringExtra("address");
        profileimage= getIntent().getStringExtra("profileimage");

        //Staff RegisterData
        edphoneno="+91"+getIntent().getStringExtra("edphoneno");
        staffOtp=getIntent().getStringExtra("staffOtp");
        edname=getIntent().getStringExtra("edname");
        edlastname=getIntent().getStringExtra("edlastname");
        eddob=getIntent().getStringExtra("eddob");
        edaddrass=getIntent().getStringExtra("edaddrass");
        edStaffUid=getIntent().getStringExtra("edStaffUid");
        edPassword=getIntent().getStringExtra("edPassword");
        crimage=getIntent().getStringExtra("crimage");
        catogary=getIntent().getStringExtra("catogary");



       //Toast.makeText(this,firstname+"  "+lastname+"  "+dob+"  "+address+"  "+mobile+"  "+profileimage+"  ", Toast.LENGTH_LONG).show();
        if (mobile==mobile)
        { textView.setText(mobile);
        }else if ( edphoneno== edphoneno)
        {
            textView.setText( edphoneno);
        }


        verifybuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!no1.getText().toString().trim().isEmpty() && !no2.getText().toString().trim().isEmpty() && !no3.getText().toString().trim().isEmpty() && !no4.getText().toString().trim().isEmpty() && !no5.getText().toString().trim().isEmpty() && !no6.getText().toString().trim().isEmpty() )
                {
                    String Entercodeotp = no1.getText().toString().trim() +
                            no2.getText().toString().trim() +
                            no3.getText().toString().trim() +
                            no4.getText().toString().trim() +
                            no5.getText().toString().trim() +
                            no6.getText().toString().trim();
                    if(firstname != null && lastname != null && dob !=null && address != null && mobile != null ) {

                        if (getotpbackend != null) {
                            progressBarverifyotp.setVisibility(View.VISIBLE);
                            verifybuttonclick.setVisibility(View.INVISIBLE);
                            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                    getotpbackend, Entercodeotp
                            );
                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                StorageReference Ref = storageReference.child(System.currentTimeMillis() + "." + getExtension(Uri.parse(profileimage)));
                                                Ref.putFile(Uri.parse(profileimage)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                String uid = FirebaseAuth.getInstance().getUid();
                                                                //Toast.makeText(OTP_Verification.this,uid, Toast.LENGTH_SHORT).show();
                                                                UserData userData = new UserData(firstname, lastname, dob, address, mobile, uri.toString(),uid);
                                                                firestore.collection("User").document(uid).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        AllUserData allUserData = new AllUserData(uid, "user");
                                                                        firebaseFirestore.collection("AllUser").document(uid).set(allUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                progressBarverifyotp.setVisibility(View.GONE);
                                                                                verifybuttonclick.setVisibility(View.VISIBLE);
                                                                                Toast.makeText(OTP_Verification.this, "DONE.....", Toast.LENGTH_SHORT).show();
                                                                                Intent intent = new Intent(OTP_Verification.this, MainActivity.class);
                                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                startActivity(intent);
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(OTP_Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        });


                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            /*Intent intent=new Intent(OTP_Verification.this,MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);*/
                                            } else {
                                                Toast.makeText(OTP_Verification.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(OTP_Verification.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                        }
                        // Toast.makeText(VerifyOtp.this, "otp verify", Toast.LENGTH_SHORT).show();
                        //otp number
                    }else if(edphoneno != null && staffOtp != null && edname != null && edlastname != null && eddob != null && edaddrass != null && edStaffUid != null && edPassword != null && crimage != null &&  catogary !=null)

                    {
                        if (staffOtp != null)
                        {
                            progressBarverifyotp.setVisibility(View.VISIBLE);
                            verifybuttonclick.setVisibility(View.INVISIBLE);
                            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                    staffOtp, Entercodeotp
                            );
                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        staffRegisterandDataStore(edphoneno,edname,edlastname,eddob,edaddrass,edStaffUid,edPassword,crimage,catogary);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(OTP_Verification.this, "Task Fell not sing in ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else if (mobile != null && getotpbackend != null)
                    {
                        progressBarverifyotp.setVisibility(View.VISIBLE);
                        verifybuttonclick.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, Entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            String uid1=FirebaseAuth.getInstance().getUid();
                                            getFirestore.collection("AllUser").document(uid1).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if(documentSnapshot.exists())
                                                    {
                                                        String unic=FirebaseAuth.getInstance().getUid();
                                                        String uid2=documentSnapshot.getString("uid");
                                                        String catogary=documentSnapshot.getString("category");
                                                       Toast.makeText(OTP_Verification.this,uid2+"   "+catogary+"     "+unic+" ", Toast.LENGTH_SHORT).show();
                                                        if(unic.equals(uid2)) {


                                                            if (catogary.equals("user")) {
                                                                Toast.makeText(OTP_Verification.this, "user sucessfully login", Toast.LENGTH_LONG).show();
                                                                progressBarverifyotp.setVisibility(View.GONE);
                                                                verifybuttonclick.setVisibility(View.VISIBLE);
                                                                Toast.makeText(OTP_Verification.this, "DONE.....", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(OTP_Verification.this, MainActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(OTP_Verification.this, "catogary not match", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        else {
                                                            Toast.makeText(OTP_Verification.this, "uid not match", Toast.LENGTH_SHORT).show();
                                                        }



                                                    }
                                                    else {
                                                        Toast.makeText(OTP_Verification.this, "Data not Found", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(OTP_Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                           /* progressBarverifyotp.setVisibility(View.GONE);
                                            verifybuttonclick.setVisibility(View.VISIBLE);
                                            Toast.makeText(OTP_Verification.this, "Login sussecfully.....", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(OTP_Verification.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);*/
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(OTP_Verification.this, "Some thing want wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    }else {
                    Toast.makeText(OTP_Verification.this, "please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberotpmove();
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" +getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OTP_Verification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTP_Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s1, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend=s1;
                                Toast.makeText(OTP_Verification.this, "OTP Sended succussfully...", Toast.LENGTH_SHORT).show();


                            }
                        }
                );
            }
        });


    }

    private void staffRegisterandDataStore(String edphoneno, String edname, String edlastname, String eddob, String edaddrass, String edStaffUid, String edPassword, String crimage, String catogary) {
        FirebaseFirestore firebaseFirestore1= FirebaseFirestore.getInstance();
        FirebaseFirestore firebaseFirestore2=FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference Ref1 = storageReference.child(System.currentTimeMillis() + "." + getExtension(Uri.parse(this.crimage)));
        Ref1.putFile(Uri.parse(crimage)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Ref1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       User_Driver_Data staffData=new User_Driver_Data(edname,edlastname,edaddrass,edphoneno,uri.toString(),FirebaseAuth.getInstance().getUid(),edStaffUid,catogary,edPassword,eddob);
                       firebaseFirestore1.collection(catogary).document(FirebaseAuth.getInstance().getUid()).set(staffData).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               AllUserData allUserData = new AllUserData(FirebaseAuth.getInstance().getUid(), "staff");
                               firebaseFirestore2.collection("AllUser").document(FirebaseAuth.getInstance().getUid()).set(allUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {

                                       Toast.makeText(OTP_Verification.this, "DONE.....", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(OTP_Verification.this, AdminMainActivity.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);

                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull @NotNull Exception e) {
                                       Toast.makeText(OTP_Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull @NotNull Exception e) {
                               Toast.makeText(OTP_Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull @NotNull Exception e) {
                       Toast.makeText(OTP_Verification.this, "image is not Download"+e, Toast.LENGTH_SHORT).show();
                   }
               });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(OTP_Verification.this, "image Fell not Store ", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void numberotpmove() {
        no1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    no2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        no2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    no3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        no3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    no4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        no4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    no5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        no5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    no6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

}