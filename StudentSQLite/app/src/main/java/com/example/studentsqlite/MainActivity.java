package com.example.studentsqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView edtMalop, edtTenlop, edtSiso;
    Button btnThem, btnSua, btnXoa;
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtMalop = findViewById(R.id.edtmalop);
        edtTenlop =findViewById(R.id.edtTenLop);
        edtSiso = findViewById(R.id.edtSiso);
        btnThem = findViewById(R.id.btnInsert);
        btnSua = findViewById(R.id.btnUpdate);
        btnXoa = findViewById(R.id.btnDelete);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);

        db = openOrCreateDatabase("qlsv.db", MODE_PRIVATE, null);
        try {
            db.execSQL("create table lop(malop text primary key, tenlop text, siso integer)");
        } catch (Exception e) {
            Log.e("LOI", e.toString());
        }

        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMalop.getText().toString().isEmpty() || edtTenlop.getText().toString().isEmpty() || edtSiso.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống các trường", Toast.LENGTH_SHORT).show();
                    return;
                }
                String malop = edtMalop.getText().toString();
                String tenlop = edtTenlop.getText().toString();
                int siso = Integer.parseInt("0" + edtSiso.getText().toString());
                if(siso <= 0) {
                    Toast.makeText(getApplicationContext(), "Sĩ số phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra mã lớp đã tồn tại chưa
                Cursor c = db.query("lop", null, "malop=?", new String[]{malop}, null, null, null);
                if(c.getCount() > 0) {
                    Toast.makeText(getApplicationContext(), "Mã lớp đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues row = new ContentValues();
                row.put("malop", malop);
                row.put("tenlop", tenlop);
                row.put("siso", siso);

                if(db.insert("lop", null, row) != -1) {
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    edtMalop.setText("");
                    edtTenlop.setText("");
                    edtSiso.setText("");
                    loadData();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] arr = list.get(position).split(" - ");
                edtMalop.setText(arr[0]);
                edtTenlop.setText(arr[1]);
                edtSiso.setText(arr[2]);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtMalop.getText().toString();
                if (malop.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mã lớp không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Dialog xác nhận xóa
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa lớp " + malop + "?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý xóa dữ liệu
                        if (db.delete("lop", "malop=?", new String[]{malop}) != 0) {
                            Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            edtMalop.setText("");
                            edtTenlop.setText("");
                            edtSiso.setText("");
                            loadData();
                        } else {
                            Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMalop.getText().toString().isEmpty() || edtTenlop.getText().toString().isEmpty() || edtSiso.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống các trường", Toast.LENGTH_SHORT).show();
                    return;
                }
                String malop = edtMalop.getText().toString();
                String tenlop = edtTenlop.getText().toString();
                int siso = Integer.parseInt("0" + edtSiso.getText().toString());
                if(siso <= 0) {
                    Toast.makeText(getApplicationContext(), "Sĩ số phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues row = new ContentValues();
                row.put("tenlop", tenlop);
                row.put("siso", siso);

                if(db.update("lop", row, "malop=?", new String[]{malop}) != 0) {
                    Toast.makeText(getApplicationContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    edtMalop.setText("");
                    edtTenlop.setText("");
                    edtSiso.setText("");
                    loadData();
                } else {
                    Toast.makeText(getApplicationContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        list.clear();
        Cursor c = db.query("lop", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String malop = c.getString(0);
            String tenlop = c.getString(1);
            int siso = c.getInt(2);
            list.add(malop + " - " + tenlop + " - " + siso);
            c.moveToNext();
        }
        c.close();
        adapter.notifyDataSetChanged();
    }
}