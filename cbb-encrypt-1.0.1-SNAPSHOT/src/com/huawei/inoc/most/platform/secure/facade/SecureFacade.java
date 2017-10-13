 package com.huawei.inoc.most.platform.secure.facade;
 
 import com.huawei.inoc.most.platform.secure.crypto.Crypto;
 import com.huawei.inoc.most.platform.secure.crypto.CryptoAlgorithm;
 import com.huawei.inoc.most.platform.secure.crypto.CryptoFactory;
 import com.huawei.inoc.most.platform.secure.digest.Digest;
 import com.huawei.inoc.most.platform.secure.digest.DigestAlgorithm;
 import com.huawei.inoc.most.platform.secure.digest.DigestFactory;
 import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2;
 import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2Algorithm;
 import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2Factory;
 import com.huawei.inoc.most.platform.secure.utils.KeyGeneratorV2;
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 import com.huawei.inoc.most.platform.secure.utils.KeyUtils;
 
 public final class SecureFacade
 {
   public static String encrypt(String plain)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).encrypt(plain);
   }
 
   public static String encrypt(String plain, String key)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).encrypt(plain, key);
   }
 
   public static String decrypt(String cipher)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).decrypt(cipher);
   }
 
   public static String decrypt(String cipher, String key)
   {
     return CryptoFactory.getInstance(CryptoAlgorithm.AES128).decrypt(cipher, key);
   }
 
   public static String digest(String plain)
   {
     return DigestFactory.getInstance(DigestAlgorithm.SHA256).digest(plain);
   }
 
   public static String digest(String plain, String salt)
   {
     return DigestFactory.getInstance(DigestAlgorithm.SHA256).digest(plain, salt);
   }
 
   public static String originalDigest(String plain)
   {
     return DigestFactory.getInstance(DigestAlgorithm.SHA256).originalDigest(plain);
   }
 
   public static String exportFactory()
   {
     return KeyGeneratorV2.exportFactory();
   }
 
   public static byte[] derivedKey(byte[] pArray, byte[] sArray)
   {
     return PBKDF2Factory.getInstance(PBKDF2Algorithm.PBKDF2WithHmacSHA256).getKey(pArray, sArray);
   }
 
   public static byte[] decodeHex(char[] data)
   {
     return KeyUtils.decodeHex(data);
   }
 
   public static char[] encodeHex(byte[] data)
   {
     return KeyUtils.encodeHex(data);
   }
 
   public static boolean exportRootKeyComponent()
   {
     return KeyGeneratorV2.genRootKeyComponent();
   }
 
   public static String exportSalt()
   {
     return KeyManager.generateSalt();
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.facade.SecureFacade
 * JD-Core Version:    0.6.2
 */