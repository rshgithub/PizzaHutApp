package com.example.pizzahut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {

    // This is the time it will take for the splash screen to be displayed
    private static int SPLASH_TIME_OUT = 4000;
    MediaPlayer click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        click = MediaPlayer.create(getBaseContext(), R.raw.monamor);
        click.start();
        ////////////////////////////////////////////////////////////////////////////
        // This is where we change our app name font to blacklist font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.dancing_script_bold);


        TextView appname = findViewById(R.id.appname);
        appname.setTypeface(typeface);

        //End of appname font changing
        ////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////
        // We use the Yoyo to make our app logo to bounce app and down.
        // Your can change the techniques to fit your liking.

        YoYo.with(Techniques.Bounce)
                .duration(7000) // Time it for logo takes to bounce up and down
                .playOn(findViewById(R.id.imageView));
        YoYo.with(Techniques.Bounce)
                .duration(7000) // Time it for logo takes to bounce up and down
                .playOn(findViewById(R.id.imageView2));
        YoYo.with(Techniques.Bounce)
                .duration(7000) // Time it for logo takes to bounce up and down
                .playOn(findViewById(R.id.textView));
        /////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////////////
        //This is where we make our app name fade in as it moves up
        // There are other Fade Techniques too
        //example FadeIn, FadeInUp, FadeInDown, FadeInLeft, FadeInRight
        //FadeOut, FadeOutDown, FadeOutLeft, FadeOutRight, FadeOutUp

        YoYo.with(Techniques.FadeInUp)
                .duration(5000) // Time it for app name to fade in up
                .playOn(findViewById(R.id.appname));
        /////////////////////////////////////////////////////////////////////////////


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences SP = getSharedPreferences(signup.PREF_NAME, MODE_PRIVATE);

                if (SP.getBoolean(signup.RememberK, false)) {
                    startActivity(new Intent(getBaseContext(), Main_Posts_Page.class));
                } else {
                    startActivity(new Intent(getBaseContext(), Sign_In.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}