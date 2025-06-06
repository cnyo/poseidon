package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DbUser;
import com.nnk.springboot.services.PasswordService;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller for managing users.
 * Provides endpoints for listing, adding, updating, and deleting users.
 */
@Controller
public class UserController {
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    private final Logger log = LogManager.getLogger(UserController.class);

    String INVALID_PASSWORD_MESSAGE = "Password must be between 8 and 20 characters, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";

    /**
     * Displays the user list page with all users.
     *
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getAllUser());
        return "user/list";
    }

    /**
     * Displays the form for adding a new user.
     *
     * @param user the user object to initialize the form
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/user/add")
    public String addUser(DbUser user, Model model) {
        model.addAttribute("user", user);

        return "user/add";
    }

    /**
     * Validates and saves the new user.
     *
     * @param user the user object to validate and save
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") DbUser user, BindingResult result, Model model) {
        if (!passwordService.isValidPassword(user.getPassword())) {
            result.rejectValue("password", "error.user", INVALID_PASSWORD_MESSAGE);
        }

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            model.addAttribute("users", userService.getAllUser());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Displays the form for updating an existing user.
     *
     * @param id the ID of the user to update
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        DbUser user = userService.getUser(id);
        user.setPassword("");
        model.addAttribute("user", user);

        return "user/update";
    }

    /**
     * Validates and updates an existing user.
     *
     * @param id the ID of the user to update
     * @param user the user object to validate and update
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render or redirect if successful
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") DbUser user,
                             BindingResult result, Model model) {
        if (!passwordService.isValidPassword(user.getPassword())) {
            result.rejectValue("password", "error.user", INVALID_PASSWORD_MESSAGE);
        }

        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            user.setId(id);
            model.addAttribute("user", user);

            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUser());
        return "redirect:/user/list";
    }

    /**
     * Deletes a user by ID and redirects to the user list.
     *
     * @param id the ID of the user to delete
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userService.getAllUser());
        return "redirect:/user/list";
    }
}
