package com.app.controller;

import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.dto.AppResponse;
import com.app.model.User;
import com.app.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
	private final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final String REDIRECT_LOGIN_PAGE = "redirect:/login";
	private final String LOGIN_PAGE = "login";
	private final String REGISTRATION_PAGE = "registration";
	private final String REDIRECT_HOME_PAGE = "redirect:/";
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String loadRegistrationPage(Model model) {
		log.info("Entering loadRegisterPage() method");

		model.addAttribute("signupRequest", new SignupRequest());
		log.info("Exiting loadRegisterPage() method");
		return REGISTRATION_PAGE;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegistration(Model model, @ModelAttribute SignupRequest signupRequest, final RedirectAttributes redirectAttributes) {
		log.info("Entering doRegistration() method");

		try {
			log.info("signupRequest:"+signupRequest);
			AppResponse appResponse = userService.doRegistration(signupRequest);
			if(appResponse.getStatus()) {
				redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
				log.info("Exiting doRegistration() method");
				return REDIRECT_LOGIN_PAGE;
			}
			else {
				model.addAttribute("msg", appResponse.getMessage());
				model.addAttribute("signupRequest", signupRequest);
				return REGISTRATION_PAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Registration Failed !");
			model.addAttribute("signupRequest", signupRequest);
			log.info("Exiting doRegistration() method");
			return REGISTRATION_PAGE;
		}
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loadLoginPage(Model model, HttpSession httpSession) {
		log.info("Entering loadLoginPage() method");
		
		User user = (User) httpSession.getAttribute("user");
		if(user != null) {
			log.info("Exiting loadHomeOrLoginPage() method");
			return REDIRECT_HOME_PAGE;
		}
		
		model.addAttribute("loginRequest", new LoginRequest());
		log.info("Exiting loadLoginPage() method");
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLogin(Model model, HttpSession httpSession, @ModelAttribute LoginRequest loginRequest) {
		log.info("Entering processLogin() method");
		
		try {
			log.info("session id:"+httpSession.getId());
			log.info("loginRequest:"+loginRequest);
			AppResponse appResponse = userService.authenticateUser(loginRequest);
			if(appResponse.getStatus()) {
				User user = userService.getUserByEmail(loginRequest.getEmail());
				if(user == null) {
					model.addAttribute("msg", "Wrong Credentials !");
					return LOGIN_PAGE;
				}

				httpSession.setAttribute("user", user);
				httpSession.setMaxInactiveInterval(7200); //2 hours

				log.info("Exiting processLogin() method");
				return REDIRECT_HOME_PAGE;
			}
			else {
				model.addAttribute("msg", appResponse.getMessage());
				log.info("Exiting processLogin() method");
				return LOGIN_PAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Login Failed !");
			log.info("Exiting processLogin() method");
			return LOGIN_PAGE;
		}
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		log.info("Entering logout() method");
		
		httpSession.invalidate();
		
		log.info("Exiting logout() method");
		return REDIRECT_LOGIN_PAGE;
	}
	

}
