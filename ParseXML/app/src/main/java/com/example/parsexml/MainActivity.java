package com.example.parsexml;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView lvEmployees;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lvEmployees = findViewById(R.id.lvEmployee);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lvEmployees.setAdapter(adapter);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getAssets().open("employee.xml");
                    XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = fc.newPullParser();
                    parser.setInput(inputStream, null);

                    int eventType = -1;
                    String nodeName;
                    String data = "";

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        eventType = parser.next();
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                nodeName = parser.getName();
                                if (nodeName.equals("employee")) {
                                    data += parser.getAttributeValue(0) + " - ";
                                    data += parser.getAttributeValue(1) + " - ";
                                } else if (nodeName.equals("name")) {
                                    parser.next();
                                    data += parser.getText() + " - ";
                                } else if (nodeName.equals("phone")) {
                                    data += parser.nextText();
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                nodeName = parser.getName();
                                if (nodeName.equals("employee")) {
                                    list.add(data);
                                    data = "";
                                }
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}