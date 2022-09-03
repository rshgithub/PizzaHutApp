package com.example.pizzahut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Contact_Us extends AppCompatActivity {
    Button btnCall , btnFaceBook  , btnٍWebsite , btnٍGmail , btnBack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        btnCall = findViewById(R.id.btn_call);
        btnFaceBook = findViewById(R.id.btn_facebook);
        btnٍWebsite = findViewById(R.id.btn_website);
        btnٍGmail = findViewById(R.id.btn_gmail);
        btnBack = findViewById(R.id.btn_back);

        // implicit intents :

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CallNum = new Intent(Intent.ACTION_DIAL);
                CallNum.setData(Uri.parse("tel:0568711400"));
                if (CallNum.resolveActivity(getPackageManager()) != null) {
                    startActivity(CallNum);}
                else{  Toast.makeText(getBaseContext(), "No application supports this service", Toast.LENGTH_LONG).show();  }

            }
        });

        btnFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fc = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/aya.enshaey"));
                if (fc.resolveActivity(getPackageManager()) != null) {
                    startActivity(fc);}
                else{  Toast.makeText(getBaseContext(), "No application supports this service", Toast.LENGTH_LONG).show();  }


            }
        });

        btnٍWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UcasSite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://newucas.ucas.edu.ps/"));
                if (UcasSite.resolveActivity(getPackageManager()) != null) {
                    startActivity(UcasSite);}

                else{  Toast.makeText(getBaseContext(), "No application supports this service", Toast.LENGTH_LONG).show();  }
                }
        });


        btnٍGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent email = new Intent(Intent.ACTION_SENDTO , Uri.parse("mailto:" + "https://mail.google.com/mail/u/0/#inbox"));
                email.putExtra(Intent.EXTRA_SUBJECT, "subject : message to application developers ");
                email.putExtra(Intent.EXTRA_TEXT, "text");
                email.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "rshurbaji@smail.ucas.edu.ps" });

                // if app that supports this servies don't exsist
                if (email.resolveActivity(getPackageManager()) != null){
                startActivity(Intent.createChooser(email, "Choose an Email  :"));}
                else {  Toast.makeText(getBaseContext(),"No application supports this service",Toast.LENGTH_LONG).show(); }


        }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getBaseContext(),"Back",Toast.LENGTH_LONG).show(); }});


    }
}