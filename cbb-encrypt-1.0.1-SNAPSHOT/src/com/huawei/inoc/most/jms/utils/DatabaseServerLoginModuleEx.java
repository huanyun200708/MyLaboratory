package com.huawei.inoc.most.jms.utils;
 
 import com.huawei.inoc.most.cbb.encrypt.PasswordTool;
 import com.huawei.inoc.most.cbb.encrypt.ShaEncrypt;
 import com.huawei.inoc.most.platform.secure.facade.SecureFacade;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.naming.InitialContext;
 import javax.naming.NamingException;
 import javax.sql.DataSource;
 import org.jboss.logging.Logger;
 import org.jboss.security.auth.spi.DatabaseServerLoginModule;
 
 public class DatabaseServerLoginModuleEx extends DatabaseServerLoginModule
 {
   private static final Logger LOG = Logger.getLogger(DatabaseServerLoginModuleEx.class);
   private static final int MAX_TRY_TIMES = 5;
   private static final int LOCK_TIMEOUT = 300000;
   private static Map<String, String> userAuthMap = new HashMap(10);
 
   protected boolean validatePassword(String inputPassword, String expectedPassword)
   {
     String salt = getUsersSalt();
 
     boolean isValidate = false;
     String username = super.getUsername();
     if (null == username)
     {
       return false;
     }
 
     if (userAuthMap.containsKey(username))
     {
       String[] authInfo = ((String)userAuthMap.get(username)).split(";");
       long lastFailedTime = Long.valueOf(authInfo[1]).longValue();
       long failedTimes = Long.valueOf(authInfo[0]).longValue();
 
       if (failedTimes >= 5L)
       {
         if (System.currentTimeMillis() - lastFailedTime <= 300000L)
         {
           String errorMsg = "Try too many times, username " + username + " is locked.";
           LOG.error(errorMsg);
           return false;
         }
 
         userAuthMap.remove(username);
       }
 
     }
 
     boolean notContainUsername = PasswordTool.notContainUserNameOrReverse(inputPassword.toCharArray(), super.getUsername());
     if (!notContainUsername)
     {
       LOG.error("Don't meet the password policy requirement.");
       isValidate = false;
     }
     else
     {
       String shaPwd = "";
       if (null != salt)
       {
         shaPwd = String.valueOf(SecureFacade.encodeHex(SecureFacade.derivedKey(inputPassword.getBytes(), salt.getBytes())));
         isValidate = super.validatePassword(shaPwd, expectedPassword);
       }
       if (!isValidate)
       {
         shaPwd = ShaEncrypt.encrypt(inputPassword, salt);
         isValidate = super.validatePassword(shaPwd, expectedPassword);
       }
     }
 
     if (!isValidate)
     {
       long failedTimes = 0L;
       long lastFailedTime = 0L;
       if (userAuthMap.containsKey(username))
       {
         String[] authInfo = ((String)userAuthMap.get(username)).split(";");
         failedTimes = Long.valueOf(authInfo[0]).longValue();
       }
       failedTimes += 1L;
       lastFailedTime = System.currentTimeMillis();
       userAuthMap.put(username, failedTimes + ";" + lastFailedTime);
     }
 
     return isValidate;
   }
 
   protected String getUsersSalt()
   {
     String username = getUsername();
     String password = null;
     Connection conn = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     try
     {
       InitialContext ctx = new InitialContext();
       DataSource ds = (DataSource)ctx.lookup(this.dsJndiName);
       conn = ds.getConnection();
 
       ps = conn.prepareStatement(this.principalsQuery);
       ps.setString(1, username);
       rs = ps.executeQuery();
       if (!rs.next())
       {
         return null;
       }
 
       password = rs.getString(2);
     }
     catch (NamingException ex)
     {
       return null;
     }
     catch (SQLException ex)
     {
       DataSource ds;
       return null;
     }
     finally
     {
       if (rs != null)
       {
         try
         {
           rs.close();
         }
         catch (SQLException e)
         {
           LOG.error("Get SALT ERROR");
         }
       }
       if (ps != null)
       {
         try
         {
           ps.close();
         }
         catch (SQLException e)
         {
           LOG.error("Get SALT ERROR");
         }
       }
       if (conn != null)
       {
         try
         {
           conn.close();
         }
         catch (SQLException ex)
         {
           LOG.error("Get SALT ERROR");
         }
       }
     }
     return password;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     com.huawei.inoc.most.jms.utils.DatabaseServerLoginModuleEx
 * JD-Core Version:    0.6.2
 */