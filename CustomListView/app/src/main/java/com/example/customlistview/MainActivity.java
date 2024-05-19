package com.example.customlistview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] namephone ={"Điện thoại Iphone 12", "Điện thoại SamSung S20","Điện thoại Nokia 6","Điện thoại Bphone 2020","Điện thoại Oppo 5","Điện thoại VSmart joy2"};
    int[] imagephone = {R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11, R.drawable.ip11};
    int[] prices = {1000, 2000, 3000, 4000, 5000, 6000};
    ArrayList<Phone> arrayList;
    MyAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lvMain);
        arrayList = new ArrayList<Phone>();
        for (int i = 0; i < namephone.length; i++) {
            Phone phone = new Phone(imagephone[i], namephone[i], prices[i]);
            arrayList.add(phone);
        }
        adapter = new MyAdapter(this, R.layout.layout_listview, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("name", arrayList.get(position).getName());
                startActivity(intent);
            }
        });
    }
}