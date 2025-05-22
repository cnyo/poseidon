package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        List<Rating> ratings = ratingService.getAllRating();
        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        model.addAttribute("rating", new Rating());

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }

        try {
            ratingService.addRating(rating);

            return "redirect:/rating/list";
        } catch (Exception e) {
            // TODO: add an error message
            // TODO: log exception
            return "rating/add";
        }

    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRating(id);
        model.addAttribute("rating", rating);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rating", rating);

            return "rating/update";
        }
        try {
            ratingService.updateRating(id, rating);
        } catch (Exception e) {
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "curvePoint/update";
        }


        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRating(id);
        if (rating != null) {
            ratingService.deleteRating(rating.getId());
        }

        return "redirect:/rating/list";
    }
}
