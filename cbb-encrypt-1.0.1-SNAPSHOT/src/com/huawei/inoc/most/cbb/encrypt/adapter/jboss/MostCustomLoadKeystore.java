package com.huawei.inoc.most.cbb.encrypt.adapter.jboss;
 
 import com.huawei.inoc.most.platform.secure.facade.SecureFacade;
 import java.io.BufferedInputStream;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.URL;
 import java.security.KeyStore;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.UnrecoverableKeyException;
 import java.security.cert.CertificateException;
 import java.util.Arrays;
 import java.util.Properties;
 import javax.management.InstanceNotFoundException;
 import javax.management.MBeanException;
 import javax.management.MBeanServer;
 import javax.management.ObjectName;
 import javax.management.ReflectionException;
 import javax.net.ssl.KeyManagerFactory;
 import javax.net.ssl.TrustManagerFactory;
 import javax.security.auth.callback.CallbackHandler;
 import org.jboss.logging.Logger;
 import org.jboss.mx.util.MBeanServerLocator;
 import org.jboss.security.SecurityDomain;
 import org.jboss.security.auth.callback.SecurityAssociationHandler;
 import org.jboss.security.plugins.JaasSecurityManager;
 import org.jboss.security.plugins.JaasSecurityManagerServiceMBean;
 
 public class MostCustomLoadKeystore extends JaasSecurityManager
   implements SecurityDomain, MostCustomLoadKeystoreMBean
 {
   private KeyStore keyStore;
   private KeyManagerFactory keyMgr;
   private static final String STORE_TYPE = "JKS";
   private URL keyStoreURL;
   private char[] keyStorePassword;
   private String keyStorePassURL;
   private ObjectName managerServiceName = JaasSecurityManagerServiceMBean.OBJECT_NAME;
   private TrustManagerFactory trustMgr;
 
   public String getKeyStorePassURL()
   {
     return this.keyStorePassURL;
   }
 
   public void setKeyStorePassURL(String keyStorePassURL)
   {
     this.keyStorePassURL = keyStorePassURL;
     this.log.debug("Using KeyStorePassUrl=" + this.keyStoreURL.toExternalForm());
     setKeyStorePass();
   }
 
   public MostCustomLoadKeystore()
   {
   }
 
   public MostCustomLoadKeystore(String securityDomain)
   {
     this(securityDomain, new SecurityAssociationHandler());
   }
 
   public MostCustomLoadKeystore(String securityDomain, CallbackHandler handler)
   {
     super(securityDomain, handler);
   }
 
   public KeyStore getKeyStore()
   {
     return this.keyStore;
   }
 
   public KeyManagerFactory getKeyManagerFactory()
   {
     return this.keyMgr;
   }
 
   public KeyStore getTrustStore()
   {
     return this.keyStore;
   }
 
   public TrustManagerFactory getTrustManagerFactory()
   {
     return this.trustMgr;
   }
 
   public ObjectName getManagerServiceName()
   {
     return this.managerServiceName;
   }
 
   public void setManagerServiceName(ObjectName managerServiceName)
   {
     this.managerServiceName = managerServiceName;
   }
 
   public String getKeyStoreURL()
   {
     String url = null;
     if (this.keyStoreURL != null)
     {
       url = this.keyStoreURL.toExternalForm();
     }
     return url;
   }
 
   public void setKeyStoreURL(String storeURL)
     throws IOException
   {
     this.keyStoreURL = new URL(storeURL);
     this.log.debug("Using KeyStore=" + this.keyStoreURL.toExternalForm());
   }
 
   public void setKeyStorePass()
   {
     Properties prop = new Properties();
     InputStream in = null;
     try
     {
       in = new BufferedInputStream(new FileInputStream(this.keyStorePassURL));
       prop.load(in);
 
       if (null != in)
       {
         try
         {
           in.close();
         }
         catch (IOException e)
         {
           this.log.error("Close resource file failed!", e);
         }
       }
     }
     catch (IllegalArgumentException e)
     {
       this.log.error("find resource file failed!", e);
 
       if (null != in)
       {
         try
         {
           in.close();
         }
         catch (IOException ex)
         {
           this.log.error("Close resource file failed!", ex);
         }
       }
     }
     catch (IOException e)
     {
       this.log.error("find resource file failed!", e);
 
       if (null != in)
       {
         try
         {
           in.close();
         }
         catch (IOException e1)
         {
           this.log.error("Close resource file failed!", e1);
         }
       }
     }
     finally
     {
       if (null != in)
       {
         try
         {
           in.close();
         }
         catch (IOException e)
         {
           this.log.error("Close resource file failed!", e);
         }
       }
     }
 
     String password = prop.getProperty("UI_CA_PWD_CIPHER").trim();
 
     String pwd = SecureFacade.decrypt(password);
 
     if ((null == pwd) || (pwd.isEmpty()))
     {
       return;
     }
     this.keyStorePassword = pwd.toCharArray();
   }
 
   public String getName()
   {
     return "JaasSecurityDomain(" + getSecurityDomain() + ")";
   }
 
   public byte[] encode(byte[] secret)
   {
     return SecureFacade.encrypt(new String(secret)).getBytes();
   }
 
   public byte[] decode(byte[] secret)
   {
     return SecureFacade.decrypt(new String(secret)).getBytes();
   }
 
   public String encode64(byte[] secret)
   {
     return SecureFacade.encrypt(new String(secret));
   }
 
   public byte[] decode64(String secret)
   {
     return decode(secret.getBytes());
   }
 
   public void reloadKeyAndTrustStore()
     throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
   {
     loadKeyAndTrustStore();
   }
 
   protected void startService()
     throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InstanceNotFoundException, ReflectionException, MBeanException
   {
     loadKeyAndTrustStore();
 
     if (this.managerServiceName != null)
     {
       MBeanServer server = MBeanServerLocator.locateJBoss();
       Object[] params = { getSecurityDomain(), this };
       String[] signature = { "java.lang.String", "org.jboss.security.SecurityDomain" };
       server.invoke(this.managerServiceName, "registerSecurityDomain", params, signature);
     }
   }
 
   protected void stopService()
   {
     if (this.keyStorePassword != null)
     {
       Arrays.fill(this.keyStorePassword, '\000');
       this.keyStorePassword = null;
     }
   }
 
   private void loadKeyAndTrustStore()
     throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException
   {
     if (this.keyStoreURL != null)
     {
       this.keyStore = KeyStore.getInstance("JKS");
       InputStream is = this.keyStoreURL.openStream();
       this.keyStore.load(is, this.keyStorePassword);
       String algorithm = KeyManagerFactory.getDefaultAlgorithm();
       this.keyMgr = KeyManagerFactory.getInstance(algorithm);
       this.keyMgr.init(this.keyStore, this.keyStorePassword);
     }
 
     if (this.keyStore != null)
     {
       String algorithm = TrustManagerFactory.getDefaultAlgorithm();
       this.trustMgr = TrustManagerFactory.getInstance(algorithm);
       this.trustMgr.init(this.keyStore);
     }
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.adapter.jboss.MostCustomLoadKeystore
 * JD-Core Version:    0.6.2
 */