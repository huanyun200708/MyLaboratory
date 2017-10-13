 package com.huawei.inoc.most.cbb.encrypt;
 
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.util.logging.Logger;
 
 public final class DecryptTool
 {
   private static final Logger LOG = Logger.getLogger(DecryptTool.class.getName());
   private static final int ARGS_LENGTH_ONE = 1;
   private static final int ARGS_LENGTH_TWO = 2;
 
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
 
     if (null == args)
     {
       out.println("Illegal arguments length! Usage: text [SecretKey]");
       out.close();
       return;
     }
 
     if (1 == args.length)
     {
       out.println(AesEncrypt.decrypt(args[0]));
     }
     else if (2 == args.length)
     {
       if ((args[1] == null) || ("".equals(args[1].trim())))
       {
         out.println(AesEncrypt.decrypt(args[0]));
       }
       else
       {
         out.println(AesEncrypt.decrypt(args[0], args[1]));
       }
     }
     else
     {
       out.println("Illegal arguments length! Usage: text [SecretKey]");
     }
 
     if (null != out)
     {
       out.close();
     }
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.DecryptTool
 * JD-Core Version:    0.6.2
 */