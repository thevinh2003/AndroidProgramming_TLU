package com.example.customlistview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    Activity context;
    int IdLayout;
    ArrayList<Phone> myList;

    public MyAdapter(Activity context, int IdLayout, ArrayList<Phone> myList) {
        super(context, IdLayout, myList);
        this.context = context;
        this.IdLayout = IdLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(IdLayout, null);
        Phone phone = myList.get(position);

        ImageView imgPhone = convertView.findViewById(R.id.imgPhone);
        TextView txtName = convertView.findViewById(R.id.tvPhoneName);
        TextView txtPrice = convertView.findViewById(R.id.tvPrice);
        txtName.setText(phone.getName());
        txtPrice.setText(phone.getPrice());
        imgPhone.setImageResource(phone.getImage());
        return convertView;
    }
}
