package com.fixture.websocket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fixture.utils.DateUtils;

  

@Service
public class IndexWebSocketHandler extends MyWebSocketHandler implements ApplicationContextAware{  
       
	
    private Logger log = LoggerFactory.getLogger(IndexWebSocketHandler.class);  
     
    //在线用户列表
    private static final Map<String,Map<String, WebSocketSession>> users;
    
    
    private Timer timer;

    static {
        users = new HashMap<>();
    }
   
    protected ApplicationContext applicationContext;
    

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	} 
	
	
    @Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("websocket连接开始------------start--------");
        String uuid = session.getAttributes().get("uuid").toString();
    	log.debug("uuid:{}",uuid);
//    	XSchoolLogin loginUser = (XSchoolLogin)session.getAttributes().get("wyh-websocket-user");
    	//根据loginuser去查询学院信息
    	String collegeId = "";
    	Map<String, WebSocketSession> m = new HashMap<String, WebSocketSession>();
    	m.put(collegeId, session);
		users.put(uuid, m);
        //每次链接成功 就开启定时器  没一分钟检测一次客户端是否在线
    	timer = new Timer(true);
        long delay = 0;
        MyTimeTask orderTimeTask = new MyTimeTask(1,collegeId,uuid,session);
        timer.schedule(orderTimeTask,delay, 300000);// 设定指定的时间time,此处为1分钟刷新一次60000*120
        log.debug("websocket连接结束------------end--------");
          
    }  
   
    @Override  
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {  
        try{
            log.debug("websocket接收消息:" + message); 
        }
        catch(Exception e)
        {
            log.debug("错误信息：" + e.getMessage());  
        }
    }  
   
    @Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception { 
    	 if(session.isOpen()){  
    	     session.close();
    	     delSessionList(session);
         }    
        log.debug("websocket信息发送失败,异常信息:" + exception.getMessage());  
    }  
   
    @Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
        delSessionList(session);
    	log.debug("连接后关闭" + closeStatus.getReason());  
    }  
   
    @Override  
    public boolean supportsPartialMessages() {  
        return false;  
    }  
   
    
    
    
    /**
     * 
     * Description: 移除当前用户
     *
     * @param session 
     * @see
     */
    public void delSessionList(WebSocketSession session)
    {
    	String uuid = "";
    	//链接断开的时候 根据考试状态 去决定是否改为异常
    	tohere:
        for (Entry<String, Map<String, WebSocketSession>> entry : users.entrySet()){
        	Map<String, WebSocketSession> m = entry.getValue();
        	for(Entry<String, WebSocketSession> ent :m.entrySet()){
        		WebSocketSession webSess = ent.getValue();
        		if(webSess == session)
                {
                	log.info("uuid为:"+entry.getKey()+"的session关闭了");
                    uuid = entry.getKey();
                    break tohere;
                }
        	}
        }
    	if(StringUtils.isNotBlank(uuid)){
    		users.remove(uuid);
    	}
    }

    class MyTimeTask extends TimerTask{
    	private Integer loginId;
    	private String collegeId;
    	private String uuid;
        private WebSocketSession session;

        public MyTimeTask(Integer loginId, String collegeId, String uuid, WebSocketSession session){
        	this.loginId = loginId;
        	this.collegeId = collegeId;
        	this.uuid = uuid;
            this.session = session;
        }

        @Override
        public void run() {
            try {
            	if(session.isOpen()){
            		System.out.println(new Date());
            		System.out.println("发送了wyhwyhwyh   "+uuid);
            		String time = DateUtils.format(new Date(), "yyyy-MM-dd");
            		Map<String, Object> param = new HashMap<String, Object>();
            		param.put("time", time);
            		param.put("collegeId", this.collegeId);
            		param.put("loginId", this.loginId);
            		Map<String, Object> result = new HashMap<String, Object>();
            		ObjectMapper objectMapper = new ObjectMapper();
     	            String jsonMapper = objectMapper.writeValueAsString(result);
            		TextMessage textMessage = new TextMessage(jsonMapper);
            		session.sendMessage(textMessage);
            	}
            } catch (Exception e){
                log.info("websocket信息发送异常,异常信息:"+e.getMessage()); 
            }

        }
    }
}  