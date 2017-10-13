 package com.huawei.inoc.most.cbb.encrypt;
 
 public final class PasswordTool
 {
   public static boolean notContainUserNameOrReverse(char[] wsPassword, String wsUserName)
   {
     String reverseLowcaseUsername = "";
     int len = wsUserName.length();
 
     for (int i = len - 1; i >= 0; i--)
     {
       reverseLowcaseUsername = reverseLowcaseUsername + wsUserName.charAt(i);
     }
 
     if ((passwordVar(wsPassword, wsUserName)) && (passwordVar(wsPassword, reverseLowcaseUsername)))
     {
       return true;
     }
 
     return false;
   }
 
   private static boolean passwordVar(char[] wsPassword, String wsUserName)
   {
     if (wsPassword.length < wsUserName.length())
     {
       return true;
     }
 
     int m = wsPassword.length - wsUserName.length();
     String str = "";
     for (int i = 0; i < m + 1; i++)
     {
       for (int j = i; j < wsUserName.length() + i; j++)
       {
         str = str + wsPassword[j];
       }
       if (str.equals(wsUserName))
       {
         return false;
       }
 
       str = "";
     }
 
     return true;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.cbb.encrypt.PasswordTool
 * JD-Core Version:    0.6.2
 */