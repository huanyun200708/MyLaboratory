 package com.huawei.inoc.most.platform.secure.utils;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.security.SecureRandom;
 import java.util.Properties;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public final class KeyManager
 {
   private static final Logger LOGGER = Logger.getLogger(KeyManager.class.getName());
 
   private static byte[] initialSalt = null;
   private static final int RANDOM_NUM = 2147483647;
   private static final int SALT_LENGTH = 16;
   private static final int CHARNUM = 62;
   private static final int DIGIT_NUM = 10;
   private static final int LOWERCASE_NUM = 26;
   private static byte[] initialPlainText = null;
 
   private static String[] initialKey = null;
 
   static
   {
     initProperties();
     initKeyFactor();
     initInitSalt();
   }
 
   private static void initProperties()
   {
     try
     {
       Properties prop = loadResProperties();
 
       String strKey = null;
 
       if (prop != null)
       {
         strKey = prop.getProperty("key");
       }
 
       initialKey = saveAsArray(strKey);
     }
     catch (IOException e)
     {
       LOGGER.log(Level.WARNING, "Load properties error!");
     }
   }
 
   private static void initInitSalt()
   {
     initialSalt = new byte[16];
     initialSalt[0] = 87;
     initialSalt[1] = 76;
     initialSalt[2] = 48;
     initialSalt[3] = 110;
     initialSalt[4] = 111;
     initialSalt[5] = 111;
     initialSalt[6] = 107;
     initialSalt[7] = 118;
     initialSalt[8] = 90;
     initialSalt[9] = 116;
     initialSalt[10] = 49;
     initialSalt[11] = 52;
     initialSalt[12] = 115;
     initialSalt[13] = 80;
     initialSalt[14] = 84;
     initialSalt[15] = 103;
   }
 
   private static void initKeyFactor()
   {
     initialPlainText = new byte[23];
     initialPlainText[0] = 67;
     initialPlainText[1] = 86;
     initialPlainText[2] = 49;
     initialPlainText[3] = 36;
     initialPlainText[4] = 35;
     initialPlainText[5] = 64;
     initialPlainText[6] = 33;
     initialPlainText[7] = 99;
     initialPlainText[8] = 118;
     initialPlainText[9] = 68;
     initialPlainText[10] = 70;
     initialPlainText[11] = 71;
     initialPlainText[12] = 53;
     initialPlainText[13] = 49;
     initialPlainText[14] = 50;
     initialPlainText[15] = 51;
     initialPlainText[16] = 68;
     initialPlainText[17] = 71;
     initialPlainText[18] = 70;
     initialPlainText[19] = 71;
     initialPlainText[20] = 51;
     initialPlainText[21] = 52;
     initialPlainText[22] = 50;
   }
 
   public static String getInitialPlainText()
   {
     if (initialPlainText == null)
     {
       return null;
     }
 
     return new String(initialPlainText);
   }
 
   public static String getInitialKey()
   {
     if (initialKey == null)
     {
       return null;
     }
 
     return initialKey[0] + initialKey[1] + initialKey[2];
   }
 
   public static String getInitialSalt()
   {
     if (initialSalt == null)
     {
       return null;
     }
 
     return new String(initialSalt);
   }
 
   private static String[] saveAsArray(String src)
   {
     if ((src == null) || (src.isEmpty()))
     {
       return null;
     }
 
     String[] result = new String[3];
 
     int keyLength = src.length();
 
     result[0] = src.substring(0, keyLength / 3);
     result[1] = src.substring(keyLength / 3, 2 * keyLength / 3);
     result[2] = src.substring(2 * keyLength / 3);
 
     return result;
   }
 
   private static Properties loadResProperties()
     throws IOException
   {
     File file = new File(new File("").getCanonicalPath());
 
     File outerKeyFile = getOuterKeyFile(file);
 
     InputStream is = null;
 
     if (outerKeyFile == null)
     {
       ClassLoader cl = KeyManager.class.getClassLoader();
       if (cl != null)
       {
         is = cl.getResourceAsStream("InitialParameter.properties");
       }
 
     }
     else
     {
       is = new FileInputStream(outerKeyFile);
     }
 
     Properties property = new Properties();
     try
     {
       if (is != null)
       {
         property.load(is);
       }
       else
       {
         LOGGER.log(Level.SEVERE, "loadResProperties: get keyfile failed");
       }
     }
     catch (IOException e)
     {
       LOGGER.log(Level.SEVERE, "Load properties error!");
       try
       {
         if (is != null)
         {
           is.close();
         }
       }
       catch (IOException e1)
       {
         LOGGER.log(Level.WARNING, "Close file error!");
       }
     }
     finally
     {
       try
       {
         if (is != null)
         {
           is.close();
         }
       }
       catch (IOException e)
       {
         LOGGER.log(Level.WARNING, "Close file error!");
       }
     }
 
     return property;
   }
 
   private static File getOuterKeyFile(File file)
   {
     if ((file == null) || (file.isFile()))
     {
       LOGGER.log(Level.SEVERE, "Can not find key.properties!");
       return null;
     }
 
     try
     {
       File target = 
         new File(file.getCanonicalPath() + "/encrypt" + "/" + "key.properties");
       if (target.exists())
       {
         return target;
       }
 
       target = 
         new File(file.getCanonicalPath() + "/common/encrypt" + "/" + "key.properties");
       if (target.exists())
       {
         return target;
       }
 
       if (file.getParent() == null)
       {
         return null;
       }
       return getOuterKeyFile(new File(file.getParent()));
     }
     catch (IOException e)
     {
       LOGGER.log(Level.SEVERE, "Can not find key.properties!");
     }
 
     return null;
   }
 
   public static String generateSalt()
   {
     SecureRandom random = new SecureRandom();
 
     int result = 0;
 
     int charIndex = 0;
 
     StringBuffer salt = new StringBuffer();
 
     int saltLength = 0;
 
     char ch = '\000';
 
     while ((result > 0) || (saltLength < 16))
     {
       if (result == 0)
       {
         result = random.nextInt(2147483647);
       }
 
       charIndex = result % 62;
 
       if (charIndex < 10)
       {
         ch = '0';
         ch = (char)(ch + charIndex);
       }
       else if (charIndex < 36)
       {
         ch = 'a';
         ch = (char)(ch + (charIndex - 10));
       }
       else
       {
         ch = 'A';
         ch = (char)(ch + (charIndex - 10 - 26));
       }
 
       salt.append(ch);
 
       if (salt.length() == 16)
       {
         break;
       }
 
       result /= 62;
 
       saltLength = salt.length();
     }
 
     return salt.toString();
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.utils.KeyManager
 * JD-Core Version:    0.6.2
 */