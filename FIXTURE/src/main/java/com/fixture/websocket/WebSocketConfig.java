package com.fixture.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration  
@EnableWebMvc  
@EnableWebSocket  
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{  
  

	@Autowired
    private IndexWebSocketHandler indexWebSocketHandler;
    
    
    @Override  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {  
    	System.out.println("examWebSocketHandler shi null =");
    	System.out.println(indexWebSocketHandler==null);
        registry.addHandler(indexWebSocketHandler,"/websocket/indexWebSocketServer").addInterceptors(new LoginWebsocketInterceptor());
        registry.addHandler(indexWebSocketHandler,"/websocket/indexWebSocketServer/sockjs").addInterceptors(new LoginWebsocketInterceptor()).withSockJS();
    }  
}
