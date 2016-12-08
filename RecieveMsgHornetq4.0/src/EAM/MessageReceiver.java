package EAM;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class MessageReceiver  implements ExceptionListener, Runnable{

	protected ConnectionFactory queueFactory;
	protected Connection connections;
	protected Session session;
	protected MessageConsumer messageConsumer;
	
	public static void main(String[] args) {  
		new	MessageReceiver().startReceiver();
    }

	private  void init() throws JMSException, NamingException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		System.out.println("-------------ININT START-------------");
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		props.put(Context.PROVIDER_URL, "jnp://10.110.2.117:15351");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		Context context = new InitialContext(props);
		
		ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connections = cf.createConnection("jmsdpl", "ACROSS_dpl_2013");
		connections.setExceptionListener(this);
		
		session = connections.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup("topic_eam");//mosum
		messageConsumer = session.createConsumer(destination,"");
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
					e.printStackTrace();
					System.out.println(e.getMessage());
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
				 System.out.println("[AcrossPM- EAM-FRAMEWORK-MessageReceiver]Catch JMSException when receive message.");
			}
		}
		try {
			this.close();
		} catch (JMSException e) {
			 System.out.println("[AcrossPM- EAM-FRAMEWORK-MessageReceiver]Catch JMSException when close jms.");

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
