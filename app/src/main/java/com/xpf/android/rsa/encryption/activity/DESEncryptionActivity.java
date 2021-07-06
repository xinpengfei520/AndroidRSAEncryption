package com.xpf.android.rsa.encryption.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.rsa.encryption.R;
import com.xpf.android.rsa.encryption.utils.Des3Utils;

public class DESEncryptionActivity extends AppCompatActivity {

    private EditText etInput;
    private TextView tvContent;
    private String encode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_encryption);
        etInput = findViewById(R.id.etInput);
        Button btnEncrypt = findViewById(R.id.btnEncrypt);
        Button btnDecrypt = findViewById(R.id.btnDecrypt);
        tvContent = findViewById(R.id.tvContent);

        btnEncrypt.setOnClickListener(v -> encrypt());
        btnDecrypt.setOnClickListener(v -> decrypt());
    }

    private void encrypt() {
        String encryptContent = etInput.getText().toString().trim();
        try {
            encode = Des3Utils.encode(encryptContent);
            tvContent.setText(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decrypt() {
        try {
            String decode = Des3Utils.decode(encode);
            tvContent.setText(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}