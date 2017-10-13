 package com.huawei.inoc.most.cbb.encrypt;
 
 import com.huawei.inoc.most.platform.secure.facade.SecureFacade;
 import com.huawei.inoc.most.platform.secure.utils.KeyUtils;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.util.logging.Logger;
 
 public final class ExportKeyTool
 {
   private static final Logger LOG = Logger.getLogger(EncryptTool.class.getName());
 
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
 
     String usage = "Illegal arguments! Usage:" + lineSeparator + "    [ null | password salt ]";
     try
     {
       if ((null == args) || (args.length == 0))
       {
         out.println(SecureFacade.exportFactory());
       }
       else if (args.length == 2)
       {
         if ((null == args[0]) || ("".equals(args[0])) || (null == args[1]) || ("".equals(args[1])))
         {
           out.println(usage);
         }
         else
         {
           out.println(KeyUtils.encodeHex(SecureFacade.derivedKey(args[0].getBytes(), args[1].getBytes())));
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
     catch (Exception e)
     {
       LOG.info(e.toString());
       out.println(usage);
 
       if (null != out)
       {
         out.close();
       }
     }
     finally
     {
       if (null != out)
       {
         out.close();
       }
     }
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.ExportKeyTool
 * JD-Core Version:    0.6.2
 */