package com.huawei.inoc.most.platform.secure.digest;

public abstract interface Digest
{
  public abstract String digest(String paramString);

  public abstract String originalDigest(String paramString);

  public abstract String digest(String paramString1, String paramString2);
}

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.digest.Digest
 * JD-Core Version:    0.6.2
 */