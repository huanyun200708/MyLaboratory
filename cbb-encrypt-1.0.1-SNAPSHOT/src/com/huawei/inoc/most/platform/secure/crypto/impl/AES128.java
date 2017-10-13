 package com.huawei.inoc.most.platform.secure.crypto.impl;
 
 import com.huawei.inoc.most.platform.secure.utils.KeyManager;
 import com.huawei.inoc.most.platform.secure.utils.KeyUtils;
 import java.io.UnsupportedEncodingException;
 import java.security.InvalidAlgorithmParameterException;
 import java.security.InvalidKeyException;
 import java.security.NoSuchAlgorithmException;
 import java.security.SecureRandom;
 import java.util.Locale;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import javax.crypto.BadPaddingException;
 import javax.crypto.Cipher;
 import javax.crypto.IllegalBlockSizeException;
 import javax.crypto.NoSuchPaddingException;
 import javax.crypto.spec.IvParameterSpec;
 import javax.crypto.spec.SecretKeySpec;
 
 public class AES128 extends CryptoHelper
 {
   private static final Logger LOGGER = Logger.getLogger(AES128.class.getName());
   private static final String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
   private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
   private static final String ALGORITHM = "AES";
   private static final int KEY_LENGTH = 16;
   private static final int TEXT_GROUP_LEN = 16;
   private static final int NUMBER_FOUR = 4;
   private static final int NUMBER_TWO = 2;
   private static final String STRING_ZERO = "0";
 
   public String decryptECB(String cipher, String key)
   {
     if ((cipher == null) || (cipher.isEmpty()) || (key == null) || (key.isEmpty()))
     {
       LOGGER.log(Level.SEVERE, "Decrypt: cipher is null or key is not available!");
       return cipher;
     }
 
     String strSecretKey = doEncrypt(key, KeyManager.getInitialPlainText());
 
     if ((strSecretKey != null) && (!strSecretKey.isEmpty()))
     {
       return doDecrypt(cipher, strSecretKey);
     }
 
     LOGGER.log(Level.SEVERE, "AesEncrypt encrypt: error");
     return cipher;
   }
 
   private static String doDecrypt(String cipher, String secretKey)
   {
     String decryptdText = "";
     try
     {
       Cipher cipherInstance = Cipher.getInstance("AES/ECB/NoPadding");
 
       byte[] secretKeyBytes = oneZeroPaddingWithKey(secretKey.getBytes("UTF-8"));
 
       byte[] bytesToDecrypt = hexString2Bytes(cipher);
 
       SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
 
       cipherInstance.init(2, secretKeySpec);
 
       byte[] decryptedBytes = cipherInstance.doFinal(bytesToDecrypt);
 
       byte[] finalBytes = oneZeroRTrim(decryptedBytes);
 
       if (finalBytes == null)
       {
         decryptdText = "";
       }
       else
       {
         decryptdText = new String(finalBytes, "UTF-8");
       }
     }
     catch (InvalidKeyException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
     catch (NoSuchAlgorithmException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
     catch (NoSuchPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
     catch (UnsupportedEncodingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
     catch (IllegalBlockSizeException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
     catch (BadPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipher;
     }
 
     return decryptdText;
   }
 
   private static byte[] hexString2Bytes(String hexString)
   {
     byte[] dstBytes = new byte[0];
     if (hexString != null)
     {
       String srcHex = hexString;
 
       if (hexString.length() % 2 == 0)
       {
         srcHex = hexString;
       }
       else
       {
         srcHex = "0" + hexString;
       }
 
       srcHex = srcHex.toUpperCase(Locale.getDefault());
 
       int iDstBytes = srcHex.length() / 2;
 
       dstBytes = new byte[iDstBytes];
 
       int hexCharIndex = 0;
 
       char hexChar = '\000';
 
       for (int i = 0; i < iDstBytes; i++)
       {
         hexChar = srcHex.charAt(hexCharIndex);
         dstBytes[i] = ((byte)(hexChar - 'A'));
         hexCharIndex++;
         hexChar = srcHex.charAt(hexCharIndex);
         int tmp104_102 = i;
         byte[] tmp104_101 = dstBytes; tmp104_101[tmp104_102] = ((byte)(tmp104_101[tmp104_102] + (byte)((byte)(hexChar - 'A') << 4)));
         hexCharIndex++;
       }
     }
     return dstBytes;
   }
 
   private static byte[] oneZeroRTrim(byte[] srcBytes)
   {
     int iCount = srcBytes[(srcBytes.length - 1)] & 0xFF;
 
     int iRemainBytes = srcBytes.length - iCount;
 
     if (iRemainBytes <= 0)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt oneZeroRTrim: invalid srcBytes");
     }
 
     byte[] dstBytes = new byte[iRemainBytes];
 
     System.arraycopy(srcBytes, 0, dstBytes, 0, iRemainBytes);
 
     return dstBytes;
   }
 
   private static String doEncrypt(String plain, String secretKey)
   {
     if ((plain == null) || (plain.isEmpty()) || (secretKey == null) || (secretKey.isEmpty()))
     {
       return plain;
     }
 
     String strEcryptedText = "";
     try
     {
       Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
 
       byte[] secretKeyBytes = oneZeroPaddingWithKey(secretKey.getBytes("UTF-8"));
 
       byte[] bytesToEncrypt = plain.getBytes("UTF-8");
 
       SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
 
       cipher.init(1, secretKeySpec);
 
       byte[] plainBytes = oneZeroPaddingWithText(bytesToEncrypt);
 
       byte[] encryptedBytes = cipher.doFinal(plainBytes);
 
       strEcryptedText = bytes2HexString(encryptedBytes);
     }
     catch (NoSuchAlgorithmException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
     catch (NoSuchPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
     catch (UnsupportedEncodingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
     catch (InvalidKeyException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
     catch (IllegalBlockSizeException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
     catch (BadPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES: encrypt error!");
 
       return plain;
     }
 
     return strEcryptedText;
   }
 
   private static byte[] oneZeroPaddingWithKey(byte[] srcBytes)
   {
     int iFinalLen = 16;
 
     byte[] dstText = new byte[iFinalLen];
 
     int iPadLen = iFinalLen - srcBytes.length;
 
     if (iPadLen <= 0)
     {
       System.arraycopy(srcBytes, 0, dstText, 0, iFinalLen);
     }
     else
     {
       System.arraycopy(srcBytes, 0, dstText, 0, srcBytes.length);
 
       for (int i = srcBytes.length; i < iFinalLen; i++)
       {
         dstText[i] = ((byte)iPadLen);
       }
     }
     return dstText;
   }
 
   private static byte[] oneZeroPaddingWithText(byte[] srcBytes)
   {
     int iFinalLen = (srcBytes.length / 16 + 1) * 16;
 
     byte[] dstText = new byte[iFinalLen];
 
     int iPadLen = iFinalLen - srcBytes.length;
 
     System.arraycopy(srcBytes, 0, dstText, 0, srcBytes.length);
 
     for (int i = srcBytes.length; i < iFinalLen; i++)
     {
       dstText[i] = ((byte)iPadLen);
     }
 
     return dstText;
   }
 
   private static String bytes2HexString(byte[] bytes)
   {
     StringBuffer hexString = new StringBuffer("");
     if (bytes != null)
     {
       char temp = '\000';
 
       for (int i = 0; i < bytes.length; i++)
       {
         temp = 'A';
         temp = (char)(temp + (bytes[i] & 0xF));
         hexString.append(temp);
         temp = 'A';
         temp = (char)(temp + (bytes[i] >> 4 & 0xF));
         hexString.append(temp);
       }
     }
     return hexString.toString();
   }
 
   public String encrypt(String plain, byte[] byteKey)
   {
     if ((plain == null) || (plain.isEmpty()) || (byteKey == null) || (byteKey.length != 16))
     {
       LOGGER.log(Level.SEVERE, "Encrypt: plain is null or key is not available!");
       return plain;
     }
 
     try
     {
       byte[] ivp = new byte[16];
       SecureRandom sr = new SecureRandom();
       sr.nextBytes(ivp);
       IvParameterSpec iv = new IvParameterSpec(ivp);
 
       SecretKeySpec sKey = new SecretKeySpec(byteKey, "AES");
 
       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
 
       cipher.init(Cipher.ENCRYPT_MODE, sKey, iv);
 
       byte[] cipherBytes = cipher.doFinal(plain.getBytes());
 
       byte[] result = new byte[16 + cipherBytes.length];
 
       System.arraycopy(ivp, 0, result, 0, 16);
       System.arraycopy(cipherBytes, 0, result, 16, cipherBytes.length);
 
       return new String(KeyUtils.encodeHex(result));
     }
     catch (NoSuchAlgorithmException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
       return plain;
     }
     catch (NoSuchPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
       return plain;
     }
     catch (InvalidKeyException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
       return plain;
     }
     catch (InvalidAlgorithmParameterException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
       return plain;
     }
     catch (IllegalBlockSizeException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
       return plain;
     }
     catch (BadPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt encryptAES [CBC]: encrypt error!");
     }return plain;
   }
 
   public String decryptCBC(String cipherText, byte[] byteKey)
   {
     try
     {
       byte[] targetBytes = KeyUtils.decodeHex(cipherText.toCharArray());
 
       if ((targetBytes == null) || (targetBytes.length < 32))
       {
         return cipherText;
       }
 
       byte[] ivp = new byte[16];
       System.arraycopy(targetBytes, 0, ivp, 0, 16);
       IvParameterSpec iv = new IvParameterSpec(ivp);
 
       byte[] cipherBytes = new byte[targetBytes.length - 16];
       System.arraycopy(targetBytes, 16, cipherBytes, 0, targetBytes.length - 16);
 
       SecretKeySpec sKey = new SecretKeySpec(byteKey, "AES");
       // ´´½¨ÃÜÂëÆ÷
       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
       cipher.init(Cipher.DECRYPT_MODE, sKey, iv);
 
       byte[] plainBytes = cipher.doFinal(cipherBytes);
 
       return new String(plainBytes);
     }
     catch (NoSuchAlgorithmException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipherText;
     }
     catch (NoSuchPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipherText;
     }
     catch (InvalidAlgorithmParameterException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipherText;
     }
     catch (IllegalBlockSizeException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipherText;
     }
     catch (BadPaddingException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
 
       return cipherText;
     }
     catch (InvalidKeyException e)
     {
       LOGGER.log(Level.SEVERE, "AesEncrypt decryptAES: decrypt error!");
     }
 
     return cipherText;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.platform.secure.crypto.impl.AES128
 * JD-Core Version:    0.6.2
 */