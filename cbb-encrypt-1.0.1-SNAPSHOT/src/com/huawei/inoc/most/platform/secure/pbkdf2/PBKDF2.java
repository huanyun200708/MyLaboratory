package com.huawei.inoc.most.platform.secure.pbkdf2;

public abstract interface PBKDF2
{
  public abstract byte[] getKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt);

  public abstract byte[] getKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
}

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2
 * JD-Core Version:    0.6.2
 */