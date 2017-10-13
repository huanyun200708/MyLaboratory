 package com.huawei.inoc.most.platform.secure.pbkdf2;
 
 import com.huawei.inoc.most.platform.secure.pbkdf2.impl.PBKDF2WithHmacSHA256;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public final class PBKDF2Factory
 {
   private static final Logger LOGGER = Logger.getLogger(PBKDF2Factory.class.getName());
 
   public static PBKDF2 getInstance(PBKDF2Algorithm algorithm)
   {
     PBKDF2 instance = null;
 
     if (null == algorithm)
     {
       return null;
     }
 
     if (PBKDF2Algorithm.PBKDF2WithHmacSHA256.toString().equals(algorithm.toString()))
     {
       instance = new PBKDF2WithHmacSHA256();
     }
     else
     {
       LOGGER.log(Level.SEVERE, "Can not support this algorithm: " + algorithm.toString());
     }
 
     return instance;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2Factory
 * JD-Core Version:    0.6.2
 */