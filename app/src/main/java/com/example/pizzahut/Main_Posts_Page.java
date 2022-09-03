package com.example.pizzahut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main_Posts_Page extends AppCompatActivity {
    public static RecyclerView RV;
    //بحدد طريقة العرض
    private RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this);
    private Post_Adapter adapter;
    private Database_Access DBA;
    public static final String Item_KEY = "food_key";
    RadioGroup Payment_RDG;
    RadioButton cash, installments;


    public static SharedPreferences SP ;
    public static final String PREF_NAME = "RegisterPrefrences";

    public static final int SHOW_ITEM_REQ_CODE = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        RV = findViewById(R.id.recyclerview);
        cash = findViewById(R.id.list_cash);
        installments = findViewById(R.id.list_installments);
        Payment_RDG = findViewById(R.id.list_RDGroup);


        SP = getSharedPreferences(PREF_NAME ,MODE_PRIVATE);


        String userName = SP.getString(signup.UserNameKey,"");
        Toast.makeText(getBaseContext(), "welcome  " + userName, Toast.LENGTH_LONG).show();


        //----------------------------------------------------------------------------------------------------
        //    تصنيف الادابتر الي كاش او تقسيط
//        if (cash.isChecked()) {
//
//            DBA.open();
//            ArrayList<Post> paypost = DBA.RadioSearch("cash");
//            DBA.close();
//            adapter.setPosts(paypost);
//            adapter.notifyDataSetChanged();
//            Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
//
//        } else  if (installments.isClickable()){
//            DBA.open();
//            ArrayList<Post> paypost = DBA.RadioSearch("installments");
//            DBA.close();
//            adapter.setPosts(paypost);
//            adapter.notifyDataSetChanged();
//            Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
//        }

        //----------------------------------------------------------------------------------------------------
//
//        switch (Payment_RDG.getCheckedRadioButtonId())
//        {
//            case R.id.list_cash:
//                Inputpayment="cash";
//                break;
//
//            case R.id.list_installments:
//                Inputpayment="installments";
//                break;
//        }

        // obj from DATABASE , create DB if Not exists or call it if already Exsists :
        DBA = Database_Access.getInstance(this);
        DBA.open();
        ArrayList<Post> posts = DBA.getAllPosts();
        DBA.close();

        adapter = new Post_Adapter(posts, new onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int postId) {
                // this code will be initialized when user click on any item in recyclerView .
                Intent intent = new Intent(getBaseContext(), Item_Details.class);
                intent.putExtra(Item_KEY, postId);
                startActivityForResult(intent,SHOW_ITEM_REQ_CODE);
            }
        });
        RV.setAdapter(adapter);
        //بحدد طريقة العرض
        mlayoutManager = new LinearLayoutManager(this);
        //مدير جوا الادابتر تاعي وظيفتو بحدد استدعاء اون كريت ومتى يجيب اون بايند والكونت
        RV.setLayoutManager(mlayoutManager);
        //هل الريسايكل حجمو ثابت ولى بتغير
        RV.setHasFixedSize(true);
//        adapter.notifyDataSetChanged();


    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //بتجيب المنيو الي انتا شغال عليها
        //وظيفتها بتاخد التصميم الي هاناك وبتحولو لاوبجكت جافا وبتركبو في المنيو الي في اون كرييت
        getMenuInflater().inflate(R.menu.menu_option, menu);

        // inflating menu search item :
        SearchView SV = (SearchView) menu.findItem(R.id.SEARCH).getActionView();
        // enabling (>) submit button to be clicked to search after writing :
        SV.setSubmitButtonEnabled(true);

        // to enable search proccess :
        SV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String SearchText) {
                DBA.open();
                ArrayList<Post> posts = DBA.getpost(SearchText);
                DBA.close();
                adapter.setPosts(posts);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Search", Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String NewSearchText) {
                // search while user still typing :
                DBA.open();
                ArrayList<Post> posts = DBA.getpost(NewSearchText);
                DBA.close();
                adapter.setPosts(posts);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Search", Toast.LENGTH_LONG).show();

                return false;
            }
        });
        // if user canceled searching :
        SV.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                DBA.open();
                ArrayList<Post> posts = DBA.getAllPosts();
                DBA.close();
                adapter.setPosts(posts);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "canceled", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//يجيب الايدي تاع الايتم
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getBaseContext(), Setting.class);
                startActivity(intent);
                break;

            case R.id.about:
                Intent intent2 = new Intent(getBaseContext(), Contact_Us.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                //المفروض هان يطلع من التطبيق
                logout();
                break;
            //    return super.onOptionsItemSelected(item); //هان يعيني تمت العملية بنجاح

        }
        return true;
    }

    private void logout() {
        SharedPreferences SP = getSharedPreferences(signup.PREF_NAME ,MODE_PRIVATE);
        // to write in / edit SharedPreferences
        SharedPreferences.Editor EDIT = SP.edit();

//        // if we want to clear all data from shared prefrences and sign up again :
//        EDIT.clear();
//        EDIT.apply();

        EDIT.putBoolean(signup.RememberK,false);
        EDIT.apply();

        Intent intent = new Intent(this, Sign_In.class);
        // Add new Flag to start new Activity
        //بترجع على شاشة موجودة وهيا تسجيل دخول
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    // after delete or insert item
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        DBA.open();
        ArrayList<Post> p = DBA.getAllPosts();
        DBA.close();
        adapter.setPosts(p);
        adapter.notifyDataSetChanged();}





}


