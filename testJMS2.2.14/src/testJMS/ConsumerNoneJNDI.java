package testJMS;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.logging.Logger;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
public class ConsumerNoneJNDI {  
    private static Logger logger = Logger.getLogger(ConsumerNoneJNDI.class);  
  
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
    	// Step 1.
    	Queue queue = HornetQJMSClient.createQueue("ExpiryQueue");
    	// Step 2.
    	Map<String, Object> connectionParams = new HashMap<String, Object>();
        connectionParams.put(TransportConstants.PORT_PROP_NAME, 5445);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, "10.71.42.147");
        connectionParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, "true");
        connectionParams.put(TransportConstants.KEYSTORE_PATH_PROP_NAME,"EAMJMSKeystore.jks");
        connectionParams.put(TransportConstants.KEYSTORE_PASSWORD_PROP_NAME,"ACROSS_dpl_2013");
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(),
                connectionParams);
		// Step 3.
        ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        Connection connection = cf.createConnection();  
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
        MessageConsumer consumer = session.createConsumer(queue);  
        connection.start();  
        try {  
            while (true) {  
                ObjectMessage messageReceived = (ObjectMessage) consumer.receive();  
                Person one = (Person)messageReceived.getObject();  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
                String time = format.format(new Date()); 
                logger.info(time + " : " + ConsumerNoneJNDI.class.getName()  
                        + " start to receive message: " + one);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}  
