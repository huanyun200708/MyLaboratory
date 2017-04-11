package cn.com.hq.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/forwardWebSocket")
public class ForwardWebSocket {


    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Map<String,Object> connections = new HashMap<String,Object>();

    private final String nickname;
    private Session session;

    public ForwardWebSocket() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }


    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.put(nickname, this); 
        String message = String.format("* %s %s", nickname, "has joined.");
        //broadcast(message);
    }


    @OnClose
    public void end() {
        connections.remove(this.nickname);
        String message = String.format("* %s %s",
                nickname, "has disconnected.");
        broadcast(message);
    }


    /**
     * 消息发送触发方法
     * @param message
     */
    @OnMessage
    public void incoming(String message) {
    	System.out.println(message.toString());
        broadcast(message.toString());
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        System.out.println("Chat Error: " + t.toString());
    }

    /**
     * 消息发送方法
     * @param msg
     */
    private static void broadcast(String msg) {
    	sendAll(msg);
    } 
    
    /**
     * 向所有用户发送
     * @param msg
     */
    public static void sendAll(String msg){
    	Iterator<String> iterator = connections.keySet().iterator();   
    	while (iterator.hasNext()) {  
    		    String key = (String) iterator.next();  
    		    ForwardWebSocket client = null ;
    	            try {
    	            	client = (ForwardWebSocket) connections.get(key);
    	                synchronized (client) {
    	                	if(client.session.isOpen()){
    	                		 client.session.getBasicRemote().sendText(msg);
    	                	}else{
    	                		iterator.remove();        
    	         		       connections.remove(key);  
    	                	}
    	                   
    	                }
    	            } catch (Exception e) { 
    	            		iterator.remove();        
	         		       connections.remove(key);  
    	                try {
    	                    client.session.close();
    	                } catch (IOException e1) {
    	                    // Ignore
    	                }
    	                String message = String.format("* %s %s",
    	                        client.nickname, "has been disconnected.");
    	                broadcast(message);
    	            }
    		 }  

    }
    
    /**
     * 向指定用户发送消息 
     * @param msg
     */
    public static void sendUser(String msg){
    	ForwardWebSocket c = (ForwardWebSocket)connections.get("Guest0");
		try {
			c.session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
            connections.remove(c);
            try {
                c.session.close();
            } catch (IOException e1) {
                // Ignore
            }
            String message = String.format("* %s %s",
                    c.nickname, "has been disconnected.");
            broadcast(message);  
		} 
    }
}