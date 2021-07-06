package com.xpf.android.rsa.encryption.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xpf.android.rsa.encryption.R;

public class DESEncryptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_encryption);
        EditText etInput = findViewById(R.id.etInput);
        Button btnEncrypt = findViewById(R.id.btnEncrypt);
        Button btnDecrypt = findViewById(R.id.btnDecrypt);
        TextView tvContent = findViewById(R.id.tvContent);

        // TODO: 2021/7/6
    }
}