package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.LoginService;
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
 * Controller for managing bid lists.
 * Provides endpoints for listing, adding, updating, and deleting bids.
 */
@Controller
public class BidListController {
    @Autowired
    private BidListService bidListService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(BidListController.class);

    /**
     * Displays the bid list page with all bids.
     *
     * @param model the model to add attributes for the view
     * @param authentication the authentication object to get user details
     * @return the name of the view to render
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, Authentication authentication)
    {
        log.info("Accessing bid list page");
        List<BidList> bidLists = bidListService.getAllBidList();
        model.addAttribute("bidLists", bidLists);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "bidList/list";
    }

    /**
     * Displays the form for adding a new bid.
     *
     * @param bid the bid object to initialize the form
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        log.info("Accessing adding bid page");
        BidListForm bidForm = bidListService.initBidFormFromBid(bid);
        model.addAttribute("bid", bidForm);

        return "bidList/add";
    }

    /**
     * Validates and adds a new bid to the list.
     *
     * @param bidForm the form containing bid details
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render or redirect if successful
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListForm bidForm, BindingResult result, Model model) {
        log.info("Validating bid form");
        if (result.hasErrors()) {
            log.error("Validation errors occurred while adding a bid: {}", result.getAllErrors());
            return "bidList/add";
        }

        try {
            log.info("Adding bid to list");
            BidList bid = bidListService.bidFormToBid(bidForm);
            bidListService.addBidList(bid);
        } catch (Exception e) {
            log.error("Error while adding a bid");
            result.rejectValue("bidListId", "error.bidListId", "Bid ID already exists");
            return "bidList/add";
        }

        log.info("Successfully added bid to list");
        return "redirect:/bidList/list";
    }

    /**
     * Displays the form for updating an existing bid.
     *
     * @param id the ID of the bid to update
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    /**
     * Validates and updates an existing bid.
     *
     * @param id the ID of the bid to update
     * @param bidForm the form containing updated bid details
     * @param result the binding result to capture validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render or redirect if successful
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListForm bidForm,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("bid", bidForm);
            return "bidList/update";
        }

        try {
            BidList bidList = bidListService.bidFormToBid(bidForm);
            bidListService.updateBid(id, bidList);
        } catch (Exception e) {
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "curvePoint/update";
        }

        return "redirect:/bidList/list";
    }

    /**
     * Deletes a bid from the list.
     *
     * @param id the ID of the bid to delete
     * @param model the model to add attributes for the view
     * @return the name of the view to redirect to
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidList(id);
        if (bidList != null) {
            bidListService.deleteBid(bidList.getId());
        }

        return "redirect:/bidList/list";
    }
}
