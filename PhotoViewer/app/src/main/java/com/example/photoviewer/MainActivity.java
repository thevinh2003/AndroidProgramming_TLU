package com.example.photoviewer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int[] image = {R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11};
    GridView gvPhone;
    ArrayList<Photo> arrayList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gvPhone = findViewById(R.id.gvPhone);
        arrayList = new ArrayList<Photo>();
        for (int i = 0; i < image.length; i++) {
            Photo phone = new Photo(image[i]);
            arrayList.add(phone);
        }
        adapter = new MyAdapter(this, R.layout.layout_phone, arrayList);
        gvPhone.setAdapter(adapter);

        gvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               android.content.Intent intent = new android.content.Intent(MainActivity.this, ViewPhoto.class);
                intent.putExtra("image", image[position]);
                startActivity(intent);
            }
        });
    }
}