package com.example.pizzahut;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Change_Password extends AppCompatActivity {

    EditText inputNewPass , inputReNewPass , inputOldPass;
    String NewPass , ReNewPass , OldPass;
    Boolean result ;
    Button CommitChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        inputNewPass = findViewById(R.id.Change_et_NewPassword);
        inputReNewPass = findViewById(R.id.Change_et_ReNewPassword);
        inputOldPass = findViewById(R.id.Change_et_OldPassword);
        CommitChange = findViewById(R.id.Change__btn_Change);


        CommitChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewPass = inputNewPass.getText().toString().trim();
                ReNewPass = inputReNewPass.getText().toString().trim();
                OldPass = inputOldPass.getText().toString().trim();

                if (!NewPass.equals("" ) && !NewPass.equals("" ) && !NewPass.equals("" )) {
                    result = signup.ChangePass(NewPass, ReNewPass, OldPass);

                    // if true = password has changed , else = false = didn't change for some reason .
                    if (result) {
                        String newPassTxt = signup.getPass();
                        Toast.makeText(getBaseContext(), "Password has changed SUCESSFULLY ! your new password is = "+ newPassTxt, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "New Passwords May Not Match or Old Password is wrong ", Toast.LENGTH_LONG).show();}

                }else {
                    Toast.makeText(getBaseContext(), "one or more feild are empty ! ", Toast.LENGTH_LONG).show();}
            }
        });
    }
}