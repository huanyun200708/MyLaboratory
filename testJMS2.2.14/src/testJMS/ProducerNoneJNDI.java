package testJMS;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.NamingException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.logging.Logger;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.spi.core.protocol.ProtocolType;

public class ProducerNoneJNDI {  
	private static Logger logger = Logger.getLogger(ProducerNoneJNDI.class);  
  
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
    	/*Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
        InitialContext ic = new InitialContext(properties);
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");  
        Queue orderQueue = (Queue) ic.lookup("/queue/ExpiryQueue");*/  
    	// Step 1.
    	Queue queue = HornetQJMSClient.createQueue("ExpiryQueue");
    	// Step 2.
    	Map<String, Object> connectionParams = new HashMap<String, Object>();
        connectionParams.put(TransportConstants.PORT_PROP_NAME, 5445);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, "10.71.42.147");
        connectionParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, "true");
        connectionParams.put(TransportConstants.KEYSTORE_PATH_PROP_NAME,"EAMJMSKeystore.jks");
        connectionParams.put(TransportConstants.KEYSTORE_PASSWORD_PROP_NAME,"ACROSS_dpl_2013");
        //connectionParams.put("enabled-cipher-suites","TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256");
        
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(),
                connectionParams);
		// Step 3.
        ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);

        Connection connection = cf.createConnection();  
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
        MessageProducer producer = session.createProducer(queue);  
        connection.start();  
        try {  
            for (int i =0;i<10;i++) {  
                Person one = new Person(i, "xuepeng_" + i);  
                ObjectMessage msg = session.createObjectMessage(one);  
                producer.send(msg);  
                logger.info(ProducerNoneJNDI.class.getName()  
                        + " start to sent message: " + one);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}
