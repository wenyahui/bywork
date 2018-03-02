package com.fixture.websocket;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginWebsocketInterceptor implements HandshakeInterceptor{

    
    private Logger logger = LoggerFactory.getLogger(LoginWebsocketInterceptor.class);  
    
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
	    if(request instanceof ServerHttpRequest){
	        logger.info("LoginWebsocketInterceptor websocket握手开始------start------");
	        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//	        HttpSession session = servletRequest.getServletRequest().getSession();
	        //sysLogin = WebUtil.getCurrentUser();
	        //根据当前登录用户获取到学院id
	        /*if(sysLogin!=null){
	        }*/
            //区分socket连接以定向发送消息
//	        XSchoolLogin loginUser = WebUtils.getCurrentUser();
	        ObjectMapper objectMapper = new ObjectMapper();
            String jsonMapper = objectMapper.writeValueAsString("s");
            logger.info("sysLogin 温亚辉温亚辉温亚辉温亚辉:{}",jsonMapper);
            //区分socket连接以定向发送消息
            attributes.put("wyh-websocket-user", "s");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            attributes.put("uuid", uuid);
	        logger.info(" LoginWebsocketInterceptorwebsocket握手结束------end------");
	     }
	     return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
