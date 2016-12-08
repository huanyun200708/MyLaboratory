package testJMS4;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hornetq.jms.client.HornetQTopic;


public class SendMessage {  
  
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
    	Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.PROVIDER_URL, "jnp://127.0.0.1:5445");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		Context context = new InitialContext(props);
		ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = cf.createConnection("jmsdpl", "ACROSS_dpl_2013");
         
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
        HornetQTopic topic = (HornetQTopic)context.lookup("topic_eam"); 
        MessageProducer producer = session.createProducer(topic);  
        connection.start();  
        try {  
            for (int i =0;i<1;i++) {  
                TextMessage msg = session.createTextMessage();
                msg.setJMSType("Person");
                msg.setText("Person" + i);
                producer.send(msg);  
                System.out.println(SendMessage.class.getName()  
                        + " start to sent message: " + "Person" + i);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}  