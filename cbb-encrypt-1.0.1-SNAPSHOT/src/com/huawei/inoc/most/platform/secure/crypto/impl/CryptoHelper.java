 package com.huawei.inoc.most.platform.secure.crypto.impl;
 
 import com.huawei.inoc.most.platform.secure.crypto.Crypto;
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 import com.huawei.inoc.most.platform.secure.utils.KeyManagerV2;
 import com.huawei.inoc.most.platform.secure.utils.KeyUtils;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public abstract class CryptoHelper
   implements Crypto
 {
   private static final Logger LOGGER = Logger.getLogger(CryptoHelper.class.getName());
   private static final String ROOT_KEY_FLAG = "rootkey";
 
   public String encrypt(String plain)
   {
     return encrypt(plain, KeyManagerV2.getWorkKey());
   }
 
   public String encrypt(String plain, String key)
   {
     if ((null == key) || (key.isEmpty()))
     {
       LOGGER.log(Level.WARNING, "Encrypt: key is not available!");
       return plain;
     }
     byte[] byteKey;
     if ("rootkey".equals(key.trim().toLowerCase()))
     {
       byteKey = KeyManagerV2.genRootKeyFromComponent();
     }
     else
     {
       String workKeyStr = decryptCBC(key, KeyManagerV2.genRootKeyFromComponent());
 
       if ((null == workKeyStr) || (workKeyStr.isEmpty()))
       {
         LOGGER.log(Level.WARNING, "Aecrypt: decrypt work key failure!");
         return plain;
       }
 
       byteKey = KeyUtils.decodeHex(workKeyStr.toCharArray());
     }
 
     return encrypt(plain, byteKey);
   }
 
   public abstract String encrypt(String paramString, byte[] paramArrayOfByte);
 
   public String decrypt(String cipher)
   {
     if ((null == cipher) || (cipher.isEmpty()))
     {
       return cipher;
     }
 
     String plain = decryptCBC(cipher);
 
     if (!cipher.equals(plain))
     {
       return plain;
     }
 
     plain = decryptECB(cipher);
 
     return plain;
   }
 
   public String decrypt(String cipher, String key)
   {
     if ((null == cipher) || (cipher.isEmpty()) || (null == key) || (key.isEmpty()))
     {
       return cipher;
     }
 
     String plain = decryptCBC(cipher, key);
 
     if (!cipher.equals(plain))
     {
       return plain;
     }
 
     plain = decryptECB(cipher, key);
 
     return plain;
   }
 
   public String decryptECB(String cipher)
   {
     return decryptECB(cipher, KeyManager.getInitialKey());
   }
 
   public abstract String decryptECB(String paramString1, String paramString2);
 
   public String decryptCBC(String cipher)
   {
     return decryptCBC(cipher, KeyManagerV2.getWorkKey());
   }
 
   public String decryptCBC(String cipher, String key)
   {
     if ((null == key) || (key.isEmpty()))
     {
       LOGGER.log(Level.WARNING, "Decrypt: key is not available!");
       return cipher;
     }
     byte[] byteKey;
     if ("rootkey".equals(key.trim().toLowerCase()))
     {
       byteKey = KeyManagerV2.genRootKeyFromComponent();
     }
     else
     {
       String workKeyStr = decryptCBC(key, KeyManagerV2.genRootKeyFromComponent());
 
       if ((null == workKeyStr) || (workKeyStr.isEmpty()))
       {
         LOGGER.log(Level.WARNING, "Decrypt: decrypt work key failure!");
         return cipher;
       }
 
       byteKey = KeyUtils.decodeHex(workKeyStr.toCharArray());
     }
 
     return decryptCBC(cipher, byteKey);
   }
 
   public abstract String decryptCBC(String paramString, byte[] paramArrayOfByte);
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.crypto.impl.CryptoHelper
 * JD-Core Version:    0.6.2
 */