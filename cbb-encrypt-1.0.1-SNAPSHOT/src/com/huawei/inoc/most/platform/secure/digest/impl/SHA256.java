 package com.huawei.inoc.most.platform.secure.digest.impl;
 
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 import java.io.UnsupportedEncodingException;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public class SHA256 extends DigestHelper
 {
   private static final Logger LOGGER = Logger.getLogger(SHA256.class.getName());
   private static final String SHA_TYPE_NAME = "SHA-256";
   private static final String CHARACTERENCODING = "ISO-8859-1";
   private static final String ZERO = "0";
 
   public String digest(String plain, String salt)
   {
     if (null == plain)
     {
       return null;
     }
     if (null == salt)
     {
       salt = KeyManager.getInitialSalt();
     }
     if (null == salt)
     {
       return plain;
     }
 
     String textWithSalt = addSalt(plain, salt);
 
     if (null == textWithSalt)
     {
       return plain;
     }
 
     return doDigest(textWithSalt);
   }
 
   private String doDigest(String plain)
   {
     String encryptedText = null;
     try
     {
       MessageDigest sha = MessageDigest.getInstance("SHA-256");
 
       byte[] decryptedBytes = plain.getBytes("ISO-8859-1");
 
       byte[] encryptedBytes = sha.digest(decryptedBytes);
 
       encryptedText = bytes2HexString(encryptedBytes);
     }
     catch (NoSuchAlgorithmException e)
     {
       LOGGER.log(Level.SEVERE, "No such algorithm SHA-256!");
     }
     catch (UnsupportedEncodingException e)
     {
       LOGGER.log(Level.SEVERE, "Un supported encoding ISO-8859-1!");
     }
 
     return encryptedText;
   }
 
   private static String bytes2HexString(byte[] bts)
   {
     StringBuffer sbDes = new StringBuffer("");
     String tmp = null;
     int byteLength = bts.length;
     for (int i = 0; i < byteLength; i++)
     {
       tmp = Integer.toHexString(bts[i] & 0xFF);
       if (1 == tmp.length())
       {
         sbDes.append("0");
       }
 
       sbDes.append(tmp);
     }
     return sbDes.toString();
   }
 
   private static String addSalt(String plain, String salt)
   {
     int textLength = plain.length();
     int saltLength = salt.length();
 
     if (0 == textLength)
     {
       return salt;
     }
 
     int segLength = saltLength / textLength;
 
     int pos = saltLength % textLength;
 
     StringBuffer textWithSalt = new StringBuffer();
 
     String sub = null;
 
     if (segLength > 0)
     {
       for (int i = 0; i < textLength; i++)
       {
         textWithSalt.append(plain.charAt(i));
         if (pos > 0)
         {
           sub = salt.substring(i * (segLength + 1), (i + 1) * (segLength + 1));
 
           textWithSalt.append(sub);
           pos--;
         }
         else
         {
           sub = salt.substring(pos + i * segLength, pos + (i + 1) * segLength);
 
           textWithSalt.append(sub);
         }
       }
     }
     else
     {
       for (int i = 0; i < pos; i++)
       {
         textWithSalt.append(plain.charAt(i));
 
         textWithSalt.append(salt.charAt(i));
       }
       textWithSalt.append(plain.substring(pos));
     }
 
     return textWithSalt.toString();
   }
 
   public String originalDigest(String plain)
   {
     return doDigest(plain);
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.digest.impl.SHA256
 * JD-Core Version:    0.6.2
 */