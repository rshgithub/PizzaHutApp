package com.example.pizzahut;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;

public class Purchase_post extends ArrayList<Purchase_post> {
        private String UserName , ItemName  , date, price;
        private int id;

    // with id to get / select Post
    public Purchase_post(int id, String UserName, String ItemName, String date, String price) {
            this.UserName = UserName;
            this.ItemName = ItemName;
            this.date = date;
            this.id = id;
            this.price = price;
        }

    // without id to insert new post
    public Purchase_post(String userName, String itemName, String date, String price) {
        UserName = userName;
        ItemName = itemName;
        this.date = date;
        this.price = price;
    }


    public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }