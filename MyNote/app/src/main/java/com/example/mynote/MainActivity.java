package com.example.mynote;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tvToday;
    EditText edtHour, edtMinute, edtWork;
    Button btnAddWork;
    ListView lvWork;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvToday = findViewById(R.id.tvToday);
        edtHour = findViewById(R.id.edtHour);
        edtMinute = findViewById(R.id.edtMinute);
        edtWork = findViewById(R.id.edtWork);
        btnAddWork = findViewById(R.id.btnAddWork);
        lvWork = findViewById(R.id.lvWork);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvWork.setAdapter(arrayAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = sdf.format(currentDate);
        tvToday.setText("Hôm nay: " + formattedDate);

        SharedPreferences sharedPreferences = getSharedPreferences("note_list_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        arrayList.clear();
        if(sharedPreferences.contains("work")){
            String work = sharedPreferences.getString("work", "");
            String[] workArray = work.substring(1, work.length() - 1).split(", ");
            for(String w : workArray){
                arrayList.add(w);
            }
        }

        btnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWork.getText().toString().isEmpty() || edtHour.getText().toString().isEmpty() || edtMinute.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                String work = edtWork.getText().toString();
                int hour = Integer.parseInt(edtHour.getText().toString());
                int minute = Integer.parseInt(edtMinute.getText().toString());

                if(hour < 0 || hour > 24 || minute < 0 || minute > 60){
                    Toast.makeText(getApplicationContext(), "Thời gian không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String workConcat = work + " - " + hour + " : " + edtMinute.getText().toString();
                arrayList.add(workConcat);
                arrayAdapter.notifyDataSetChanged();

                if(sharedPreferences.contains("work")){
                    editor.remove("work");
                }
                editor.putString("work", arrayList.toString());
                editor.apply();

                edtMinute.setText("");
                edtHour.setText("");
                edtWork.requestFocus();
                edtWork.setText("");
            }
        });
    }
}