package com.xpf.android.rsa.encryption.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.rsa.encryption.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.btnRsa).setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RSAEncryptionActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnAes).setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, AESEncryptionActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnDes).setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, DESEncryptionActivity.class);
            startActivity(intent);
        });
    }
}