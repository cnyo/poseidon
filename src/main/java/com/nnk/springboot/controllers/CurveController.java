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

@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication)
    {
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();
        model.addAttribute("curvePoints", curvePoints);
        model.addAttribute("displayName", loginService.getDisplayName(authentication));

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/add";
    }

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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

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

    @DeleteMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);
        if (curvePoint != null) {
            curvePointService.deleteCurvePoint(curvePoint.getId());
        }

        return "redirect:/curvePoint/list";
    }
}
