package com.huawei.inoc.most.platform.secure.crypto;

public abstract interface Crypto
{
  public abstract String encrypt(String paramString);

  public abstract String encrypt(String paramString1, String paramString2);

  public abstract String decrypt(String paramString);

  public abstract String decrypt(String paramString1, String paramString2);
}

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.crypto.Crypto
 * JD-Core Version:    0.6.2
 */