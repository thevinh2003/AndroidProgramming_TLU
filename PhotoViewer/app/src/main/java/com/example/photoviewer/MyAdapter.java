package com.example.photoviewer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Photo> {
    Activity context;
    int layoutId;
    ArrayList<Photo> myList;

    public MyAdapter(Activity context, int layoutId, ArrayList<Photo> myList) {
        super(context, layoutId, myList);
        this.context = context;
        this.layoutId = layoutId;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Photo photo = myList.get(position);

        ImageView img = convertView.findViewById(R.id.imgPhone);
        img.setImageResource(photo.getImage());

        return convertView;
    }
}
