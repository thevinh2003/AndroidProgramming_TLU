package com.example.jsonreader;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView lvProducts;
    ArrayList<String> productList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lvProducts = findViewById(R.id.lvProducts);
        productList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        lvProducts.setAdapter(adapter);

        btnParse.setOnClickListener(v -> {
            productList.clear();
            String json = null;
            try {
                InputStream is = getAssets().open("computer.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
                JSONObject obj = new JSONObject(json);
                productList.add(obj.getString("MaDM"));
                productList.add(obj.getString("TenDM"));
                JSONArray arr = obj.getJSONArray("Sanphams");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj2 = arr.getJSONObject(i);
                    productList.add(obj2.getString("MaSP") + " - " + obj2.getString("TenSP"));
                    productList.add(obj2.getString("SoLuong") + " * " + obj2.getString("DonGia") + " = " + obj2.getString("ThanhTien"));
                    productList.add(obj2.getString("Hinh"));
                }
                adapter.notifyDataSetChanged();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
