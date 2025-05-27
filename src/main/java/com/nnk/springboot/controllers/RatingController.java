package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    private final Logger log = LogManager.getLogger(LoginController.class);

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        log.info("Get rating list page");
        List<Rating> ratings = ratingService.getAllRating();
        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        log.info("Add rating form");
        model.addAttribute("rating", new Rating());

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("Validate rating form");

        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            return "rating/add";
        }

        try {
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
        Rating rating = ratingService.getRating(id);
        model.addAttribute("rating", rating);
        log.info("Rating updated");

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        log.info("Update rating form");

        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            model.addAttribute("rating", rating);

            return "rating/update";
        }

        try {
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
