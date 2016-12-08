package testJMS;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.hornetq.core.logging.Logger;

public class TestMessageListener implements MessageListener{
	private static Logger logger = Logger.getLogger(TestMessageListener.class); 
	@Override
	public void onMessage(Message msg) {
		Person one = (Person)msg;  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
        String time = format.format(new Date()); 
        logger.info(time + " : " + ConsumerNoneJNDI_18.class.getName()  
                + " start to receive message: " + one);  
		
	}

}
