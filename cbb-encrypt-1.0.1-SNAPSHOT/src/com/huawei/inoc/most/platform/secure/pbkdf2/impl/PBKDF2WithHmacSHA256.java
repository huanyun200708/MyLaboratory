 package com.huawei.inoc.most.platform.secure.pbkdf2.impl;
 
 import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2;
 import org.bouncycastle.crypto.digests.SHA256Digest;
 import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
 import org.bouncycastle.crypto.params.KeyParameter;
 
 public class PBKDF2WithHmacSHA256
   implements PBKDF2
 {
   public byte[] getKey(byte[] pArray, byte[] sArray)
   {
     return getKey(pArray, sArray, 50000);
   }
 
   public byte[] getKey(byte[] pArray, byte[] sArray, int iterationCount)
   {
     PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
     gen.init(pArray, sArray, iterationCount);
     byte[] dk = ((KeyParameter)gen.generateDerivedParameters(128)).getKey();
     return dk;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.pbkdf2.impl.PBKDF2WithHmacSHA256
 * JD-Core Version:    0.6.2
 */