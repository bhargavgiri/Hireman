package com.example.hireman.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hireman.R;

public class User_Send_RequestActivity extends AppCompatActivity {
String firstname,lastname,dob,mobile,address,profileimage,uid;
    de.hdodenhof.circleimageview.CircleImageView image;
    TextView firstname1,lastname1,dob1,mobile1,address1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__send__request);
        firstname=getIntent().getStringExtra("firstname");
        lastname=getIntent().getStringExtra("lastname");
        dob=getIntent().getStringExtra("dob");
        mobile=getIntent().getStringExtra("mobile");
        address=getIntent().getStringExtra("address");
        profileimage=getIntent().getStringExtra("profileimage");
        uid=getIntent().getStringExtra("uid");
        image=findViewById(R.id.profileimage);
        firstname1=findViewById(R.id.firstname);
        lastname1=findViewById(R.id.lastname);
        dob1=findViewById(R.id.dob);
        mobile1=findViewById(R.id.mobile);
        address1=findViewById(R.id.address);


        firstname1.setText(firstname);
        lastname1.setText(lastname);
        dob1.setText(dob);
        mobile1.setText(mobile);
        address1.setText(address);
        Toast.makeText(User_Send_RequestActivity.this,firstname+"\n"+lastname+"\n"+dob+"\n"+mobile+"\n"+address+"\n"+profileimage+"\n"+uid, Toast.LENGTH_LONG).show();
        Glide.with(User_Send_RequestActivity.this).load(profileimage).into(image);
    }
}