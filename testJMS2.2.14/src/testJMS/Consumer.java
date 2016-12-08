package testJMS;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hornetq.core.logging.Logger;
public class Consumer {  
    private static Logger logger = Logger.getLogger(Consumer.class);  
  
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
        properties.setProperty(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
        InitialContext ic = new InitialContext(properties);
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");  
        Queue orderQueue = (Queue) ic.lookup("/queue/ExpiryQueue");  
        Connection connection = cf.createConnection();  
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
        MessageConsumer consumer = session.createConsumer(orderQueue);  
        connection.start();  
        try {  
            while (true) {  
                ObjectMessage messageReceived = (ObjectMessage) consumer.receive();  
                Person one = (Person)messageReceived.getObject();  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
                String time = format.format(new Date()); 
                logger.info(time + " : " + Consumer.class.getName()  
                        + " start to receive message: " + one);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}  
