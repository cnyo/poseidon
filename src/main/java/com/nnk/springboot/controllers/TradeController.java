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

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(BidListController.class);

    @RequestMapping("/trade/list")
    public String home(Model model, Authentication authentication)
    {
        List<Trade> trades= tradeService.getAllTrade();
        model.addAttribute("trades", trades);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model)
    {
        model.addAttribute("trade", trade);

        return "trade/add";
    }

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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        model.addAttribute("trade", trade);

        return "trade/update";
    }

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

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getTrade(id);
        if (trade != null) {
            tradeService.deleteTrade(trade.getId());
        }

        return "redirect:/trade/list";
    }
}
