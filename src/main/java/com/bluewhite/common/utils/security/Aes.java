package com.bluewhite.common.utils.security;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class Aes {
	// 测试
	public static void main(String args[]) {
		// // 待加密内容
		String str = "123456";
		try {
			String a = encrypt(str);
			System.out.println("加密后：" + a);
			System.out.println("解密后：" + decrypt(a));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'A', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };

	public static String encrypt(String data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes());
		
		String encryptedValue = Base64.encodeBase64String(encVal);
		return encryptedValue;
	}
	
	public static String decrypt(String data) throws Exception{  
        Cipher cipher = Cipher.getInstance(ALGO);  
        Key securekey = generateKey();  
        cipher.init(Cipher.DECRYPT_MODE, securekey);  
        //byte[] res = new BASE64Decoder().decodeBuffer(data);
        byte[] res = Base64.decodeBase64(data);
        res = cipher.doFinal(res);  
        return new String(res);  
    } 
	
	public static String encryptUTF8(String data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes("utf-8"));
		//String encryptedValue = new BASE64Encoder().encode(encVal);
		String encryptedValue = Base64.encodeBase64String(encVal);
		return encryptedValue;
	}
	
	public static String decryptUTF8(String data) throws Exception{  
        Cipher cipher = Cipher.getInstance(ALGO);  
        Key securekey = generateKey();  
        cipher.init(Cipher.DECRYPT_MODE, securekey);  
        //byte[] res = new BASE64Decoder().decodeBuffer(data);
        byte[] res = Base64.decodeBase64(data);
        res = cipher.doFinal(res);  
        return new String(res,"utf-8");  
    }
	
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

}
