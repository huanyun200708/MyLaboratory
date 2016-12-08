package sendLocalJms;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import modle.Person;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQTopic;


public class LoclaSender {  
	
	protected static MessageProducer messageProducer;
	protected static ConnectionFactory queueFactory;
	protected static Connection connections;
	protected static Session session;
  
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
    	System.out.println("-------------ININT START-------------");
		Map<String, Object> connectionParams = new HashMap<String, Object>();
    	connectionParams.put(TransportConstants.PORT_PROP_NAME, 5445);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, "127.0.0.1");
        connectionParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, "true");
        connectionParams.put(TransportConstants.KEYSTORE_PATH_PROP_NAME,"/opt/netwatcher/pm4h2/work/conf/security/jms/DSBJMSKeystore.jks");
        connectionParams.put(TransportConstants.KEYSTORE_PASSWORD_PROP_NAME,"ACROSS_dsbjms_cer_2016");
        connectionParams.put(TransportConstants.TRUSTSTORE_PATH_PROP_NAME,"/opt/netwatcher/pm4h2/work/conf/security/jms/DSBJMSKeystore.truststore");
        connectionParams.put(TransportConstants.TRUSTSTORE_PASSWORD_PROP_NAME,"ACROSS_dsbjms_cer_2016");
        connectionParams.put(TransportConstants.ENABLED_PROTOCOLS_PROP_NAME,"TLSv1.2");
        connectionParams.put(TransportConstants.ENABLED_CIPHER_SUITES_PROP_NAME,"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,TLS_RSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256,TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256,TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,TLS_DHE_DSS_WITH_AES_128_CBC_SHA256,TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,TLS_RSA_WITH_AES_128_CBC_SHA,TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA,TLS_ECDH_RSA_WITH_AES_128_CBC_SHA,TLS_DHE_RSA_WITH_AES_128_CBC_SHA,TLS_DHE_DSS_WITH_AES_128_CBC_SHA,TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256,TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,TLS_DHE_DSS_WITH_AES_128_GCM_SHA256,TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA,TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA,SSL_RSA_WITH_3DES_EDE_CBC_SHA,TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA,TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA,SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA,TLS_EMPTY_RENEGOTIATION_INFO_SCSV,TLS_DH_anon_WITH_AES_128_GCM_SHA256,TLS_DH_anon_WITH_AES_128_CBC_SHA256,TLS_ECDH_anon_WITH_AES_128_CBC_SHA,TLS_DH_anon_WITH_AES_128_CBC_SHA,TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA,SSL_DH_anon_WITH_3DES_EDE_CBC_SHA,TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,TLS_ECDHE_RSA_WITH_RC4_128_SHA,SSL_RSA_WITH_RC4_128_SHA,TLS_ECDH_ECDSA_WITH_RC4_128_SHA,TLS_ECDH_RSA_WITH_RC4_128_SHA,SSL_RSA_WITH_RC4_128_MD5,TLS_ECDH_anon_WITH_RC4_128_SHA,SSL_DH_anon_WITH_RC4_128_MD5,SSL_RSA_WITH_DES_CBC_SHA,SSL_DHE_RSA_WITH_DES_CBC_SHA,SSL_DHE_DSS_WITH_DES_CBC_SHA,SSL_DH_anon_WITH_DES_CBC_SHA,SSL_RSA_EXPORT_WITH_DES40_CBC_SHA,SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA,SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA,SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA,SSL_RSA_EXPORT_WITH_RC4_40_MD5,SSL_DH_anon_EXPORT_WITH_RC4_40_MD5,TLS_RSA_WITH_NULL_SHA256,TLS_ECDHE_ECDSA_WITH_NULL_SHA,TLS_ECDHE_RSA_WITH_NULL_SHA,SSL_RSA_WITH_NULL_SHA,TLS_ECDH_ECDSA_WITH_NULL_SHA,TLS_ECDH_RSA_WITH_NULL_SHA,TLS_ECDH_anon_WITH_NULL_SHA,SSL_RSA_WITH_NULL_MD5,TLS_KRB5_WITH_3DES_EDE_CBC_SHA,TLS_KRB5_WITH_3DES_EDE_CBC_MD5,TLS_KRB5_WITH_RC4_128_SHA,TLS_KRB5_WITH_RC4_128_MD5,TLS_KRB5_WITH_DES_CBC_SHA,TLS_KRB5_WITH_DES_CBC_MD5,TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA,TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5,TLS_KRB5_EXPORT_WITH_RC4_40_SHA,TLS_KRB5_EXPORT_WITH_RC4_40_MD5");
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(),
                connectionParams);
        ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        connections = cf.createConnection("jmsdpl","jmsdpl");    

		session = connections.createSession(false, Session.AUTO_ACKNOWLEDGE);
         
		Destination queue = null;
		 try {
	        	queue = HornetQJMSClient.createTopic("testTopic");
	        	messageProducer = session.createProducer(queue); 
			} catch (Exception e) {
				queue = HornetQJMSClient.createQueue("testTopic");
				messageProducer = session.createProducer(queue); 
			}
		 connections.start();  
        try {  
            for (int i =0;i<1;i++) {  
                Person one = new Person(i, "HQ_" + i);  
                String message = ""+Math.random();
                ObjectMessage msg = session.createObjectMessage(message);  
                messageProducer.send(msg);  
                System.out.println(LoclaSender.class.getName()  
                        + " start to sent message: " + message);  
            }  
        } finally {  
            session.close();  
        }  
    }  
  
}  