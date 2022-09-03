package com.example.pizzahut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Purchase_List extends AppCompatActivity {
    public static ArrayList<Purchase_post> ppost = new ArrayList<>();
    RecyclerView RV;
    public static Purchase_Adapter adapter;
    private Database_Access DBA;

    TextView prices, dates, itemname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__list);

        RV = findViewById(R.id.recyclerview);
        prices = findViewById(R.id.Item_details_Price_Num_Tv);
        dates = findViewById(R.id.dateandtime);
        itemname = findViewById(R.id.itemname);


        if (ppost.equals("")) {
            Toast.makeText(getBaseContext(), "there is no data", Toast.LENGTH_SHORT).show();
        } else {
            // obj from DATABASE :
            DBA = Database_Access.getInstance(this);
            DBA.open();
            ArrayList<Purchase_post> Purchases = DBA.getAllPurchase();
            DBA.close();

            adapter = new Purchase_Adapter(Purchases);
            // هاي بتسمح بطريقة عرض البيانات داخل الشاشة على شكل لينير مسلا
            RV.setAdapter(adapter);
            RecyclerView.LayoutManager LLM = new LinearLayoutManager(this);
            RV.setLayoutManager(LLM);
            RV.setHasFixedSize(true);
//            adapter.notifyDataSetChanged();

        }

    }
}