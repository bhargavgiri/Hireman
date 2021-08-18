package com.example.hireman.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hireman.R;
import com.example.hireman.User.All_User_Activity;
import com.example.hireman.modelclass.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Alluser_Adapter extends RecyclerView.Adapter<Alluser_Adapter.ViewHolder>{
    private Context context;
    public List<UserData> list;
  //  ArrayList<WholesellerData> data1;
    String   tvContact,tvuserFistname,tvlastname;

    public Alluser_Adapter(Context context, List<UserData> list) {
        this.context = context;
        this.list = list;
        this.tvContact = tvContact;
        this.tvuserFistname = tvuserFistname;
        this.tvlastname = tvlastname;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.all_user_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Alluser_Adapter.ViewHolder holder, int position) {
        holder.tvuserFistname.setText(list.get(position).getFirstname());
        holder.tvlastname.setText(list.get(position).getLastname());
        holder.tvContact.setText(list.get(position).getMobile());

        Glide.with(context).load(list.get(position).getProfileimage()).into(holder.profile_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{

       CircleImageView profile_image;
       TextView tvContact,tvuserFistname,tvlastname;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.profile_image);

            tvuserFistname=itemView.findViewById(R.id.tvuserFistname);
            tvlastname=itemView.findViewById(R.id.tvlastname);
            tvlastname=itemView.findViewById(R.id.tvlastname);
            tvContact=itemView.findViewById(R.id.tvContact);
        }
    }
}
