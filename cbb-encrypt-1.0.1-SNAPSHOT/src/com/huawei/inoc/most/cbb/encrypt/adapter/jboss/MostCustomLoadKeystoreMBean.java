package com.huawei.inoc.most.cbb.encrypt.adapter.jboss;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.management.ObjectName;
import org.jboss.system.ServiceMBean;

public abstract interface MostCustomLoadKeystoreMBean extends ServiceMBean
{
  public abstract String getKeyStoreURL();

  public abstract void setKeyStoreURL(String paramString)
    throws IOException;

  public abstract void setKeyStorePass();

  public abstract void setKeyStorePassURL(String paramString);

  public abstract void reloadKeyAndTrustStore()
    throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException;

  public abstract ObjectName getManagerServiceName();

  public abstract void setManagerServiceName(ObjectName paramObjectName);

  public abstract byte[] encode(byte[] paramArrayOfByte);

  public abstract byte[] decode(byte[] paramArrayOfByte);

  public abstract String encode64(byte[] paramArrayOfByte);

  public abstract byte[] decode64(String paramString);
}

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.adapter.jboss.MostCustomLoadKeystoreMBean
 * JD-Core Version:    0.6.2
 */