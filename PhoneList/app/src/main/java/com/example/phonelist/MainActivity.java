package com.example.phonelist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvSelected;
    ListView listView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvSelected = findViewById(R.id.tvSelected);
        final String[] phoneList = {"Apple", "Samsung", "OnePlus", "Google", "Xiaomi", "Oppo", "Vivo", "Realme", "Asus", "Nokia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, phoneList);
        LinearLayout ll = findViewById(R.id.main);
        listView = ll.findViewById(R.id.lvPhone);
        listView.setAdapter(adapter);
        tvSelected.setText("Vị trí 1 : " + phoneList[0]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                tvSelected.setText("Vị trí " + (i + 1) + " : " + phoneList[i]);
            }
        });

    }
}