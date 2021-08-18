package com.example.hireman.Adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hireman.R;
import com.example.hireman.User.User_Send_RequestActivity;
import com.example.hireman.modelclass.UserData;
import com.example.hireman.modelclass.User_Driver_Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Driver_Adapter extends RecyclerView.Adapter<User_Driver_Adapter.ViewHolder> implements Filterable {

    private Context context;
    public List<UserData> list;
    ArrayList<UserData> data1;
    String s_name,so_name,contact,image;
    public static String W_token;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth;
    String userId;

public User_Driver_Adapter(Context context,ArrayList<UserData> list){
this.context=context;
this.list=list;
data1=new ArrayList<>(list);
}

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        // background Tread work
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            List<UserData> filter_data=new ArrayList<>();

            if (keyword.toString().isEmpty())
            {
                filter_data.addAll(data1);
            }else
            {
                /*for (WholesellerData W : data1)
                {
                    if (W.Shope_name.toString().toLowerCase().contains(keyword.toString().toLowerCase().trim()))
                    {
                        filter_data.add(W);
                    }
                }*/
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filter_data;
            return filterResults;
        }

        @Override // main Ui Tread
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList<UserData>)results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.user_driver_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       UserData userData=list.get(position);
        holder.firstname1.setText(userData.getFirstname());
        holder.lastname1.setText(userData.getLastname());
        holder.contact1.setText(userData.getMobile());
        Glide.with(context).load(userData.getProfileimage()).into(holder.image);
        auth = FirebaseAuth.getInstance();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, User_Send_RequestActivity.class);
                intent.putExtra("firstname",userData.getFirstname());
                intent.putExtra("lastname",userData.getLastname());
                intent.putExtra("mobile",userData.getMobile());
                intent.putExtra("dob",userData.getDob());
                intent.putExtra("address",userData.getAddress());
                intent.putExtra("profileimage",userData.getProfileimage());
                intent.putExtra("uid",userData.getUid());
                context.startActivity(intent);
                Toast.makeText(context,userData.getUid(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        List<UserData> list;
        TextView firstname1,lastname1,contact1;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname1=itemView.findViewById(R.id.firstname1);
            lastname1=itemView.findViewById(R.id.lastname1);
            contact1=itemView.findViewById(R.id.Contact1);
            image=itemView.findViewById(R.id.profile_image);
        }
    }
}
