package com.example.giuaky;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArrayAdapterContactItem<T> extends ArrayAdapter<T> {
    Activity context;
    int idLayout;
    ArrayList<T> myList;

    public ArrayAdapterContactItem(Activity context, int idLayout, ArrayList<T> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = context.getLayoutInflater();
        convertView = myInflater.inflate(idLayout, null);

        T item = myList.get(position);

        if (item instanceof DonVi) {
            DonVi myContact = (DonVi) item;
            ImageView img = convertView.findViewById(R.id.img_avatar);
            img.setImageResource(R.drawable.avatar);
            TextView tvName = convertView.findViewById(R.id.tvName);
            tvName.setText(myContact.getTenDonVi());
            TextView tvPhone = convertView.findViewById(R.id.tvPhone);
            tvPhone.setText(myContact.getDienThoai());
        }
        if(item instanceof NhanVien){
            NhanVien myContact = (NhanVien) item;
            ImageView img = convertView.findViewById(R.id.img_avatar);
            img.setImageResource(R.drawable.avatar);
            TextView tvName = convertView.findViewById(R.id.tvName);
            tvName.setText(myContact.getHoTen());
            TextView tvPhone = convertView.findViewById(R.id.tvPhone);
            tvPhone.setText(myContact.getSoDienThoai());
        }
        return convertView;
    }
}
