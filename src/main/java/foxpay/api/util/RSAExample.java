package foxpay.api.util;


import cn.hutool.core.io.file.FileReader;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAExample {


    // 签名
    public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }


    // 签名
    public static String sign(byte[] data, String privateKeyStr) throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(x509EncodedKeySpec);

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(data);

        return Base64.getEncoder().encodeToString(signature.sign());
    }

    // 验证签名
    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA1withRSA");
        verifier.initVerify(publicKey);
        verifier.update(data);
        return verifier.verify(signature);
    }


    // 验证签名
    public static boolean verify(byte[] data, byte[] signature, String publicKeyStr) throws Exception {
        // Base64解码签名
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        // 创建公钥对象
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        // 创建公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature verifier = Signature.getInstance("SHA1withRSA");
        verifier.initVerify(publicKey);
        verifier.update(data);
        return verifier.verify(signatureBytes);
    }


    public static String extractFromPem(String privateKey) {
        String pkcs1_begin = "-----BEGIN RSA PRIVATE KEY-----";
        String pkcs1_end = "-----END RSA PRIVATE KEY-----";
        String pkcs8_begin = "-----BEGIN PRIVATE KEY-----";
        String pkcs8_end = "-----END PRIVATE KEY-----";
        String pkcs1_pub_begin = "-----BEGIN RSA PUBLIC KEY-----";
        String pkcs1_pub_end = "-----END RSA PUBLIC KEY-----";
        String pkcs8_pub_begin = "-----BEGIN PUBLIC KEY-----";
        String pkcs8_pub_end = "-----END PUBLIC KEY-----";
        privateKey = privateKey.replace(pkcs1_begin, "");
        privateKey = privateKey.replace(pkcs1_end, "");
        privateKey = privateKey.replace(pkcs8_begin, "");
        privateKey = privateKey.replace(pkcs8_end, "");
        privateKey = privateKey.replace(pkcs1_pub_begin, "");
        privateKey = privateKey.replace(pkcs1_pub_end, "");
        privateKey = privateKey.replace(pkcs8_pub_begin, "");
        privateKey = privateKey.replace(pkcs8_pub_end, "");
        //去换行符空格
        privateKey = privateKey.replaceAll("\\s+", "");
        return privateKey;
    }

    public static void a() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // 要签名的数据
        byte[] data = "Hello, World!".getBytes();
        // 签名
        byte[] signature = sign(data, privateKey);
        // 验证签名
        boolean isValid = verify(data, signature, publicKey);
        System.out.println("Signature is valid? " + isValid);

    }

    public static void b() throws Exception {
        FileReader publicFile = new FileReader("E:\\public.pem");


    }


    public static void main(String[] args) throws Exception{
        b();

    }
}
