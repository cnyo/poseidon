package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.form.RatingForm;
import com.nnk.springboot.services.LoginService;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private LoginService loginService;

    private final Logger log = LogManager.getLogger(RatingController.class);

    @RequestMapping("/rating/list")
    public String home(Model model, Authentication authentication)
    {
        log.info("Get rating list page");
        List<Rating> ratings = ratingService.getAllRating();
        model.addAttribute("ratings", ratings);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        log.info("Add rating form");
        model.addAttribute("rating", new RatingForm());

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingForm ratingForm, BindingResult result, Model model) {
        log.info("Validate rating form");

        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            model.addAttribute("rating", ratingForm);
            return "rating/add";
        }

        try {
            Rating rating = ratingService.formToRating(ratingForm);
            ratingService.addRating(rating);
            log.info("Rating added");

            return "redirect:/rating/list";
        } catch (Exception e) {
            log.error(e.getMessage());

            return "rating/add";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Get rating form");
        try {
            Rating rating = ratingService.getRating(id);
            RatingForm ratingForm = ratingService.ratingToForm(rating);
            model.addAttribute("rating", ratingForm);

            return "rating/update";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/rating/list";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingForm ratingForm,
                             BindingResult result, Model model) {
        log.info("Update rating form");

        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            model.addAttribute("rating", ratingForm);

            return "rating/update";
        }

        try {
            Rating rating = ratingService.formToRating(ratingForm);
            ratingService.updateRating(id, rating);
            log.info("Rating updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "rating/update";
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        log.info("Delete rating form");
        Rating rating = ratingService.getRating(id);
        if (rating != null) {
            ratingService.deleteRating(rating.getId());
            log.info("Rating deleted");
        }

        return "redirect:/rating/list";
    }
}
