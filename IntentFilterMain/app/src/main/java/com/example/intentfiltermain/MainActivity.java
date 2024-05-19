package com.example.intentfiltermain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnopen;
    EditText edtlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        edtlink = findViewById(R.id.edtUrl);
        btnopen = findViewById(R.id.btnopen);

//        btnopen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://" + edtlink.getText().toString()));
//                startActivity(intent);
//            }
//        });
    }
}