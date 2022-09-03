package com.example.pizzahut;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Calendar;
import java.util.Locale;

public class signup extends AppCompatActivity {


    public static final String FullNameKey = "FullName_K", UserNameKey = "UserName_K", EmailKey = "Email_K", PassKey = "Pass_K",
            RePassKey = "RePass_K", PhoneKey = "Phone_K", DBKey = "DB_K", SpCountryK = " SpCountry_K", AddressK = " Adress_K",
            GenderK = "Gender_K " , AdminK = "Admin_K ", ImageK = " Image_K" , RememberK = " Remember_K";


    String InputFname , InputEmail, InputUname , InputPass , InputRePass , InputBirthdate , InputAddress, InputPhone , Inputspinner ;

    private EditText Fname, Email, Uname, Pass, RePass, Phone, BirthDate , Address ;
    android.widget.Spinner SpCountry;
    Button Save , AddImage ;
    ImageView Image;
    CheckBox AdminCheck;
    private static final int RESULT_LOAD_IMAGE = 1;
    RadioButton RdFemale, RdMale;
    RadioGroup Gender;
    Uri UriImage ;
    MediaPlayer click;

    public static String EMAIL_INTENT_KEY = "User_Email";
    public static String PASS_INTENT_KEY = "User_Pass" ;

    // to read from SharedPreferences
    public static SharedPreferences SP ;
    // to write in / edit SharedPreferences
    public static SharedPreferences.Editor EDIT ;
    public static final String PREF_NAME = "RegisterPrefrences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SP = getSharedPreferences(PREF_NAME ,MODE_PRIVATE);
        EDIT=SP.edit();



        Fname = findViewById(R.id.SiginUp_et_Fullname);
        Email = findViewById(R.id.SiginUp_et_Email);
        Uname = findViewById(R.id.SiginUp_et_Username);
        Pass = findViewById(R.id.SiginUp_et_Password);
        RePass = findViewById(R.id.SiginUp_et_RePassword);
        Phone = findViewById(R.id.SiginUp_et_Phone);
        Address = findViewById(R.id.SiginUp_et_Adress);
        SpCountry = findViewById(R.id.SiginUp_et_Spinner);
        BirthDate = findViewById(R.id.SiginUp_et_BirthDate);
        Save = findViewById(R.id.SiginUp_btnSave);
        AddImage = findViewById(R.id.SiginUp_btnSaveImage);
        AdminCheck = findViewById(R.id.SiginUp_CbAdminstrator);
        Gender = findViewById(R.id.SiginUp_RDGroup);
        RdFemale = findViewById(R.id.SiginUp_Rfemale);
        RdMale = findViewById(R.id.SiginUp_Rmale);
        Image = findViewById(R.id.SiginUp_ImageView);




//        -------------------------- If User is Already signed up Toast method ------------------------------
        restoreResult();
//        -------------------------------- Pick Image From Gallery  -------------------------------------

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gal =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gal, RESULT_LOAD_IMAGE);
            }
        });

