 package com.huawei.inoc.most.cbb.encrypt;
 
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.util.logging.Logger;
 
 public final class EncryptTool
 {
   private static final Logger LOG = Logger.getLogger(EncryptTool.class.getName());
   private static final int ARGS_LENGTH_TWO = 2;
   private static final int ARGS_LENGTH_THREE = 3;
   private static final String AES = "-aes";
   private static final String SHA = "-sha";
 
   public static void main(String[] args)
   {
     PrintStream out = null;
     try
     {
       out = new PrintStream(System.out, false, "UTF-8");
     }
     catch (UnsupportedEncodingException e)
     {
       LOG.warning("Unsupport this charset UTF-8!");
       return;
     }
 
     String lineSeparator = System.getProperty("line.separator");
 
     String usage = "Illegal arguments! Usage:" + lineSeparator + "    -aes text [key]" + lineSeparator + "    -sha text [salt]";
 
     if ((null == args) || ((args.length != 2) && (args.length != 3)))
     {
       out.println(usage);
       out.close();
       return;
     }
     if ("-aes".equalsIgnoreCase(args[0]))
     {
       if (2 == args.length)
       {
         out.println(AesEncrypt.encrypt(args[1]));
       }
       else if (3 == args.length)
       {
         if ((args[2] == null) || ("".equals(args[2].trim())))
         {
           out.println(AesEncrypt.encrypt(args[1]));
         }
         else
         {
           out.println(AesEncrypt.encrypt(args[1], args[2]));
         }
       }
     }
     else if ("-sha".equalsIgnoreCase(args[0]))
     {
       if (2 == args.length)
       {
         out.println(ShaEncrypt.encrypt(args[1], null));
       }
       else if (3 == args.length)
       {
         if (null == args[2])
         {
           out.println(ShaEncrypt.encrypt(args[1], null));
         }
         else if ("".equals(args[2].trim()))
         {
           out.println(ShaEncrypt.encrypt(args[1], null));
         }
         else
         {
           out.println(ShaEncrypt.encrypt(args[1], args[2]));
         }
       }
     }
     else
     {
       out.println(usage);
     }
 
     if (null != out)
     {
       out.close();
     }
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.EncryptTool
 * JD-Core Version:    0.6.2
 */