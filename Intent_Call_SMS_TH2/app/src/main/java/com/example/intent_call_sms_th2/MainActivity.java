package com.example.intent_call_sms_th2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnCall, btnSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.btnSMS);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myItent = new Intent(MainActivity.this, CallActivity.class);
                startActivity(myItent);
            }
        });

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myItent = new Intent(MainActivity.this, SMSActivity.class);
                startActivity(myItent);
            }
        });
    }
}