package testJMS;

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

public class ProducerNoneJNDI_18 {  
	private static Logger logger = Logger.getLogger(ProducerNoneJNDI_18.class);  
  
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
    	connectionParams.put(TransportConstants.PORT_PROP_NAME, 15216);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, "10.110.2.18");
        connectionParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, "true");
        connectionParams.put(TransportConstants.KEYSTORE_PATH_PROP_NAME,"EAMJMSKeystore.jks");
        connectionParams.put(TransportConstants.KEYSTORE_PASSWORD_PROP_NAME,"ACROSS_dpl_2013");
        
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(),
                connectionParams);
		// Step 3.
        ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);

        Connection connection = cf.createConnection("jmsdhb","ACROSS_dhb_2013");  
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);  
        MessageProducer producer = session.createProducer(queue);  
        connection.start();  
        try {  
            for (int i =0;i<10;i++) {  
                Person one = new Person(i, "xuepeng_" + i);  
                ObjectMessage msg = session.createObjectMessage(one);  
                producer.send(msg);  
                logger.info(ProducerNoneJNDI_18.class.getName()  
                        + " start to sent message: " + one);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}
