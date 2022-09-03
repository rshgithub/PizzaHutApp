package com.example.pizzahut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Purchase_Adapter extends RecyclerView.Adapter<Purchase_Adapter.postviewholder> {

    private ArrayList<Purchase_post> Purchase_post ;

    public Purchase_Adapter(ArrayList<Purchase_post> ppost) {
        Purchase_post = ppost;
    }

    public ArrayList<Purchase_post> getPurchase_post() {
        return Purchase_post;
    }

    public void setPurchase_post(ArrayList<Purchase_post> purchase_post) {
        Purchase_post = purchase_post;
    }

    @NonNull
    @Override
    public postviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_post,parent,false);
        postviewholder postviewholder= new postviewholder(v);
        return postviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull postviewholder holder, int position) {
        Purchase_post Currentitem =Purchase_post.get(position);
        // خد id الصورة وضيفها على الصورة getMimageview
        holder.date.setText(Currentitem.getDate());
        holder.itemname.setText(Currentitem.getItemName());
        holder.price.setText(Currentitem.getPrice());
    }

    @Override
    public int getItemCount() {
        return Purchase_post.size();
    }


    class postviewholder extends RecyclerView.ViewHolder {
        TextView itemname,date,price;


        //هان ببعت بفيو تعتنا
        public postviewholder(View itemView) {
            super(itemView);
            // itemView الموجودة ب اون كريت
            itemname = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.Item_details_Price_Num_Tv);
            date = itemView.findViewById(R.id.dateandtime);

        }


    }

}
