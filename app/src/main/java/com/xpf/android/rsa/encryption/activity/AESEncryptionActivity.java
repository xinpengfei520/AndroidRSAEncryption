package com.xpf.android.rsa.encryption.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.rsa.encryption.R;
import com.xpf.android.rsa.encryption.utils.AesUtils;

public class AESEncryptionActivity extends AppCompatActivity {

    private static final String TAG = "AESEncryptionActivity";
    private static String KEY = "";
    private EditText etInput;
    private TextView tvContent;
    private String encode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes_encryption);
        etInput = findViewById(R.id.etInput);
        Button btnEncrypt = findViewById(R.id.btnEncrypt);
        Button btnDecrypt = findViewById(R.id.btnDecrypt);
        tvContent = findViewById(R.id.tvContent);

        KEY = AesUtils.generateKey();
        Log.d(TAG, "KEY:" + KEY);

        btnEncrypt.setOnClickListener(v -> encrypt());
        btnDecrypt.setOnClickListener(v -> decrypt());
    }

    private void encrypt() {
        String encryptContent = etInput.getText().toString().trim();
        encode = AesUtils.encode(KEY, encryptContent);
        tvContent.setText(encode);
    }

    private void decrypt() {
        String decode = AesUtils.decode(KEY, encode);
        tvContent.setText(decode);
    }
}