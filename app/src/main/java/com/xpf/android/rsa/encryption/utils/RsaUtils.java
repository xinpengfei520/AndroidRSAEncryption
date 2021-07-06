package com.xpf.android.rsa.encryption.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author x-sir :)
 * @date 2021/5/17
 */
public class RsaUtils {

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuQue3tJHQi+wm0vDThx/YUgSE+IVlJ7K2aHtmzbflmZDP1ruVZlRxBvxzT2aw0savKRNOCc/brHqv2xEoMRRM39NFLuDvtshM0gA8pCfUypukml1dye6sDrUbMprndaAEgsPaSBQOOkzpHvLq3wsTr3jyDUUYn9oyokXmuCUT5wIDAQAB";
    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK5C57e0kdCL7CbS8NOHH9hSBIT4hWUnsrZoe2bNt+WZkM/Wu5VmVHEG/HNPZrDSxq8pE04Jz9useq/bESgxFEzf00Uu4O+2yEzSADykJ9TKm6SaXV3J7qwOtRsymud1oASCw9pIFA46TOke8urfCxOvePINRRif2jKiRea4JRPnAgMBAAECgYBQYSkL+hzmQpxXniYak/Hy8IYIJbA2JBURgWkmVs6z990tmUFmXNCnYA8TSzU1a8vcoMg4T1tS2yCuPBCSZu0S8l3WVYZ8dylfjVyaLbbACseIxWEWNb9w5selQ2fflpOQfHci27IV+To8E3aqDyeBHd0xbuMYgVfvXtp8oraceQJBAO1sQ5Yp6icoLzwOlluRukf71WFbHEUPTdPYRRqwJG9KZd7XxSgzKUL6ot+OVzatyunSEQhVlUFiC+F9OM++9JkCQQC75XlODJvz5yTKW9UEzxT/OcEqZtpi3ro55IETxUExn9d9S83USNK/YoXiAyvdS+ijYrANrvD+sd3xOJHzkRx/AkBjg0ZM0WKoaOprZL3WwZzvgPlt5e9SbtizG2nThtKupuNN5OhpMhBwV3ce9p1NeMic+oRTFvQJRJwwNfky8hm5AkBD7mzy6OAhSDom5xiWyn+e3rSVreXaxQezEogQK8qsNEvmQtfBniqDgw1Ab0EWmvEa0P0xlUQP8SJ6qacq1hXlAkEAw9BUx+OeB9HuWROrYwm8Fxrpu+6Mn1wg7eHGpL+i9iRpj3La2MWRw78IJOFQ7FF0jdKhjhZITHDTV9Z3/3y0ZQ==";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] decodedKey = Base64.decode(privateKey.getBytes(), Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decode(publicKey.getBytes(), Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher;
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encode(encryptedData, Base64.DEFAULT));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decode(data, Base64.DEFAULT);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encode(signature.sign(), Base64.DEFAULT));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decode(sign.getBytes(), Base64.DEFAULT));
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {
        try {
            // 生成密钥对
//            KeyPair keyPair = getKeyPair();
//            String privateKey = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
//            String publicKey = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
//            System.out.println("私钥:" + privateKey);
//            System.out.println("公钥:" + publicKey);
            // RSA加密
            String data = "待加密的文字内容";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);

            // RSA签名
            String sign = sign(data, getPrivateKey(privateKey));
            // RSA验签
            boolean result = verify(data, getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
