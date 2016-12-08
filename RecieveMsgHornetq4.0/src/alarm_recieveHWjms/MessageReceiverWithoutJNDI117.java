package alarm_recieveHWjms;

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
    	connectionParams.put(TransportConstants.PORT_PROP_NAME, 15216);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, "10.110.2.117");
        connectionParams.put(TransportConstants.SSL_ENABLED_PROP_NAME, "true");
        connectionParams.put(TransportConstants.KEYSTORE_PATH_PROP_NAME,"/opt/netwatcher/pm4h2/work/conf/security/jms/DSBJMSKeystore.jks");
        connectionParams.put(TransportConstants.KEYSTORE_PASSWORD_PROP_NAME,"ACROSS_dsbjms_cer_2016");
        connectionParams.put(TransportConstants.TRUSTSTORE_PATH_PROP_NAME,"/opt/netwatcher/pm4h2/work/conf/security/jms/DSBJMSKeystore.truststore");
        connectionParams.put(TransportConstants.TRUSTSTORE_PASSWORD_PROP_NAME,"ACROSS_dsbjms_cer_2016");
        connectionParams.put(TransportConstants.ENABLED_PROTOCOLS_PROP_NAME,"TLSv1.2");
        TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(),
                connectionParams);
        ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, transportConfiguration);
        connections = cf.createConnection("jmsdhb","ACROSS_dhb_2013");    
		connections.setExceptionListener(this);

		session = connections.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = null;
		 try {
	        	queue = HornetQJMSClient.createTopic("itf_queue");
	        	messageConsumer = session.createConsumer(queue); 
			} catch (Exception e) {
				queue = HornetQJMSClient.createQueue("itf_queue");
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
