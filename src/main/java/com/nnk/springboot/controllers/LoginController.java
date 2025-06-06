package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Controller for handling login and user-related requests.
 * Provides endpoints for displaying the login page, user articles, and error pages.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(LoginController.class);

    /**
     * Displays the login page if the user is not authenticated.
     * If the user is already authenticated, redirects to the home page.
     *
     * @param authentication the authentication object to check user status
     * @return ModelAndView object containing the login page or redirect to home
     */
    @GetMapping("login")
    public ModelAndView login(Authentication authentication) {
        if (!loginService.isAnonymousAuthentication(authentication)) {
            log.info("User is already authenticated, redirecting to home page");
            return new ModelAndView("redirect:/");
        }

        log.info("Get login page");
        ModelAndView mav = new ModelAndView();
        Map<String, String> oauth2AuthenticationUrls = loginService.getOauth2AuthenticationUrls();
        mav.addObject("urls", oauth2AuthenticationUrls);
        mav.addObject("user", new DbUser());
        mav.setViewName("login");
        log.info(mav.getViewName());

        return mav;
    }

    /**
     * Displays the user articles page for authenticated users.
     * This endpoint is secured and requires authentication.
     *
     * @return ModelAndView object containing the user articles page
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("Get all user articles");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getAllUser());
        mav.setViewName("user/list");
        log.info(mav.getViewName());

        return mav;
    }

    /**
     * Displays the error page when the user is not authorized to access certain data.
     * This endpoint is secured and requires authentication.
     *
     * @return ModelAndView object containing the error page
     */
    @GetMapping("error")
    public ModelAndView error() {
        log.info("Get error page");
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        log.info(mav.getViewName());

        return mav;
    }
}
