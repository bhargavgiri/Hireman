package com.example.hireman.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.hireman.OTP_Verification;
import com.example.hireman.R;
import com.example.hireman.User.RegisterActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView crimage;
    EditText edname,edlastname,eddob,edphoneno,edaddrass,edPrice,edStaffUid,edPassword;
    Button btncreate;
    Spinner splist;
    String catogary[]={"Select catogory","Hospitality","House Keeper","Driver","Other"};
    String profileimage;
    String catogaryselect;
    AwesomeValidation validation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);

        crimage=findViewById(R.id.crimage);
        edname=findViewById(R.id.edname);
        edlastname=findViewById(R.id.edlastname);
        eddob=findViewById(R.id.eddob);
        edphoneno=findViewById(R.id.edphoneno);
        edaddrass=findViewById(R.id.edaddrass);
        edStaffUid=findViewById(R.id.edStaffUid);
        edPassword=findViewById(R.id.edPassword);
        btncreate=findViewById(R.id.btncreate);
        edPrice=findViewById(R.id.edPrice);

        crimage.setOnClickListener(this);
        btncreate.setOnClickListener(this);

        splist=findViewById(R.id.splist);
        ArrayAdapter<String> ad=new ArrayAdapter<String>(StaffRegisterActivity.this,R.layout.support_simple_spinner_dropdown_item,catogary);
        ad.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        splist.setAdapter(ad);

        //validation
        //intilalize

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this, R.id.edname, RegexTemplate.NOT_EMPTY, R.string.EnterName);
        validation.addValidation(this, R.id.edlastname, RegexTemplate.NOT_EMPTY, R.string.EnterlastName);
        validation.addValidation(this, R.id.eddob, RegexTemplate.NOT_EMPTY, R.string.EnterBirthDate);
        validation.addValidation(this, R.id.edphoneno, "[5-9]{1}[0-9]{9}$", R.string.EnterMobileno);
        validation.addValidation(this, R.id.edaddrass, RegexTemplate.NOT_EMPTY, R.string.EnterAddress);
        validation.addValidation(this, R.id.edStaffUid, RegexTemplate.NOT_EMPTY, R.string.Enterstaffid);
        validation.addValidation(this, R.id.edPassword, RegexTemplate.NOT_EMPTY, R.string.EnterPassword);
        validation.addValidation(this, R.id. splist, RegexTemplate.NOT_EMPTY, R.string.EnterStaffType);
        validation.addValidation(this,R.id.edPrice,RegexTemplate.NOT_EMPTY,R.string.EnterStaffPirce);

        splist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    catogaryselect="select";

                }else if(i==1){
                    catogaryselect="Hospitality";
                    Toast.makeText(StaffRegisterActivity.this,"hospitality", Toast.LENGTH_SHORT).show();
                }else if(i==2){
                    catogaryselect="Housekeeper";

                }else if(i==3){
                    catogaryselect="Driver";

                }else if(i==4){
                    catogaryselect="Other";
                    Toast.makeText(StaffRegisterActivity.this, "OTHER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.crimage:
                       imageuplod();
                break;
            case R.id.btncreate:
                    createstaff();
                break;
        }
    }

    private void createstaff() {
        if (validation.validate())
        {
            if ((edphoneno.getText().toString().trim().length())==10)
            {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + edphoneno.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        StaffRegisterActivity.this,
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
                                Toast.makeText(StaffRegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        /*progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);*/
                               // Toast.makeText(StaffRegisterActivity.this,s, Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(StaffRegisterActivity.this,OTP_Verification.class);
                                intent.putExtra("edphoneno",edphoneno.getText().toString());
                                intent.putExtra("staffOtp",s);
                                intent.putExtra("edname",edname.getText().toString().trim());
                                intent.putExtra("edlastname",edlastname.getText().toString().trim());
                                intent.putExtra("eddob",eddob.getText().toString().trim());
                                intent.putExtra("edaddrass",edaddrass.getText().toString().trim());
                                intent.putExtra("edStaffUid",edStaffUid.getText().toString().trim());
                                intent.putExtra("edPassword",edPassword.getText().toString().trim());
                                intent.putExtra("crimage",profileimage);
                                intent.putExtra("catogary",catogaryselect);
                                intent.putExtra("edPrice",edPrice.getText().toString());
                                startActivity(intent);

                            }
                        }
                );
            }else {
                Toast.makeText(StaffRegisterActivity.this, "Enter correct mobile number", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Full fell Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void imageuplod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //android.R.attr manifest;
            if (ContextCompat.checkSelfPermission(StaffRegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StaffRegisterActivity.this, new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            } else {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(StaffRegisterActivity.this);
            }
        }
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
                crimage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}