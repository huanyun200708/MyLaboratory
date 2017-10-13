 package com.huawei.inoc.most.cbb.encrypt;
 
 import com.huawei.inoc.most.platform.secure.crypto.Crypto;
 import com.huawei.inoc.most.platform.secure.crypto.CryptoAlgorithm;
 import com.huawei.inoc.most.platform.secure.crypto.CryptoFactory;
 
 public final class AesEncrypt
 {
   public static String encrypt(String textToEncrypt)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).encrypt(textToEncrypt);
   }
 
   public static String decrypt(String textToDecrypt)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).decrypt(textToDecrypt);
   }
 
   public static String encrypt(String textToEncrypt, String key)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).encrypt(textToEncrypt, key);
   }
 
   public static String decrypt(String textToDecrypt, String key)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).decrypt(textToDecrypt, key);
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.AesEncrypt
 * JD-Core Version:    0.6.2
 */