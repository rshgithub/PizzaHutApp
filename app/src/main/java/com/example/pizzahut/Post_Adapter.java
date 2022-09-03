package com.example.pizzahut;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.postviewholder>
{
    private onRecyclerViewItemClickListener listener ;
    private ArrayList<Post> posts ;

    // public ArrayList<Post> getPosts() {return posts;}

    // set after search post
    public void setPosts(ArrayList<Post> posts) {
        this.posts=posts;
    }

    // constructor : عشان اقدر ابعت للاون بايند الاراي للشاشة
    public Post_Adapter(ArrayList<Post> posts, onRecyclerViewItemClickListener listener) {
        this.posts = posts ;
        this.listener=listener;
    }

//يتم استدعائها مرة واحدة لكل مرة بعرض صنف عالشاشة وبترجع بكل مرة فيو هولدر
    @NonNull
    @Override
    public postviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_activty,parent,false);
        // بننشاو عشان ابعتلو التصميم كامل
        postviewholder postviewholder= new postviewholder(v);
        return postviewholder;
    }
    //بترجعش اشي بتاخد الهولدر والبوزيشن
    //يتم استدعائها اما اول مرة بعد الاون كريت
    //بركب بجيب العنصر الي بالفيو هولدر وبحطو بالبوزيشن المحدد
    @Override
    public void onBindViewHolder(@NonNull postviewholder holder, int position) {
        //اوبجكت من الكلاس
        Post p =posts.get(position);
        if(p.getItemName() != null && !p.getItemName().isEmpty()){ holder.tv_ItemName.setText(p.getItemName()); }
        if(p.getPrice() != 0){ holder.tv_price.setText(String.valueOf(p.getPrice())); }
        if(p.getPayment() != null && !p.getPayment().isEmpty()){ holder.tv_payment.setText(p.getPayment()); }
        //بجيب الصورة الهولدر وبركب الصورة هاي بدلها
        if(p.getImage() != null && !p.getImage().isEmpty()){ holder.image.setImageURI(Uri.parse(p.getImage())); }
        else{ holder.image.setImageResource(R.drawable.ic_pizza_5_); }

        holder.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Sending_Text = "Check out " + p.getItemName() + " meal , it's only for " + p.getPrice()+" $ from Pizza Hut APP " ;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, Sending_Text);
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Send To"));

                //               to share text and image together

//                Uri imageUri = Uri.parse(p.getImage());
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, Sending_Text);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                shareIntent.setType("image/jpeg");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                view.getContext().startActivity(Intent.createChooser(shareIntent, "Send To"));


            }
        });

    //تابع لليسينر
        holder.id= p.getId();

    }

    //عدد عناصر المصفوفة
    @Override
    public int getItemCount() {
        return posts.size();
    }



    //holder class
    class postviewholder extends RecyclerView.ViewHolder {

    TextView tv_ItemName  , tv_payment , tv_price;
    ImageView image ;
    int id;
        Button share_btn ;

//    String NameText = tv_ItemName.getText().toString();


        // هان بستقبل فيو وببعتو للاب وبعمل انفلات للعناصر
        public postviewholder(View itemView) {
            super(itemView);
            // itemView الموجودة ب اون كريت
            tv_ItemName = itemView.findViewById(R.id.itemname);
            tv_payment = itemView.findViewById(R.id.payment);
            tv_price = itemView.findViewById(R.id.Item_details_Price_Num_Tv);
            image = itemView.findViewById(R.id.ItemImage);
            share_btn = itemView.findViewById(R.id.Item_details_Share_btn);


            //لما يضفط علالايتم
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // when image is clicked :999999
                    listener.onItemClick(id);
                }
            });
        }
    }
}