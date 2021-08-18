package com.example.hireman.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

public class User_OtherActivity extends AppCompatActivity {
    ArrayList<UserData> list;
    FirebaseFirestore f_store;

    User_Driver_Adapter W_ilst1;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__other);
        RecyclerView r_view1 = findViewById(R.id.recycler_view3);
        auth= FirebaseAuth.getInstance();
        list=new ArrayList<>();

        W_ilst1=new User_Driver_Adapter(User_OtherActivity.this,list);
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
                        r_view1.setAdapter(W_ilst1);
                        r_view1.setLayoutManager(new LinearLayoutManager(User_OtherActivity.this));

                    }
                    Toast.makeText(User_OtherActivity.this, "Data set...", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(User_OtherActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_OtherActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}