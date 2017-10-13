 package com.huawei.inoc.most.platform.secure.digest;
 
 import com.huawei.inoc.most.platform.secure.digest.impl.SHA256;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public final class DigestFactory
 {
   private static final Logger LOGGER = Logger.getLogger(DigestFactory.class.getName());
 
   public static Digest getInstance(DigestAlgorithm algorithm)
   {
     Digest instance = null;
 
     if (null == algorithm)
     {
       return null;
     }
 
     if ("SHA256".equals(algorithm.toString()))
     {
       instance = new SHA256();
     }
     else
     {
       LOGGER.log(Level.SEVERE, "Can not support this algorithm: " + algorithm.toString());
     }
 
     return instance;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.digest.DigestFactory
 * JD-Core Version:    0.6.2
 */