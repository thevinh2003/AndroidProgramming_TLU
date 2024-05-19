package com.example.intent1_th2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SolveActivity extends AppCompatActivity {
    Intent subIntent;
    EditText edtA, edtB;
    Button btnSum, btnSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_solve);

        edtA = findViewById(R.id.receiveA);
        edtB = findViewById(R.id.receiveB);
        btnSum = findViewById(R.id.btnSum);
        btnSub = findViewById(R.id.btnSub);

        subIntent = getIntent();
        float a = subIntent.getFloatExtra("soA", 0);
        float b = subIntent.getFloatExtra("soB", 0);

        edtA.setText(a+"");
        edtB.setText(b+"");

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sum = a + b;
                subIntent.putExtra("result", sum);
                setResult(33, subIntent);
                finish();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sub = a - b;
                subIntent.putExtra("result", sub);
                setResult(34, subIntent);
                finish();
            }
        });
    }
}