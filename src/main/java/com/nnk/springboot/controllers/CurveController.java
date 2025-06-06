package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

/**
 * Controller for managing curve points.
 * Provides endpoints for listing, adding, updating, and deleting curve points.
 */
@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private LoginService loginService;

    /**
     * Displays the curve point list page with all curve points.
     *
     * @param model the model to add attributes for the view
     * @param authentication the authentication object to get user details
     * @return the name of the view to render
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication)
    {
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();
        model.addAttribute("curvePoints", curvePoints);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "curvePoint/list";
    }

    /**
     * Displays the form for adding a new curve point.
     *
     * @param curvePoint the curve point object to initialize the form
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/add";
    }

    /**
     * Validates and adds a new curve point.
     *
     * @param curvePoint the curve point object to validate and add
     * @param result the binding result to hold validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render, or redirect if successful
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        try {
            curvePointService.addCurvePoint(curvePoint);

            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "curvePoint/add";
        }
    }

    /**
     * Displays the form for updating an existing curve point.
     *
     * @param id the ID of the curve point to update
     * @param model the model to add attributes for the view
     * @return the name of the view to render
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

    /**
     * Validates and updates an existing curve point.
     *
     * @param id the ID of the curve point to update
     * @param curvePoint the updated curve point object
     * @param result the binding result to hold validation errors
     * @param model the model to add attributes for the view
     * @return the name of the view to render, or redirect if successful
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curvePoint", curvePoint);

            return "curvePoint/update";
        }

        try {
            curvePointService.updateCurvePoint(id, curvePoint);
            // TODO: log
        } catch (Exception e) {
            result.rejectValue("curveId", "error.curveId", "Curve ID already exists");
            return "curvePoint/update";
        }

        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete
     * @return the name of the view to redirect to
     */
    @DeleteMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        if (curvePoint != null) {
            curvePointService.deleteCurvePoint(curvePoint.getId());
        }

        return "redirect:/curvePoint/list";
    }
}
