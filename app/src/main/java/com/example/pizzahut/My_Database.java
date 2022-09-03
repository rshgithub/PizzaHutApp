package com.example.pizzahut;

import android.content.Context;

import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class My_Database extends SQLiteAssetHelper {

    public static final String DB_NAME = "foodapp.db";
    public static final int DB_VERSION = 1 ;

    public static final String  FOOD_PROD_TB = "product",
                PROD_TB_ID = "id" , PROD_TB_NAME = "name" ,  PROD_TB_DESC = "description" , PROD_TB_PRICE = "price" , PROD_TB_PAY = "payment" , PROD_TB_IMAGE = "image" ;

    public static final String  FOOD_PURCH_TB = "purchases",
            PURCH_TB_ID = "id" , PURCH_TB_UNAME = "User Name" , PURCH_TB_PNAME = "Purch Name" , PURCH_TB_DT = "Date & Time" , PURCH_TB_TOTPRICE = "total price" ;

    public My_Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    //-------------------------------------------------------------------------



}
