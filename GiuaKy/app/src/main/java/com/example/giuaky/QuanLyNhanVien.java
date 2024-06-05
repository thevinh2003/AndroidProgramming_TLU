package com.example.giuaky;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class QuanLyNhanVien extends AppCompatActivity {
    private ArrayList<String> listNhanVien;
    private ArrayAdapter<String> adapterNhanVien;
    EditText edtSearch;
    ListView lvNhanVien;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_nhan_vien);

        db = openOrCreateDatabase("giuaky.db", MODE_PRIVATE, null);
        listNhanVien = new ArrayList<>();
        adapterNhanVien = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNhanVien);
        lvNhanVien = findViewById(R.id.lvNhanVien);
        lvNhanVien.setAdapter(adapterNhanVien);
        loadNhanVien();
    }

    private void loadNhanVien() {
        listNhanVien.clear();
        String query = "SELECT * FROM NhanVien";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String Hoten = cursor.getString(1);
                String ChucVu = cursor.getString(2);
                String Email = cursor.getString(3);
                String DienThoai = cursor.getString(4);
                listNhanVien.add(id + " - " + Hoten + " - " + ChucVu + " - " + Email + " - " + DienThoai);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapterNhanVien.notifyDataSetChanged();
    }
}