package com.example.ontap_book;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    Spinner spAuthor;
    EditText edtBookName, edtPrice;
    ArrayList<String> listAuthor;
    ArrayAdapter<String> adapterAuthor;
    SQLiteDatabase database = null;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        database = openOrCreateDatabase("book_management.db", MODE_PRIVATE, null);

        edtBookName = findViewById(R.id.edtBookName);
        edtPrice = findViewById(R.id.edtBookPrice);
        spAuthor = findViewById(R.id.spAuthor);
        btnAdd = findViewById(R.id.btnAddBook);
        listAuthor = new ArrayList<>();
        adapterAuthor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listAuthor);
        adapterAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthor.setAdapter(adapterAuthor);

        loadAuthor();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtBookName.getText().toString();
                String price = edtPrice.getText().toString();
                int authorPosition = spAuthor.getSelectedItemPosition();

                if (name.isEmpty() || price.isEmpty() || authorPosition == -1) {
                    Toast.makeText(AddActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                String author = listAuthor.get(authorPosition);

                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("price", price);
                values.put("author_id", authorPosition + 1); // assuming author_id is position + 1

                if (database.insert("books", null, values) == -1) {
                    Toast.makeText(AddActivity.this, "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
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
