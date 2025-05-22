package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BidListController {
    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        List<BidList> bidLists = bidListService.getAllBidList();
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        BidListForm bidForm = bidListService.initBidFormFromBid(bid);
        model.addAttribute("bid", bidForm);

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListForm bidForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        try {
            BidList bid = bidListService.bidFormToBid(bidForm);
            bidListService.saveBid(bid);
        } catch (Exception e) {
            result.rejectValue("bidListId", "error.bidListId", "Bid ID already exists");
            return "bidList/add";
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListForm bidForm,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("bid", bidForm);
            return "bidList/update";
        }

        try {
            bidListService.updateBid(id, bidForm);
        } catch (Exception e) {
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "curvePoint/update";
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        if (bidList != null) {
            bidListService.deleteBid(bidList.getId());
        }

        return "redirect:/bidList/list";
    }
}
