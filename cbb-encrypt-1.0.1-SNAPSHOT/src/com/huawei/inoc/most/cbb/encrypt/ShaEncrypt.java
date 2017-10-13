 package com.huawei.inoc.most.cbb.encrypt;
 
 import com.huawei.inoc.most.platform.secure.digest.Digest;
 import com.huawei.inoc.most.platform.secure.digest.DigestAlgorithm;
 import com.huawei.inoc.most.platform.secure.digest.DigestFactory;
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 
 public final class ShaEncrypt
 {
   public static final String SALT = KeyManager.getInitialSalt();
 
   public static String getSalt()
   {
     return KeyManager.generateSalt();
   }
 
   public static String encrypt(String textToEncrypt, String salt)
   {
     return DigestFactory.getInstance(DigestAlgorithm.SHA256).digest(textToEncrypt, salt);
   }
 
   public static String encrypt(char[] textToEncrypt, String salt)
   {
     return DigestFactory.getInstance(DigestAlgorithm.SHA256).digest(new String(textToEncrypt), salt);
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.ShaEncrypt
 * JD-Core Version:    0.6.2
 */