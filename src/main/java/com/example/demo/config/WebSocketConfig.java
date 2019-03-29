package com.example.demo.config;

import com.example.demo.controller.ChatController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket//启动webSocket长连接
public class WebSocketConfig implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new ChatController(),"/chat")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                                   ServerHttpResponse serverHttpResponse,
                                                   WebSocketHandler webSocketHandler,
                                                   Map<String, Object> map) throws Exception {
                        ServletServerHttpRequest request= (ServletServerHttpRequest) serverHttpRequest;
                        String userName=request.getServletRequest().getParameter("userName");
//                        User user= (User) request.getServletRequest().getSession().getAttribute("user");
                        map.put("userName",userName);
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, @Nullable Exception e) {

                    }
                });
    }
}
