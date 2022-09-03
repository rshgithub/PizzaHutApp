package com.example.pizzahut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Sign_In extends AppCompatActivity {
    public static EditText Email_et, Password_et;
    Button Login_btn, Register_btn;
    CheckBox Remember_chbox;
    MediaPlayer click;

    public static SharedPreferences SP ;
    public static SharedPreferences.Editor EDIT ;
    public static final String PREF_NAME = "RegisterPrefrences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email_et = findViewById(R.id.SiginIn_et_Email);
        Password_et = findViewById(R.id.SiginIn_et_Password);
        Login_btn = findViewById(R.id.SiginIn_btn_Signin);
        Register_btn = findViewById(R.id.SiginIn_btn_Register);
        Remember_chbox = findViewById(R.id.SiginIn_Remember_chbox);


        SP = getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        EDIT = SP.edit();


        // استقبال البيانات
        if(getIntent() != null){
            String EML = getIntent().getStringExtra(signup.EMAIL_INTENT_KEY);
            String PAS = getIntent().getStringExtra(signup.PASS_INTENT_KEY);
            // if there's no retrieved data from sign up activity :
            if( EML != null && PAS != null ){
                Email_et.setText(EML);
                Password_et.setText(PAS);}
        }else{
            Toast.makeText(getBaseContext(), "No retrieved Data", Toast.LENGTH_SHORT).show();
        }


        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();

                String em = Email_et.getText().toString().trim();
                String ps = Password_et.getText().toString().trim();

                String EML = SP.getString(signup.EmailKey,"");
                String PAS = SP.getString(signup.PassKey,"");


                if (!em.isEmpty() && !ps.isEmpty()) {

                    if(em.equals(EML) && ps.equals(PAS)){
                        Intent intent = new Intent(getBaseContext(),Main_Posts_Page.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "Loged in succefully", Toast.LENGTH_SHORT).show();


                        if(Remember_chbox.isChecked()){
                            EDIT.putBoolean(signup.RememberK,true);
                            EDIT.apply();
                        }else {
                            EDIT.putBoolean(signup.RememberK, false);
                            EDIT.apply();
                        }

                    }else{
                        Toast.makeText(getBaseContext(), "Email or Password Doesn't match", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginIn_et_Email));
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginIn_et_Password));
                    Toast.makeText(getBaseContext(), "Please check your Email Field and Password Field", Toast.LENGTH_SHORT).show();

                }


            }
        });

        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),signup.class);
                startActivity(intent);
            }
        });


    }

}
