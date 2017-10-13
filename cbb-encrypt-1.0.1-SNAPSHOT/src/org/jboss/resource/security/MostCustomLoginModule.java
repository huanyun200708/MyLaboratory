 package org.jboss.resource.security;
 
 import com.huawei.inoc.most.platform.secure.facade.SecureFacade;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;

import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jboss.security.SimplePrincipal;
 
 public class MostCustomLoginModule extends AbstractPasswordCredentialLoginModule
 {
   private static final Logger LOG = Logger.getLogger(MostCustomLoginModule.class);
   private String username;
   private String passwordCipher;
   private String principalName;
 
   public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map options)
   {
     super.initialize(subject, this.callbackHandler, sharedState, options);
 
     this.principalName = ((String)options.get("principal"));
     if (this.principalName == null)
     {
       throw new IllegalArgumentException("Must supply a principal name!");
     }
     this.username = ((String)options.get("username"));
     if (this.username == null)
     {
       throw new IllegalArgumentException("The user name is a required option");
     }
     this.passwordCipher = ((String)options.get("password"));
     if (this.passwordCipher == null)
     {
       throw new IllegalArgumentException("The password is a required option");
     }
   }
 
   public boolean login()
     throws LoginException
   {
     LOG.trace("login called");
     if (super.login())
     {
       return true;
     }
 
     Principal principal = new SimplePrincipal(this.principalName);
     SubjectActions.addPrincipals(this.subject, principal);
 
     this.sharedState.put("javax.security.auth.login.name", this.principalName);
 
     String pwd = SecureFacade.decrypt(this.passwordCipher);
 
     if (null == pwd)
     {
       LOG.info("Can not decrypt data source password! Check file:login-config !");
       return false;
     }
     char[] decodedPassword = pwd.toCharArray();
     PasswordCredential cred = new PasswordCredential(this.username, decodedPassword);
     cred.setManagedConnectionFactory(getMcf());
     SubjectActions.addCredentials(this.subject, cred);
 
     this.loginOk = true;
     return true;
   }
 
   public boolean abort()
   {
     this.username = null;
     this.passwordCipher = null;
     return true;
   }
 
   protected Principal getIdentity()
   {
     Principal simple = new SimplePrincipal(this.principalName);
     return simple;
   }
 
   protected Group[] getRoleSets()
   {
     Group[] empty = new Group[0];
     return empty;
   }
 }

/* Location:           C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar
 * Qualified Name:     org.jboss.resource.security.MostCustomLoginModule
 * JD-Core Version:    0.6.2
 */