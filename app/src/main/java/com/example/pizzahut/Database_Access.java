package com.example.pizzahut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database_Access {
    // access DB clas
    public SQLiteDatabase database; // to make instace / obj from the DB and get writeable and readable
    private SQLiteOpenHelper openHelper; // to open and close DB
    private static Database_Access instance;


    // Singleton Design Pattern :
    // this method stops user from making obj's from DB the known way .
    // create the database from this class .
    private Database_Access(Context context) {
        this.openHelper = new My_Database(context);
    }

    // the method will be used from user to create BD obj's
    // if there's no obj from Db then Create it , if there's one already then use it .
    public static Database_Access getInstance(Context context) {
        // if there's no obj already created then Create one
        if (instance == null) {
            instance = new Database_Access(context);
        }
        // if there's obj already created then use it
        return instance;
    }

    // open Db to use it and close it to able another user to use it .
    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        // if Db obj exists and opened to write in .
        if (this.database != null) {
            this.database.close();
        }
    }

//-------------------------------------------------------------------------
// add item
//تستقبل postوبترجع بولين هل تم اضافة عالداتابيز او لا
    public boolean insertProduct(Post post) {
        //زي البندل هوا كلاس بضيف البيانات على شكل كي (اسم العامود)وفاليو
        ContentValues CV = new ContentValues();
        //بنخلي يضيف على الاوبجكت العمود الاسم واعطي قيمة ...
        CV.put(My_Database.PROD_TB_NAME, post.getItemName());
        CV.put(My_Database.PROD_TB_PRICE, post.getPrice());
        CV.put(My_Database.PROD_TB_PAY, post.getPayment());
        CV.put(My_Database.PROD_TB_DESC, post.getDescription());
        CV.put(My_Database.PROD_TB_IMAGE, post.getImage());

        //انسيرت بترجع لونج لو ما تمت عملية الاضافة برجع-1

        long result = database.insert(My_Database.FOOD_PROD_TB, null, CV);
        // if inserting return true > 1 if false = -1
        return result != -1;
    }


    public boolean DeleteItemPost(Post p) {
        // "where clause is obligatory in delete query "
        // delete rows as condition ( delete where id = X ) :
        String args[] = {String.valueOf(p.getId())};
        long result = database.delete(My_Database.FOOD_PROD_TB, "id=?", args);
        // number of rows deleted
        return result > 0;
    }


    public boolean insertpurchases(Purchase_post post) {
        ContentValues CV = new ContentValues();
        CV.put(My_Database.PURCH_TB_UNAME, post.getUserName());
        CV.put(My_Database.PURCH_TB_PNAME, post.getItemName());
        CV.put(My_Database.PURCH_TB_DT, post.getDate());
        CV.put(My_Database.PURCH_TB_TOTPRICE, post.getPrice());

//        long result = database.insert(MyDatabase.FOOD_PURCH_TB, MyDatabase.PURCH_TB_UNAME + " LIKE ?" + Arrays.toString(new String[]{username}), CV);
     //   return database.insert(MyDatabase.FOOD_PURCH_TB, null, CV) != -1;

        long result = database.insert(My_Database.FOOD_PURCH_TB, null, CV);
        // if inserting return true > 1 if false = -1
        return result != -1;
    }


    public ArrayList<Post> getAllPosts() {
        // return all cars :
        ArrayList<Post> posts = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.FOOD_PROD_TB, null);
        //cursor.moveToFirst(); >>> returns boolean , if there's an obj returns true , else returns false .

        // to know if cursor contains data or not :
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int prod_id = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_ID));
                String prod_name = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_NAME));
                String prod_desc = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_DESC));
                int prod_price = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_PRICE));
                String prod_payment = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_PAY));
                String prod_image = (cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_IMAGE)));

                // call constructor :
                Post p = new Post(prod_id, prod_name, prod_desc, prod_price, prod_payment, prod_image);

                posts.add(p);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return posts;
    }

    public ArrayList<Purchase_post> getAllPurchase() {

        //بنجلب البيانات من القاعدة وبتم تخزينها داخل الاراي
        ArrayList<Purchase_post> pur_post = new ArrayList<>();
        //rawQuery بترجع اوبجكت من نوع رو كيرسر
        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.FOOD_PURCH_TB, null);
        //cursor.moveToFirst(); >>> returns boolean , if there's an obj returns true , else returns false .

        // الانتقال لاول عنصر وفحص اذا في قيم او لا
        if (cursor != null && cursor.moveToFirst()) {
            //بنستعمل دو وايل عشان نتاكد انو حيقرا بيانات اول عنصر بدون دو حييجاهل اول عنصر
            do {
                //getColumnIndex  بترجع القيمة باسم العامود حسب مكان الاسم
                int Purchase_id = cursor.getInt(cursor.getColumnIndex(My_Database.PURCH_TB_ID));
                String Purchase_UserName = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_UNAME));
                String Purchase_ItemName = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_PNAME));
                String Purchase_date = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_DT));
                String Purchase_price = (cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_TOTPRICE)));

                // call constructor :
                //بناخد العناصر عشان اضيفها للمصفوفة
                Purchase_post pp = new Purchase_post(Purchase_id, Purchase_UserName, Purchase_ItemName, Purchase_date, Purchase_price);
                pur_post.add(pp);

            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return pur_post;
    }

    //------------------------------------------------------------------------------------------------------------------------
    public ArrayList<Purchase_post> getLastPurchases() {
        ArrayList<Purchase_post> pur_post = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT  *   FROM " + My_Database.FOOD_PURCH_TB + " ORDER BY " + My_Database.PURCH_TB_ID + " DESC " + " LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {

            int Purchase_id = cursor.getInt(cursor.getColumnIndex(My_Database.PURCH_TB_ID));
            String Purchase_UserName = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_UNAME));
            String Purchase_ItemName = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_PNAME));
            String Purchase_date = cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_DT));
            String Purchase_price = (cursor.getString(cursor.getColumnIndex(My_Database.PURCH_TB_TOTPRICE)));
            // call constructor :
            Purchase_post pp = new Purchase_post(Purchase_id, Purchase_UserName, Purchase_ItemName, Purchase_date, Purchase_price);

            pur_post.add(pp);

            cursor.close();
        }

        return pur_post;
    }
    //-----------------------------------------------------------------------------------------

    public boolean ClearAllPurchases(){

        //if you want the function to return the count of deleted rows insert "1" in where clause >> db.delete(TABLE_NAME, "1", null)
        long result = database.delete( My_Database.FOOD_PURCH_TB , "1" ,  null);
        // if deleting return true > 1 if false = -1
        return result > 0;

    }


    //------------------------------------------------------------------------------------------------------------------------

    // get item details in details activity from posts activity
    public Post getPost(int postId) {

        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.FOOD_PROD_TB + " WHERE " + My_Database.PROD_TB_ID  +" =? ",new String[] {String.valueOf(postId)});
        //cursor.moveToFirst(); >>> returns boolean , if there's an obj returns true , else returns false .

        // to know if cursor contains data or not :
        if (cursor != null && cursor.moveToFirst()) {

            String prod_name = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_NAME));
            String prod_desc = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_DESC));
            int prod_price = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_PRICE));
            String prod_image = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_IMAGE));
            // call constructor :
            Post p = new Post(prod_name, prod_desc, prod_price, prod_image);

            cursor.close();
            return p;
        }

        return null;
    }


    // name search method
    public ArrayList<Post> getpost(String nameSearch) {
        //return all cars :
        ArrayList<Post> posts = new ArrayList<>();

        //like "select" method but contains a condition " where " to do some search  .
        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.FOOD_PROD_TB + " WHERE " + My_Database.PROD_TB_NAME + " LIKE ?", new String[]{"%" + nameSearch + "%"
        });

        // to know if cursor contains data or not :
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int prod_id = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_ID));
                String prod_name = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_NAME));
                int prod_price = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_PRICE));
                String prod_payment = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_PAY));
                String prod_image = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_IMAGE));

