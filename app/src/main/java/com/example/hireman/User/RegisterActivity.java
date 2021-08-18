package com.example.hireman.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hireman.GetMobileNumber;
import com.example.hireman.OTP_Verification;
import com.example.hireman.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
EditText firstname;
    EditText lastname;
    EditText dob;
    EditText address;
    EditText mobile;
    String profileimage;
Button submit;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname=findViewById(R.id.first_name);
        lastname=findViewById(R.id.last_name);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        mobile=findViewById(R.id.mobile);
        submit=findViewById(R.id.submit);
        circleImageView=findViewById(R.id.profileimage);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //android.R.attr manifest;
                    if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]
                                {Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                    } else {

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(RegisterActivity.this);
                    }
                }

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstname.getText().toString().trim().isEmpty() && !lastname.getText().toString().trim().isEmpty() && !dob.getText().toString().trim().isEmpty() && !address.getText().toString().trim().isEmpty() && !mobile.getText().toString().trim().isEmpty())
                {
                    if((mobile.getText().toString().trim().length())==10)
                    {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobile.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                RegisterActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        /*progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);*/
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                       /* progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);*/
                                        Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        /*progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);*/
                                        Intent intent=new Intent(RegisterActivity.this, OTP_Verification.class);
                                        intent.putExtra("mobile",mobile.getText().toString());
                                        intent.putExtra("backendotp",s);
                                        intent.putExtra("firstname",firstname.getText().toString().trim());
                                        intent.putExtra("lastname",lastname.getText().toString().trim());
                                        intent.putExtra("dob",dob.getText().toString().trim());
                                        intent.putExtra("address",address.getText().toString().trim());
                                        intent.putExtra("profileimage",profileimage);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                        );
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Enter correct mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please full fill All Detail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri;
                resultUri = result.getUri();
                profileimage = resultUri.toString();
                circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}