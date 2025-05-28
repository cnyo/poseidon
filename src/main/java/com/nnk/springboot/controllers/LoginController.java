package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserService userService;

    private final Logger log = LogManager.getLogger(LoginController.class);

    @GetMapping("login")
    public ModelAndView login() {
        log.info("Get login page");
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", new DbUser());
        mav.setViewName("login");
        log.info(mav.getViewName());

        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("Get all user articles");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getAllUser());
        mav.setViewName("user/list");
        log.info(mav.getViewName());

        return mav;
    }

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
