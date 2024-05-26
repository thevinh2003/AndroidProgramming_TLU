package com.example.vnexpress;

import android.graphics.Bitmap;

public class List
{
    private String title, info, link;
    private Bitmap img;

    public List(String title, String info, String link, Bitmap img)
    {
        this.title = title;
        this.info = info;
        this.link = link;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
