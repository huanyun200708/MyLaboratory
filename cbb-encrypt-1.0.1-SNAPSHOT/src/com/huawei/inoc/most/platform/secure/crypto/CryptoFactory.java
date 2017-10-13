 package com.huawei.inoc.most.platform.secure.crypto;
 
 import com.huawei.inoc.most.platform.secure.crypto.impl.AES128;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public final class CryptoFactory
 {
   private static final Logger LOGGER = Logger.getLogger(CryptoFactory.class.getName());
 
   public static Crypto getInstance(CryptoAlgorithm algorithm)
   {
     Crypto instance = null;
 
     if (null == algorithm)
     {
       return null;
     }
 
     if ("AES128".equals(algorithm.toString()))
     {
       instance = new AES128();
     }
     else
     {
       LOGGER.log(Level.SEVERE, "Can not support this algorithm: " + algorithm.toString());
     }
     return instance;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.crypto.CryptoFactory
 * JD-Core Version:    0.6.2
 */