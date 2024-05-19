package com.example.intent_call_sms_th2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSActivity extends AppCompatActivity {
    Button btnBack;
    EditText edtPhoneNumber;
    ImageView iconSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smsactivity);

        btnBack = findViewById(R.id.btnBack);
        iconSMS = findViewById(R.id.iconSMS);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        iconSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                if(phoneNumber.isEmpty()) {edtPhoneNumber.requestFocus(); return;}
                if (!isVietnamesePhoneNumber(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("smsto:"+phoneNumber));
                startActivity(smsIntent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isVietnamesePhoneNumber(String phoneNumber) {
        String vietnamPhoneRegex = "^(0[0-9]{9,10})$";
        return phoneNumber.matches(vietnamPhoneRegex);
    }
}