package com.app.config;

import com.app.model.User;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
	
	private final Logger log = LoggerFactory.getLogger(HttpSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.info("inside sessionCreated");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		User user = (User) se.getSession().getAttribute("user");
		log.info("inside sessionDestroyed. user : "+user);
	}

}
