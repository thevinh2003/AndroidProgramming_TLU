package com.example.ontap_book;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    private String DB_PATH_SUFFIX = "/databases/";
    private SQLiteDatabase database = null;
    private String DATABASE_NAME = "book_management.db";
    private Button btnAdd;
    private ListView lv;
    private ArrayList<Book> listBook;
    private ArrayAdapter<Book> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        processCopy();
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        btnAdd = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lvBook);
        listBook = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listBook);
        lv.setAdapter(adapter);
        loadBook();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = listBook.get(position);
                String name = book.getName();
                int price = book.getPrice();
                int author = book.getAuthor_id();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("author", author);
                startActivity(intent);
            }
        });
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

    private void loadBook() {
        listBook.clear();
        String query = "SELECT * FROM books INNER JOIN authors ON books.author_id = authors.id";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String price = cursor.getString(2);
                String author = cursor.getString(3);
                listBook.add(new Book(id, name, Integer.parseInt(price), author);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            loadBook();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}