package com.example.internetworking;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    String URl = "https://api.androidhive.info/pizza/?format=xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnParse);
        lv = findViewById(R.id.listview);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadExampleTask().execute();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> result = new ArrayList<>();
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                XMLParse xmlParser = new XMLParse();
                String xml = xmlParser.getXmlFromUrl(URl);
                parser.setInput(new StringReader(xml));
                int eventType = parser.getEventType();
                String nodeName;
                String data = "";
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("id")) {
                                data += parser.nextText() + " - ";
                            } else if (nodeName.equals("name")) {
                                data += parser.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                result.add(data);
                                data = "";  // Reset data after adding to result
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (strings != null && !strings.isEmpty()) {
                adapter.clear();
                adapter.addAll(strings);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
