package com.example.intentservice_th2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageButton btnplay, btnstop;
    Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnplay = findViewById(R.id.btnplay);
        btnstop = findViewById(R.id.btnstop);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Khai báo Intent công khai để khởi động Service
                Intent intent1 = new Intent(MainActivity.this,
                        MyService.class);
                startService(intent1);
                if (flag == true){
                    btnplay.setImageResource(R.drawable.pause);
                    flag = false;
                }
                else
                {
                    btnplay.setImageResource(R.drawable.play);
                    flag = true;
                }
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new
                        Intent(MainActivity.this,MyService.class);
                stopService(intent2);
                btnplay.setImageResource(R.drawable.play);
                flag = true;
            }
        });
    }
}