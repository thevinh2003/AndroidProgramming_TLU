package com.example.photoviewer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewPhoto extends AppCompatActivity {
    ImageView imageView;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_photo);

        imageView = findViewById(R.id.subPhotoView);
        btnBack = findViewById(R.id.btnBack);

        android.content.Intent intent = getIntent();
        int image = intent.getIntExtra("image", 0);
        imageView.setImageResource(image);

        btnBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });
    }
}