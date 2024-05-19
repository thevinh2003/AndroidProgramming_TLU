package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText txtName, txtHeight, txtWeight, txtResultBMI, txtDiagnose;
    Button btnSolve;
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

        txtName = findViewById(R.id.txtName);
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtResultBMI = findViewById(R.id.txtResultBMI);
        txtDiagnose = findViewById(R.id.txtDiagnose);
        btnSolve = findViewById(R.id.btnSolve);

        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                float height = Float.parseFloat("0" + txtHeight.getText().toString());
                float weight = Float.parseFloat("0" + txtWeight.getText().toString());

                if(name.isEmpty() || height == 0 || weight == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra thông tin", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                float resultBMI = weight / (height * height);
                txtResultBMI.setText(String.format("%.2f", resultBMI));

                if(resultBMI < 18){
                    txtDiagnose.setText("Gầy");
                    return;
                }
                else if(resultBMI <= 24.9){
                    txtDiagnose.setText("Bình thường");
                    return;
                }
                else if(resultBMI <= 29.9){
                    txtDiagnose.setText("Béo phì độ I");
                    return;
                }
                else if(resultBMI <= 34.9){
                    txtDiagnose.setText("Béo phì độ II");
                    return;
                }
                else {
                    txtDiagnose.setText("Béo phì độ III");
                    return;
                }
            }
        });
    }
}