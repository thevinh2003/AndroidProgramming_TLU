package com.example.vnexpress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
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

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   static ListView lv;
   ArrayList<List> newsList;
   MyAdapter adapter;
   String nodeName, title = "", description = "", des = "", urlStr = "", link = "" , URl = "https://vnexpress.net/rss/tin-moi-nhat.rss";
   Bitmap mIcon_val = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        newsList = new ArrayList<>();
        adapter = new MyAdapter(this, R.layout.layout_listview, newsList);
        lv.setAdapter(adapter);
        LoadExample task = new LoadExample();
        task.execute();
    }

    class LoadExample extends AsyncTask<Void, Void, ArrayList<List>> {
        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            newsList = new ArrayList<List>();
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                XMLParser xmlParser = new XMLParser();
                String xml = xmlParser.getXML(URl);
                parser.setInput(new StringReader(xml));
                int eventType = -1;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("title")) {
                                title = parser.nextText();
                            } else if (nodeName.equals("link")) {
                                link = parser.nextText();
                            } else if (nodeName.equals("description")) {
                                description = parser.nextText();
                                try {
                                    urlStr = description.substring(description.indexOf("src=") + 5, description.indexOf("><a/>") - 2);
                                }
                                catch (Exception e) {
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                des = description.substring(description.indexOf("</br>") + 5);
                                try {
                                    URL news = new URL(urlStr);
                                    mIcon_val = BitmapFactory.decodeStream(news.openConnection().getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                newsList.add(new List(title, des, link, mIcon_val));
                            }
                            break;
                    }
                }
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return newsList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<List> lists) {
            super.onPostExecute(lists);
            adapter.clear();
            adapter.addAll(lists);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}