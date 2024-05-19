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

import java.net.URI;

public class CallActivity extends AppCompatActivity {
    Button btnBack;
    EditText edtPhoneNumber;
    ImageView iconCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);

        btnBack = findViewById(R.id.btnBack);
        iconCall = findViewById(R.id.iconCall);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        iconCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                if(phoneNumber.isEmpty()) {edtPhoneNumber.requestFocus(); return;}

                if (!isVietnamesePhoneNumber(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

                if (ActivityCompat.checkSelfPermission(CallActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CallActivity.this, new
                            String[]{android.Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(callIntent);
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