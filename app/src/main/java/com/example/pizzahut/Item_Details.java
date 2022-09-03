package com.example.pizzahut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item_Details extends AppCompatActivity {

    Button Add_Btn, Min_Btn, Share_btn ,Save_btn;
    ImageView imageView, Delete_Btn;
    TextView Total_Price, Total_Count, Item_Name, Item_Price, Item_Description;

    MediaPlayer click;
    private Database_Access DBA ;

    public static String Input_Item_Name, Input_Item_Date, Input_Item_Price , UserName, Sending_Text;

    private int Item_Id =-1;
    int sum=0;

    public static SharedPreferences SP ;
    public static SharedPreferences.Editor EDIT ;
    public static final String PREF_NAME = "RegisterPrefrences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        Total_Price = findViewById(R.id.Item_details_Total_Num_Tv);
        Total_Count = findViewById(R.id.Item_details_Count_Tv);
        Save_btn = findViewById(R.id.Item_details_Save_btn);
        Add_Btn = findViewById(R.id.Item_details_Add_btn);
        Min_Btn = findViewById(R.id.Item_details_Min_btn);
        Item_Name = findViewById(R.id.foodname);
        Item_Price = findViewById(R.id.Item_details_Price_Num_Tv);
        Item_Description = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);
        Delete_Btn = findViewById(R.id.Item_details_Delete_btn);
        Share_btn = findViewById(R.id.Item_details_Share_btn);

        SP = getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        EDIT = SP.edit();

        if(SP.getBoolean(signup.AdminK, false)){ Delete_Btn.setVisibility(View.VISIBLE); } else{ Delete_Btn.setVisibility(View.GONE); }


        DBA = Database_Access.getInstance(this);

        Intent intent = getIntent();
        Item_Id = intent.getIntExtra(Main_Posts_Page.Item_KEY, Item_Id);

        DBA.open();
        // get car info where id = * .
        Post p = DBA.getPost(Item_Id);
        DBA.close();

        if (p != null ){
            fillfoodToFields(p);
        }




        Add_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();
                sum++;
                Total_Count.setText(sum + "");
                Total_Price.setText(totalprice()+ "");
            }
        });

        Min_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();
                sum--;
                Total_Count.setText(sum + "");
                Total_Price.setText(totalprice()+ "");

            }
        });
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();

                UserName = SP.getString(signup.UserNameKey,"");
                Input_Item_Name = Item_Name.getText().toString();
                Input_Item_Price = Total_Price.getText().toString();
                Date now = new Date();
                SimpleDateFormat Time = new SimpleDateFormat("YYYY-MM-dd   hh:mm:ss");
                String TimeNow = Time.format(now);
                Input_Item_Date =TimeNow;

                DBA.open();
                // insert data to item :
                Purchase_post pp= new Purchase_post(UserName, Input_Item_Name, Input_Item_Date, Input_Item_Price);
                Boolean result = DBA.insertpurchases(pp);

                // if process done successfully
                if (result) {
                    Toast.makeText(getBaseContext(), "item Added successfully ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(), "item NOT Added successfully ", Toast.LENGTH_SHORT).show(); }

                DBA.close();
            }
        });


        Delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBA.open();
                // delete process only needs ID so other values isn't necessary
                Post p = new Post(Item_Id , null , null , 0 , null , null);
                // delete process
                Boolean result = DBA.DeleteItemPost(p) ;
                if (result){
                    Toast.makeText(getBaseContext(),"Item deleted successfully From DataBase ",Toast.LENGTH_SHORT).show();
                    setResult(Main_Posts_Page.SHOW_ITEM_REQ_CODE,null);   // null = no data to get back
                    finish(); }

                DBA.close();
            }
        });

        Share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sending_Text = "Check out " + p.getItemName() + " meal , it's only for " + p.getPrice()+" $ from Pizza Hut APP " ;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, Sending_Text);
                startActivity(Intent.createChooser(shareIntent, "Send To"));
            }
        });
    }



    private void fillfoodToFields (Post p){
        if(p.getImage() != null && !p.getImage().isEmpty()){
            imageView.setImageURI(Uri.parse(p.getImage()));
        }
        Item_Name.setText(p.getItemName());
        Item_Description.setText(p.getDescription());
        Item_Price.setText(p.getPrice()+"");
    }

    public int totalprice(){
        int Price = Integer.parseInt(Item_Price.getText().toString());
        int Count = Integer.parseInt(Total_Count.getText().toString());
        int Total = Count*Price;
        return Total;
    }

}