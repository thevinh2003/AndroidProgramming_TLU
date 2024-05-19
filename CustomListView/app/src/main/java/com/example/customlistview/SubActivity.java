package com.example.customlistview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        tvName = findViewById(R.id.tvSelected);
        android.content.Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tvName.setText(name);
    }
}