package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.TradeService;
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
 * Controller for managing trades.
 * Provides endpoints for listing, adding, updating, and deleting trades.
 */
@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(BidListController.class);

    /**
     * Displays the trade list page with all trades.
     *
     * @param model the model to add attributes for the view
     * @param authentication the authentication object to get user details
     * @return the name of the view to render
     */
    @RequestMapping("/trade/list")
    public String home(Model model, Authentication authentication)
    {
        List<Trade> trades= tradeService.getAllTrade();
        model.addAttribute("trades", trades);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "trade/list";
    }

    /**
     * Displays the form for adding a new trade.
     *
     * @param trade the trade object to initialize the form
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model)
    {
        model.addAttribute("trade", trade);

        return "trade/add";
    }

    /**
     * Validates and adds a new trade.
     *
     * @param trade the trade object to validate and add
     * @param result the binding result to hold validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render, or redirect if successful
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }

        try {
            tradeService.saveTrade(trade);
        } catch (Exception e) {
            result.rejectValue("tradeId", "error.tradeId", "Trade ID already exists");
            return "trade/add";
        }

        return "redirect:/trade/list";
    }

    /**
     * Displays the form for updating an existing trade.
     *
     * @param id the ID of the trade to update
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        model.addAttribute("trade", trade);

        return "trade/update";
    }

    /**
     * Validates and updates an existing trade.
     *
     * @param id the ID of the trade to update
     * @param trade the trade object with updated values
     * @param result the binding result to hold validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render, or redirect if successful
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }

        try {
            tradeService.updateTrade(id, trade);
        } catch (Exception e) {
            result.rejectValue("tradeId", "error.tradeId", "Trade ID already exists");
            return "trade/update";
        }

        return "redirect:/trade/list";
    }

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete
     * @param model the model to add attributes for the view
     * @return the name of the view to redirect to
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        if (trade != null) {
            tradeService.deleteTrade(trade.getId());
        }

        return "redirect:/trade/list";
    }
}