//        -------------------------------- Open calender to choose birth date when click on edittext -------------------------------------
        final Calendar myCalendar = Calendar.getInstance();
        EditText edittext= findViewById(R.id.SiginUp_et_BirthDate);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime())); }};

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(signup.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //----------------------------------------------------------------------------------------------------------------------------

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = MediaPlayer.create(getBaseContext(), R.raw.click);
                click.start();
                InputFname = Fname.getText().toString();
                InputEmail = Email.getText().toString();
                InputUname = Uname.getText().toString();
                InputPass = Pass.getText().toString().trim();
                InputRePass = RePass.getText().toString();
                InputPhone = Phone.getText().toString();
                Inputspinner = SpCountry.getSelectedItem().toString();
                InputBirthdate = BirthDate.getText().toString();
                InputAddress = Address.getText().toString();



                if (Fname.getText().toString().trim().equals("" )) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Fullname));
                    Toast.makeText(getBaseContext(), "Please Enter your Full Name", Toast.LENGTH_SHORT).show();
                } else if (Uname.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Username));
                    Toast.makeText(getBaseContext(), "Please Enter your User Name ", Toast.LENGTH_SHORT).show();
                } else if (Address.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Adress));
                    Toast.makeText(getBaseContext(), "Please Enter your Address ", Toast.LENGTH_SHORT).show();
                } else if (Email.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Email));
                    Toast.makeText(getBaseContext(), "Please Enter your Email", Toast.LENGTH_SHORT).show();
                } else if (Pass.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Password));
                    Toast.makeText(getBaseContext(), "Please Enter your Password", Toast.LENGTH_SHORT).show();
                } else if (RePass.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_RePassword));
                    Toast.makeText(getBaseContext(), "Please ReEnter your Password again", Toast.LENGTH_SHORT).show();
                } else if (!RePass.getText().toString().trim().equals(Pass.getText().toString().trim())) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Password));
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_RePassword));
                    Toast.makeText(getBaseContext(), "Passwords Doesn't Match !! ", Toast.LENGTH_SHORT).show();
                }else if (BirthDate.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_BirthDate));
                    Toast.makeText(getBaseContext(), "Please Enter your BirthDate", Toast.LENGTH_SHORT).show();
                } else if (Phone.getText().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Phone));
                    Toast.makeText(getBaseContext(), "Please Enter your phone", Toast.LENGTH_SHORT).show();
                } else if (SpCountry.getSelectedItem().toString().trim().equals("")) {
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.SiginUp_et_Spinner));
                    Toast.makeText(getBaseContext(), "Please Enter your Country", Toast.LENGTH_SHORT).show();
                } else if (Gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getBaseContext(), "Please Check your Gender", Toast.LENGTH_SHORT).show();
                } else if (UriImage == null) {
                    Toast.makeText(getBaseContext(), "Please Choose an Image", Toast.LENGTH_SHORT).show();
                } else {


                    EDIT.putString(FullNameKey, InputFname);
                    EDIT.putString(UserNameKey, InputUname);
                    EDIT.putString(EmailKey, InputEmail);
                    EDIT.putString(PassKey, InputPass);
                    EDIT.putString(RePassKey, InputRePass);
                    EDIT.putString(PhoneKey, InputPhone);
                    EDIT.putString(DBKey, InputBirthdate);
                    EDIT.putString(AddressK, InputAddress);
                    EDIT.putString(SpCountryK, Inputspinner);
                    EDIT.putString(ImageK, UriImage.getPath());

                    if (RdMale.isClickable()) {
                        EDIT.putString(GenderK,"Male");
                    } else  if (RdFemale.isClickable())
                        EDIT.putString(GenderK, "Female");

                    if(AdminCheck.isChecked()) {
                        EDIT.putBoolean(AdminK, true);
                    }else{
                        EDIT.putBoolean(AdminK, false);
                    }
                    EDIT.apply();


                    Toast.makeText(getBaseContext(), "Data Saved ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getBaseContext(),Sign_In.class);
                    intent.putExtra(EMAIL_INTENT_KEY, InputEmail);
                    intent.putExtra(PASS_INTENT_KEY, InputPass);
                    startActivity(intent);

                }
            }
        });


    }


    public static boolean ChangePass(String NewPass, String ReNewPass, String OldPass){
        String SavedPass =  SP.getString(PassKey, null) ;
        if( OldPass.equals(SavedPass) && ReNewPass.equals(NewPass)){
            EDIT.putString(PassKey, NewPass);
            EDIT.putString(RePassKey, ReNewPass);
            EDIT.apply();
            return true ;
        }else {
            return false;
        }
    }

    public static String getPass(){

        return SP.getString(PassKey,"");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // GET IMAGE FROM GALLERY
        //requestCode بعرف الشاشة الي انتا راجع منها
        //RESULT_OK ازا رجع بشكل سليم وتمت العملية برجع اوك
        //null != data هاي للامان لو فش تطبيق كاميرا
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && null != data) {
                //remove old pic
                Image.setBackgroundResource(0);
                UriImage = data.getData();
                Image.setImageURI(UriImage);
                Toast.makeText(getBaseContext(), "DONE ", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void restoreResult(){
        String Full = SP.getString(FullNameKey, "") ;
        String User = SP.getString(UserNameKey, "") ;
        String Email = SP.getString(EmailKey, "") ;
        String address = SP.getString(AddressK, "") ;
        String Pass = SP.getString(PassKey, "") ;
        String RePass = SP.getString(RePassKey, "") ;
        String Phone = SP.getString(PhoneKey, "") ;
        String DB = SP.getString(DBKey, "") ;
        String Count = SP.getString(SpCountryK, "") ;
        String Gender = SP.getString(GenderK, "") ;
        String Img = SP.getString(ImageK, "") ;

        if (Full == null && User == null && address == null && Email == null && Pass == null && RePass == null && Phone == null && DB == null && Count == null && Gender == null && Img == null){
            Toast.makeText(getApplicationContext()," you Didn't sign up yet ", Toast.LENGTH_SHORT).show();}
        else if (Full != null && User != null && Email != null && Pass != null && address != null && RePass != null && Phone != null && DB != null && Count != null && Gender != null && Img != null){
            Toast.makeText(getApplicationContext(), "you already signed up ", Toast.LENGTH_SHORT).show(); }
    }

}