package EAM;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class MessageRecieveListener implements MessageListener{

	@Override
	public void onMessage(Message m) {
		//String url = "jnp://10.110.2.18:15351";
        //String topic = "topic_eam";
        System.out.println("-------------ON MESSAGE-------------");
        System.out.println("Message:" + m);
        if(m instanceof TextMessage){
            try {
                TextMessage tm =(TextMessage) m;
                String jmsType = tm.getJMSType();
                String jmsContent = tm.getText();
                System.out.println("jmsType: "+jmsType+" jmsContent: "+jmsContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(m instanceof ObjectMessage){
            try {
            	ObjectMessage o =(ObjectMessage) m;
                System.out.println("ObjectMessage: " + o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

}
