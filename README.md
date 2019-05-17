# AndroidRSAEncryption

Android RSA Encryption Demo.

# 安装 OpenSSl

在终端中输入 openssl 如果有反应说明已经安装了 OpenSSl，否则戳下面的文章先进行安装。

[https://blog.csdn.net/Qyee16/article/details/72799852](https://blog.csdn.net/Qyee16/article/details/72799852)

# OpenSSl 工具生成密钥对

这里执行命令有两种方式：

- 方式一：进入 OpenSSL命令行模式；终端中输入 openssl 
- 方式二：不进入 OpenSSL命令行模式；终端中不需要输入 openssl 

如果是方式一执行命令不需要加 openssl 前缀，否则需要加前缀！！！

生成私钥：

```
openssl genrsa -out rsa_private_key.pem 1024
```

用私钥生成公钥：

```
openssl rsa -in ~/Desktop/rsa_private_key.pem -out rsa_public_key.pem -pubout
```

生成的私钥是不能在代码中直接使用的，需要使用 PKCS#8 编码格式化

```
openssl pkcs8 -topk8 -in ~/Desktop/rsa_private_key.pem -out pkcs8_rsa_private_key.pem -nocrypt
```

## 使用说明

1.RSAUtils中配置公钥和密钥，可以使用getKeys()方法产生。如果是客户端，则无须配置私钥，把没有私钥的RSAUtils放到客户端，
因为仅需要用到公钥加密的方法。
2.AESUtils中配置偏移量IV_STRING；
3.rsa.js中最底部配置公钥，须和上面RSAUtils配置的公钥一致；
4.aes.js中的底部var iv  = CryptoJS.enc.Utf8.parse("16-Bytes--String");   //加密向量中，替换里面的字符串，加密向量须和
是上面的AESUtils中的偏移量一致。
