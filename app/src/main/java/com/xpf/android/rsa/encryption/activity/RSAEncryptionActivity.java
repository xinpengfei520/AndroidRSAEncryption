package com.xpf.android.rsa.encryption.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xpf.android.rsa.encryption.R;
import com.xpf.android.rsa.encryption.RSAUtils;
import com.xpf.android.rsa.encryption.utils.Base64Utils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by xpf on 2019.05.16 :)
 * Function:
 */
public class RSAEncryptionActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * 加密，解密按钮
     */
    private Button btn1, btn2;
    /**
     * 需加密的内容，加密后的内容，解密后的内容
     */
    private EditText et1, et2, et3;

    /**
     * 密钥内容 base64 code
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCm/lee01uUd5kChouKJC9eg4wQ\n" +
            "xshspcD5GKUYuhkEK68zztqtGYYbFWeAxPpVbOf29+FgxUv5DIzRTU7AQFVF775z\n" +
            "laGqVXBp1CZ6exV4yk/5v3CpHc4jNetH8YM+7tVXNkSoYajXQ2gdd6FG2rm7IF2z\n" +
            "fLOEmxq17n5yG8m8pwIDAQAB";

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKb+V57TW5R3mQKG\n" +
            "i4okL16DjBDGyGylwPkYpRi6GQQrrzPO2q0ZhhsVZ4DE+lVs5/b34WDFS/kMjNFN\n" +
            "TsBAVUXvvnOVoapVcGnUJnp7FXjKT/m/cKkdziM160fxgz7u1Vc2RKhhqNdDaB13\n" +
            "oUbaubsgXbN8s4SbGrXufnIbybynAgMBAAECgYAQKj7BGSScUpd1LyEC1k3fkExW\n" +
            "s2djXQg5FLGmmx0g0jm7giKY7weUR5YlWOwCqPArcANZIsAf858OA7XD1nZqjqqU\n" +
            "HSauvtIC6+skhTSLd55xHd3vUb8VNkxPvJh7Geg3FFBsSdBh/GMB29yIGHe7TY2O\n" +
            "p8Dyu4WPjVIL5/8wQQJBANCYNdLdpgMxbguDQ8+yZKlcc5WNWKmQL8wf5Kx9ngFG\n" +
            "53lP7Ui0Dfqm9OsYmZFKx4jZTa8PBZWWlukrefef6QsCQQDM8dTairg8Q27K+bZ+\n" +
            "eK5FWnm48ASaZliQJi5KLavOwRSEzfeywU3FzxxTyB0a9CBAUd4AqjTqEG0HMlCD\n" +
            "RZRVAkEAx3qYFlRS20Dc/POF2My/yNqZyk4GmPlDTFC/rVjfzlbRK8kMoPdXWvlo\n" +
            "xJ6c1T4O/UbaLGwQKhJ9tPQXyn/JKwJAce29tf+Hi3ixDoTivku4FTXGlNhYtrxO\n" +
            "X1PaR8I70CPllC4rlGOBKIWJ6clE5MbxZtAb6aK056lZ8rY1q8PyQQJBAMfYLfrr\n" +
            "8j6Fs+srS2+U7pFU53RHCjh9/m66toReaD8J1bRwAtnHBxjOTZGyfYsBjEvGu5kV\n" +
            "H2+8MYkh52Mv1TI=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_encryption);
        initView();
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 加密
            case R.id.btn1:
                encrypt();
                break;
            // 解密
            case R.id.btn2:
                decrypt();
                break;
            default:
                break;
        }
    }

    private void decrypt() {
        String encryptContent = et2.getText().toString().trim();
        Log.i(TAG, "encryptContent===" + encryptContent);

        try {
            // 从字符串中得到私钥
            PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);

            // 方式二：从文件中得到私钥
            //InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
            //PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);

            // 因为 RSA 加密后的内容经 Base64 再加密转换了一下，所以先 Base64 解密回来再给 RSA 解密
            byte[] base64Decode = Base64Utils.decode(encryptContent);
            byte[] decryptByte = RSAUtils.decryptData(base64Decode, privateKey);
            et3.setText(new String(decryptByte));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void encrypt() {
        String source = et1.getText().toString().trim();
        try {
            // 从字符串中得到公钥
            PublicKey publicKey = RSAUtils.loadPublicKey(PUBLIC_KEY);

            // 方式二：从文件中得到公钥
            //InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
            //PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);

            byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);
            // 为了方便观察吧加密后的数据用 base64 加密转一下，要不然看起来是乱码,所以解密是也是要用 Base64 先转换
            String afterEncrypt = Base64Utils.encode(encryptByte);
            et2.setText(afterEncrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
