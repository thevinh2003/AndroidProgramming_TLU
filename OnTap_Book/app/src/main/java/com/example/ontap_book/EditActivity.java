package com.example.ontap_book;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    Spinner spAuthor;
    EditText edtBookName, edtPrice;
    ArrayList<String> listAuthor;
    ArrayAdapter<String> adapterAuthor;
    SQLiteDatabase database = null;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        database = openOrCreateDatabase("book_management.db", MODE_PRIVATE, null);

        edtBookName = findViewById(R.id.edtBookName);
        edtPrice = findViewById(R.id.edtBookPrice);
        spAuthor = findViewById(R.id.spAuthor);
        btnEdit = findViewById(R.id.btnEditBook);
        listAuthor = new ArrayList<>();
        adapterAuthor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listAuthor);
        adapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthor.setAdapter(adapterAuthor);
    }

    private void loadAuthor() {
        listAuthor.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Authors", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            listAuthor.add(name);
        }
        cursor.close();
        adapterAuthor.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}