//                // call constructor :
                Post p = new Post(prod_name, prod_payment, prod_price, prod_image);
                posts.add(p);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return posts;
    }


    //    // name search method
    public ArrayList<Post> RadioSearch(String Payment) {
        //return all cars :
        ArrayList<Post> posts = new ArrayList<>();

        //like "select" method but contains a condition " where " to do some search  .
        Cursor cursor = database.rawQuery("SELECT * FROM " + My_Database.FOOD_PROD_TB + " WHERE " + My_Database.PROD_TB_PAY + " LIKE ? ", new String[]{Payment});

        // to know if cursor contains data or not :
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int prod_id = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_ID));
                String prod_name = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_NAME));
                int prod_price = cursor.getInt(cursor.getColumnIndex(My_Database.PROD_TB_PRICE));
                String prod_payment = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_PAY));
                String prod_image = cursor.getString(cursor.getColumnIndex(My_Database.PROD_TB_IMAGE));

//                // call constructor :
                Post p = new Post(prod_payment);
                posts.add(p);
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return posts;


    }



}

//------------------------------------------------------------------------------------------------------------------------
//    public ArrayList<Purchase_post> getAllPurchase(String username) {
//        // return all purches :
//        ArrayList<Purchase_post> pur_post = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM MyDatabase.FOOD_PURCH_TB WHERE MyDatabase.PURCH_TB_UNAME LIKE ?", new String[]{username});
//        //cursor.moveToFirst(); >>> returns boolean , if there's an obj returns true , else returns false .
//
//        // to know if cursor contains data or not :
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//
//                int Purchase_id = cursor.getInt(cursor.getColumnIndex(MyDatabase.PURCH_TB_ID));
//                String Purchase_UserName = cursor.getString(cursor.getColumnIndex(MyDatabase.PURCH_TB_UNAME));
//                String Purchase_ItemName = cursor.getString(cursor.getColumnIndex(MyDatabase.PURCH_TB_PNAME));
//                String Purchase_date = cursor.getString(cursor.getColumnIndex(MyDatabase.PURCH_TB_DT));
//                String Purchase_price = (cursor.getString(cursor.getColumnIndex(MyDatabase.PURCH_TB_TOTPRICE)));
//
//                // call constructor :
//                Purchase_post pp = new Purchase_post(Purchase_id, Purchase_UserName, Purchase_ItemName, Purchase_date, Purchase_price);
//
//                pur_post.add(pp);
//
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//
//        return pur_post;
//    }
//