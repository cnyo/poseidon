package com.nnk.springboot.controllers;

import com.nnk.springboot.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling home and admin home requests.
 * Provides endpoints for displaying the home page and redirecting to the admin home.
 */
@Controller
public class HomeController
{
	@Autowired
	private LoginService loginService;

	private final Logger log = LogManager.getLogger(HomeController.class);

	/**
	 * Displays the home page with user display name.
	 *
	 * @param model the model to add attributes for the view
	 * @param authentication the authentication object to get user details
	 * @return the name of the view to render
	 */
	@RequestMapping("/")
	public String home(Model model, Authentication authentication)
	{
		model.addAttribute("displayName", loginService.getDisplayName(authentication));

		return "home";
	}

	/**
	 * Redirects to the admin home page.
	 *
	 * @param model the model to add attributes for the view
	 * @return a redirect to the bid list page
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
}
