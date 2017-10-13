 package com.huawei.inoc.most.cbb.encrypt;
 
 import com.huawei.inoc.most.platform.secure.facade.SecureFacade;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.util.logging.Logger;
 
 public final class ExportSaltTool
 {
   private static final Logger LOG = Logger.getLogger(ExportSaltTool.class.getName());
 
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
 
     out.println(SecureFacade.exportSalt());
 
     out.close();
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.ExportSaltTool
 * JD-Core Version:    0.6.2
 */