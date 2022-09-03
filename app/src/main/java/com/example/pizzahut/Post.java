package com.example.pizzahut;

public class Post {
    private String ItemName , description , payment   , image;
    private int id, price;

    // with id to get / select Post
    public Post(int id , String ItemName ,String description, int price, String payment, String image) {
        this.ItemName = ItemName;
        this.description = description;
        this.price = price;
        this.payment = payment;
        this.image = image;
        this.id = id;
    }

    // get post without payment - item details .
    public Post(String itemName, String description, int price, String image) {
        ItemName = itemName;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // search by radio button
    public Post(String payment) {
        this.payment = payment;
    }

    // without id to insert new post
    public Post(String ItemName , String description, int price, String payment, String image)  {
        this.ItemName = ItemName;
        this.description = description;
        this.payment = payment;
        this.image = image;
        this.price = price;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }}