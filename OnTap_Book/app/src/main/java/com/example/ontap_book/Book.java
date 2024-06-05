package com.example.ontap_book;

import androidx.annotation.NonNull;

public class Book {
    private int id;
    private String name;
    private int author_id;
    private String author_name;
    private int price;

    public Book(int id, String name, int author_id, int price, String author_name) {
        this.id = id;
        this.name = name;
        this.author_id = author_id;
        this.price = price;
        this.author_name = author_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " - " + price + " - " + author_id;
    }
}
