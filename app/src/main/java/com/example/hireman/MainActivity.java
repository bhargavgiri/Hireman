package com.example.hireman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hireman.User.User_DriverActivity;
import com.example.hireman.User.User_HospitalityActivity;
import com.example.hireman.User.User_HouseKeeperActivity;
import com.example.hireman.User.User_OtherActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    androidx.cardview.widget.CardView driver,hospitality,housekeeper,other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hospitality=findViewById(R.id.hospitality);
        housekeeper=findViewById(R.id.housekeeper);
        other=findViewById(R.id.other);
        driver=findViewById(R.id.driver);
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this, User_DriverActivity.class);
                startActivity(i1);
            }
        });
        hospitality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this,User_HospitalityActivity.class);
                startActivity(i1);
            }
        });
        housekeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this, User_HouseKeeperActivity.class);
                startActivity(i1);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this, User_OtherActivity.class);
                startActivity(i1);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            Intent intent=new Intent(getApplicationContext(),GetMobileNumber.class);
            startActivity(intent);
            finish();
        }
    }
}