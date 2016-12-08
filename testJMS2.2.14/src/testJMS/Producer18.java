package testJMS;
import java.io.File;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hornetq.core.logging.Logger;


public class Producer18 {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            runExample();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    private static void runExample() throws NamingException, JMSException { 
    	Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "jnp://10.110.2.18:15351");
        InitialContext ic = new InitialContext(properties);
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");  
        Queue orderQueue = (Queue) ic.lookup("/queue/ExpiryQueue");  
        //Connection connection = cf.createConnection("jmsdhb","ACROSS_dhb_2013");
        Connection connection = cf.createConnection("jmsdpl","ACROSS_dpl_2013");  
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
        MessageProducer producer = session.createProducer(orderQueue);  
        connection.start();  
        try {  
            for (int i =0;i<1;i++) {  
                Person one = new Person(i, "xuepeng_" + i);  
                ObjectMessage msg = session.createObjectMessage(one);  
                producer.send(msg);  
                System.out.println(ProducerNoneJNDI_18.class.getName()  
                        + " start to sent message: " + one);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}  