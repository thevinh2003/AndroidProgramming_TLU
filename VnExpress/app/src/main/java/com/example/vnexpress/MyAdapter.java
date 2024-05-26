package com.example.vnexpress;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<List> {
    private Activity context;
    private ArrayList<List> list;
    private int layout;

    public MyAdapter(Activity context, int resource, ArrayList<List> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout, null);
        final List list = this.list.get(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageBitmap(list.getImg());
        TextView textView = convertView.findViewById(R.id.tvTitle);
        textView.setText(list.getTitle());
        TextView textView1 = convertView.findViewById(R.id.tvContent);
        textView1.setText(list.getInfo());

        MainActivity.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(list.getLink()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
