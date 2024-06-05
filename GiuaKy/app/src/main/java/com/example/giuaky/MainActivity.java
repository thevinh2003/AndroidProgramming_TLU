package com.example.giuaky;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    Button btnThemDonVi;
    SearchView searchView;
    ListView lvDonVi;
    ListView lvNhanVien;
    SQLiteDatabase db;
    ArrayList<DonVi> list;
    ArrayList<NhanVien> listNhanVien;
    ArrayAdapterContactItem<DonVi> adapterDonVi;
    ArrayAdapterContactItem<NhanVien> adapterNhanVien;
    private String DB_PATH_SUFFIX = "/databases/";
    private String DATABASE_NAME = "giuaky.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("giuaky.db", MODE_PRIVATE, null);
        processCopy();
        addControl();
        tab1_action();
    }

    private void processCopy() {
        File dbFile = new File(getDBPath(DATABASE_NAME));
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copy database successful", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getDBPath(String DATABASE_NAME) {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + this.DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDBPath(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addControl() {
        //Định nghĩa tabHost
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec spec1, spec2;
        //Tab1
        spec1 = tabHost.newTabSpec("t1");
        spec1.setContent(R.id.tab_donvi);
        spec1.setIndicator("Đơn vị");
        tabHost.addTab(spec1);
        //Tab2
        spec2 = tabHost.newTabSpec("t2");
        spec2.setContent(R.id.tab_nhanvien);
        spec2.setIndicator("Nhân viên");
        tabHost.addTab(spec2);

        //Khi chuyển tab
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("t2")) {
                    tab2_action();
                }
                else {
                    tab1_action();
                }
            }
        });

        //Ánh xạ ID
        btnThemDonVi = findViewById(R.id.btnThemDonVi);
        searchView = findViewById(R.id.svDonVi);
        lvDonVi = findViewById(R.id.lvDonVi);
        lvNhanVien = findViewById(R.id.lvNhanVien);
        list = new ArrayList<DonVi>();
        listNhanVien = new ArrayList<NhanVien>();
        adapterDonVi = new ArrayAdapterContactItem<DonVi>(this, R.layout.layout_contact_item, list);
        adapterNhanVien = new ArrayAdapterContactItem<NhanVien>(this, R.layout.layout_contact_item, listNhanVien);
        lvNhanVien.setAdapter(adapterNhanVien);
        lvDonVi.setAdapter(adapterDonVi);
    }

    public void tab1_action(){
        list.clear();
        String query = "SELECT * FROM DonVi";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String TenDonVi = cursor.getString(1);
                String Email = cursor.getString(2);
                String website = cursor.getString(3);
                String Logo = cursor.getString(4);
                if(Logo == null) Logo = "";
                String DiaChi = cursor.getString(5);
                String DienThoai = cursor.getString(6);
                int DonViCha = cursor.getInt(7);
                list.add(new DonVi(id, TenDonVi, Email, website, Logo, DiaChi, DienThoai, DonViCha));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapterDonVi.notifyDataSetChanged();
    }

    public void tab2_action(){
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
                String AnhDaiDien = cursor.getString(5);
                int DonViID = cursor.getInt(6);
                listNhanVien.add(new NhanVien(id, Hoten, ChucVu, Email, DienThoai, AnhDaiDien, DonViID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapterNhanVien.notifyDataSetChanged();
    }
}