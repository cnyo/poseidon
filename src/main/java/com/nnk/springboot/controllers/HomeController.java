package com.nnk.springboot.controllers;

import com.nnk.springboot.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@Autowired
	private LoginService loginService;

	private final Logger log = LogManager.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model, Authentication authentication)
	{
		model.addAttribute("displayName", loginService.getDisplayName(authentication));

		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
