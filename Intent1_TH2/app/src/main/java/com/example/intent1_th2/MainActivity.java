package com.example.intent1_th2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtResult;
    Button btnRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.receiveA);
        edtB = findViewById(R.id.receiveB);
        edtResult = findViewById(R.id.edtResult);
        btnRequest = findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a = Float.parseFloat("0" + edtA.getText().toString());
                float b = Float.parseFloat("0" + edtB.getText().toString());

                if(a == 0) edtA.setText("0");
                if(b == 0) edtB.setText("0");

                Intent myIntent = new Intent(MainActivity.this, SolveActivity.class);
                myIntent.putExtra("soA", a);
                myIntent.putExtra("soB", b);

                startActivityForResult(myIntent, 99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 33) {
            float sum = data.getFloatExtra("result",0);
            edtResult.setText("Tổng 2 số là: " + String.format("%.2f", sum));
        }
        if(requestCode == 99 && resultCode == 34) {
            float sub = data.getFloatExtra("result",0);
            edtResult.setText("Hiệu 2 số là: " + String.format("%.2f", sub));
        }
    }
}