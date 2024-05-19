package com.example.temporator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtC, txtF;
    Button btnToC, btnToF, btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtC = findViewById(R.id.txtC);
        txtF = findViewById(R.id.txtF);
        btnToC = findViewById(R.id.btnToC);
        btnToF = findViewById(R.id.btnToF);
        btnClear = findViewById(R.id.btnClear);

        btnToF.setOnClickListener(this);
        btnToC.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnToC) {
            float fTemp = Float.parseFloat(txtF.getText().toString());
            float cTemp = (fTemp - 32) * 5/9;
            txtC.setText(String.valueOf(cTemp));
        }
        if(v.getId() == R.id.btnToF) {
            float cTemp = Float.parseFloat(txtC.getText().toString());
            float fTemp = cTemp * 9/5 + 32;
            Log.d("click", "check click");
            txtF.setText(String.valueOf(fTemp));
        }
        if(v.getId() == R.id.btnClear) {
            txtF.setText("");
            txtC.setText("");
        }
    }
}