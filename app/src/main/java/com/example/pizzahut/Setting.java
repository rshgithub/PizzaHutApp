package com.example.pizzahut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Setting extends AppCompatActivity {
    Button showallpurchase,showlastpurchase,changepassword,clearallpurchase,addnewItem;
    private int Id = -1 ;
    private Database_Access DBA ;

    MediaPlayer click;

    public static SharedPreferences SP ;
    public static SharedPreferences.Editor EDIT ;
    public static final String PREF_NAME = "RegisterPrefrences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        showallpurchase=findViewById(R.id.showallpurchase);
        showlastpurchase=findViewById(R.id.showlastpurchase);
        changepassword=findViewById(R.id.changepassword);
        clearallpurchase=findViewById(R.id.clearallpurchase);
        addnewItem=findViewById(R.id.addnewItem);


        SP = getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        EDIT = SP.edit();

        DBA = Database_Access.getInstance(this);

        String userName = SP.getString(signup.UserNameKey,"");
        Toast.makeText(getBaseContext(), "welcome  " + userName, Toast.LENGTH_LONG).show();

        // if user is Admin :
        if(SP.getBoolean(signup.AdminK, false)){ addnewItem.setVisibility(View.VISIBLE); } else{ addnewItem.setVisibility(View.GONE); }

        showallpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();

                if (Purchase_List.ppost.isEmpty()){
                    Toast.makeText(getBaseContext(), "there is no item in the list", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(getBaseContext(), Purchase_List.class);
                    startActivity(intent);
                }
            }
        });

        showlastpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();

                if (Purchase_List.ppost.isEmpty()){
                    Toast.makeText(getBaseContext(), "there is no item in the list", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(getBaseContext(), Last_Purchases.class);
                    startActivity(intent);
                }

            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();
                Intent intent = new Intent(getBaseContext(), Change_Password.class);
                startActivity(intent);
            }
        });


        clearallpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            click = MediaPlayer.create(getBaseContext(), R.raw.click);
            click.start();

                if (Purchase_List.ppost.isEmpty()){
                    Toast.makeText(getBaseContext(), "there is no item in the list", Toast.LENGTH_LONG).show();

                }else {
                    DBA.open();
                    DBA.ClearAllPurchases();
                    DBA.close();
                    Toast.makeText(getBaseContext(), "deleted all item", Toast.LENGTH_LONG).show();
                }

            }
        });

        addnewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();
                Intent intent = new Intent(getBaseContext(), Add_Item.class);
                startActivity(intent);

            }

        });

    }

 }
