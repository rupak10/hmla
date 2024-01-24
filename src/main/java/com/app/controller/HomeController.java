package com.app.controller;

import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	private final String HOME_PAGE = "home";
	
	private final String ACTIVE_MENU = "home";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadHomePage(Model model, HttpSession httpSession) {
		log.info("Entering loadHomePage() method");

		model.addAttribute("title", "Home");
		model.addAttribute("userFullName", CommonUtil.getUserFullName(httpSession));
		model.addAttribute("am", ACTIVE_MENU);
		log.info("Exiting loadHomePage() method");
		return HOME_PAGE;
    }


}
