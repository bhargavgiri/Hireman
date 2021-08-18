package com.example.hireman.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hireman.Adaper.Alluser_Adapter;
import com.example.hireman.Adaper.User_Driver_Adapter;
import com.example.hireman.R;
import com.example.hireman.modelclass.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class All_User_Activity extends AppCompatActivity {





    ArrayList<UserData> list;
    FirebaseFirestore f_store;
    RecyclerView rvView;
    Alluser_Adapter alluserAdaptor;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        rvView= findViewById(R.id.rvView);
        auth= FirebaseAuth.getInstance();
        list=new ArrayList<>();
        alluserAdaptor=new Alluser_Adapter(All_User_Activity.this,list);
        f_store=FirebaseFirestore.getInstance();
        f_store.collection("User").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> D_list=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d:D_list) {


                        UserData e=d.toObject(UserData.class);
                        list.add(e);
                    }
                    rvView.setAdapter(alluserAdaptor);
                    rvView.setLayoutManager(new LinearLayoutManager(All_User_Activity.this));
                    Toast.makeText(All_User_Activity.this, "Data set...", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(All_User_Activity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(All_User_Activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}