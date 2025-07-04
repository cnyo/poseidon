package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.RuleNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

/**
 * Controller for managing rule names.
 * Provides endpoints for listing, adding, updating, and deleting rule names.
 */
@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(RuleNameController.class);

    /**
     * Displays the rule name list page with all rule names.
     *
     * @param model the model to add attributes for the view
     * @param authentication the authentication object to get user details
     * @return the name of the view to render
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication authentication) {
        List<RuleName> ruleNames = ruleNameService.getAllRuleName();
        model.addAttribute("ruleNames", ruleNames);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "ruleName/list";
    }

    /**
     * Displays the form for adding a new rule name.
     *
     * @param ruleName the rule name object to initialize the form
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        model.addAttribute("ruleName", new RuleName());

        return "ruleName/add";
    }

    /**
     * Validates and adds a new rule name.
     *
     * @param ruleName the rule name object to validate and add
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render or redirect to the list page
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }

        try {
            ruleNameService.addRuleName(ruleName);

        } catch (Exception e) {
            result.rejectValue("ruleNameId", "error.ruleName", "RuleName ID already exists");
            return "ruleName/add";
        }

        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form for updating an existing rule name.
     *
     * @param id the ID of the rule name to update
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }

    /**
     * Validates and updates an existing rule name.
     *
     * @param id the ID of the rule name to update
     * @param ruleName the updated rule name object
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render or redirect to the list page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }

        try {
            ruleNameService.updateRuleName(id, ruleName);
        } catch (Exception e) {
            result.rejectValue("ruleNameId", "error.ruleNameId", "RuleName ID already exists");
            return "ruleName/update";
        }

        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a rule name by its ID.
     *
     * @param id the ID of the rule name to delete
     * @param model the model to add attributes for the view
     * @return the name of the view to redirect to
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleName(id);
        if (ruleName != null) {
            ruleNameService.deleteRuleName(ruleName.getId());
        }

        return "redirect:/ruleName/list";
    }
}
