 package com.huawei.inoc.most.platform.secure.digest.impl;
 
 import com.huawei.inoc.most.platform.secure.digest.Digest;
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 
 public abstract class DigestHelper
   implements Digest
 {
   public String digest(String plain)
   {
     return digest(plain, KeyManager.getInitialSalt());
   }
 
   public abstract String digest(String paramString1, String paramString2);
 
   public abstract String originalDigest(String paramString);
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.digest.impl.DigestHelper
 * JD-Core Version:    0.6.2
 */