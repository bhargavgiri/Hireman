package com.example.hireman.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hireman.R;
import com.example.hireman.Staff.StaffRegisterActivity;
import com.example.hireman.User.All_User_Activity;

public class AdminMainActivity extends AppCompatActivity {
    androidx.cardview.widget.CardView cvaddstaff,cvstaffHistory,cvallusers,cvuserHistory,cvallstaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        cvaddstaff=findViewById(R.id.addstaff);
        cvstaffHistory=findViewById(R.id.staffHistory);
        cvallusers=findViewById(R.id.allusers);
        cvuserHistory=findViewById(R.id.userHistory);
        cvallstaff=findViewById(R.id.allstaff);

        cvaddstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMainActivity.this, StaffRegisterActivity.class);
                startActivity(i);
            }
        });

        cvallusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminMainActivity.this, All_User_Activity.class);
                startActivity(i);

            }
        });
        
    }
}