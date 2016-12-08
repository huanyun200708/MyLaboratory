package alarm_recieveLocalJms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.NamingException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

public class MessageReceiverWithoutJNDI117 implements ExceptionListener,
		Runnable {

	protected MessageConsumer messageConsumer;
	protected ConnectionFactory queueFactory;
	protected Connection connections;
	protected Session session;

	public static void main(String[] args) {
		new MessageReceiverWithoutJNDI117().startReceiver();

	}

	private void init() throws JMSException, NamingException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
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
		connections.setExceptionListener(this);

		session = connections.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = null;
		 try {
	        	queue = HornetQJMSClient.createTopic("testTopic");
	        	messageConsumer = session.createConsumer(queue); 
			} catch (Exception e) {
				queue = HornetQJMSClient.createQueue("testTopic");
				messageConsumer = session.createConsumer(queue); 
			}
		messageConsumer = session.createConsumer(queue, "");
		messageConsumer.setMessageListener(new MessageRecieveListener());

		connections.start();
		System.out.println("-------------ININT END-------------");

	}

	@Override
	public void run() {
		System.out.println("-------------RECEVICE RUN-------------");
		while (true) {
			if (connections == null) {
				try {
					this.init();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | JMSException
						| NamingException e) {
					System.out.println("-------------ININT ERROR-------------");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("-------------sleep zzzz-------------");
			}
		}
	}

	// 启动监听
	public void startReceiver() {
		new Thread(this).start();
	}

	@Override
	public void onException(JMSException jmsexception) {
		if (connections != null) {
			try {
				connections.setExceptionListener(null);
			} catch (JMSException e) {
				System.out
						.println("[AcrossPM- EAM-FRAMEWORK-MessageReceiver]Catch JMSException when receive message.");
			}
		}
		try {
			this.close();
		} catch (JMSException e) {
			System.out
					.println("[AcrossPM- EAM-FRAMEWORK-MessageReceiver]Catch JMSException when close jms.");

		} finally {
			clear();
		}
	}

	public void close() throws JMSException {
		if (messageConsumer != null) {
			messageConsumer.close();
		}
		if (session != null) {
			session.close();
		}
		if (connections != null) {
			connections.close();
		}
	}

	/**
	 * 清空jms
	 */
	public void clear() {
		messageConsumer = null;
		session = null;
		connections = null;
	}

}
