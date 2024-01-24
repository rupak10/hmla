package com.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		System.out.println("Inerceptor is calling");
        
        // Check if session exists and user is authenticated
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login"); // Redirect to login page if session is invalid
            return false;
        }
        
        return true; // Continue with the request handling
	}
}
