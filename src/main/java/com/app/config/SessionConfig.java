package com.app.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {
	
	@Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        ServletListenerRegistrationBean<SessionListener> listenerBean =
                new ServletListenerRegistrationBean<>();

        listenerBean.setListener(new SessionListener());
        return listenerBean;
    }




}
