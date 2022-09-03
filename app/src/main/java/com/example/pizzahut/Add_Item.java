package com.example.pizzahut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Add_Item extends AppCompatActivity {
    EditText Item_Name, Item_Price, Item_Description;
    RadioButton radioButton , radioButton2 ;
    RadioGroup RadioGroup;
    Button Save_btn;
    ImageView Image ;

    String Input_Item_Name, Input_Item_Description, Input_Item_Payment, Input_Item_Image;
    int Input_Item_Price;

    private Database_Access DBA ;

    Uri uriImage;
    static final int PIC_IMG_GAL = 5;
    static final String  CHANNEL_ID = "CHANNEL_ID";
    static final int  NOTIFICATION_ID = 10 ;

    private static final int PERM_REQ_CODE = 3;

    // MediaPlayer click; -------------------------

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Item_Name =findViewById(R.id.itemname);
        Image=findViewById(R.id.Image);
        Item_Price =findViewById(R.id.itemprice);
        Item_Description =findViewById(R.id.description);
        RadioGroup = findViewById(R.id.group);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        Save_btn =findViewById(R.id.Item_details_Save_btn);

        // take permission from user to get into gallery for Version +6 :
        //check if permission is already taken from user or not , returns INT .
        // compact = support library
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // if permission isn't granted take it in thins activity
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_REQ_CODE);
        }

        // obj from DATABASE :
        DBA = Database_Access.getInstance(this);

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gal =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gal, PIC_IMG_GAL);
            }
        });


        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input_Item_Name = Item_Name.getText().toString();
                Input_Item_Price = Integer.parseInt(Item_Price.getText().toString());
                Input_Item_Description = Item_Description.getText().toString();

                if (Input_Item_Name.equals("" )) {
                    Toast.makeText(getBaseContext(), "Please Enter Item Name", Toast.LENGTH_SHORT).show();
                }else if (Input_Item_Price <= 0){
                    Toast.makeText(getBaseContext(), "Please Enter Item price", Toast.LENGTH_SHORT).show();
                }else if (RadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getBaseContext(), "Please Check Item Payment", Toast.LENGTH_SHORT).show();
                }else if (Input_Item_Description.equals("" )) {
                    Toast.makeText(getBaseContext(), "Please Enter Item Description ", Toast.LENGTH_SHORT).show();
                }else {

                    if (uriImage != null) {
                        Input_Item_Image = (uriImage.toString());
                    }

                    switch (RadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radioButton:
                            Input_Item_Payment = "cash";
                            break;

                        case R.id.radioButton2:
                            Input_Item_Payment = "installments";
                            break;
                    }

                    DBA.open();
                    // insert data to item :
                    Post p = new Post(Input_Item_Name, Input_Item_Description, Input_Item_Price, Input_Item_Payment, Input_Item_Image);

                    Boolean result = DBA.insertProduct(p);
                    // if process done successfully
                    if (result) {
                        Toast.makeText(getBaseContext(), "item Added successfully ", Toast.LENGTH_SHORT).show();
                        //setResult(Main_Posts_Page.SHOW_ITEM_REQ_CODE,null);   // null = no data to get back
                        DisplayNotification();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "item NOT Added successfully ", Toast.LENGTH_SHORT).show();

                    }

                    DBA.close();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // FROM GALLERY
        if(requestCode == PIC_IMG_GAL && resultCode == RESULT_OK){
            if (null != data){
                // saving image from Galery directory in a var .
                uriImage = data.getData();
                Image.setImageURI(uriImage);
                Toast.makeText(getBaseContext(),"DONE ",Toast.LENGTH_SHORT).show();}

            else if ( resultCode == RESULT_CANCELED){
                Toast.makeText(getBaseContext(),"CANCELED",Toast.LENGTH_SHORT).show(); } }
    }

    //------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERM_REQ_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }}

    private void DisplayNotification() {

        // check if version > 26 build notification channel :
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // obj of class notification channel:
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID," Daily Recipes Notify ", NotificationManager.IMPORTANCE_HIGH);
            // channel description :
            channel.setDescription("NEW MEAL ADDED , ChEcK OuT !");
            // Managing Notification + adding to Notification .. etc  :
            // no "new" bc Notification is already in android so we're reusing what's already exists .
            NotificationManager notification_manager = getSystemService(NotificationManager.class);
            //get Notification service and build a channel on it .
            notification_manager.createNotificationChannel(channel);
        }

        // build + publish notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pizza_1_)
                .setContentTitle(" ChEcK OuT !")
                .setContentText("NEW MEAL ADDED , ChEcK OuT !")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("NEW MEAL ADDED , ChEcK OuT !"))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat NMC = NotificationManagerCompat.from(this);
        // notification id :
        NMC.notify(NOTIFICATION_ID,builder.build());
    }
}
