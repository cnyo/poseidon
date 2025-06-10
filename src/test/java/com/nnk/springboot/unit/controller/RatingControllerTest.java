package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.form.RatingForm;
import com.nnk.springboot.services.LoginServiceImpl;
import com.nnk.springboot.services.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingServiceImpl ratingService;

    @MockitoBean
    private LoginServiceImpl loginService;

    static Rating rating;

    static RatingForm ratingForm;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("30");
        rating.setSandPRating("30");
        rating.setFitchRating("30");
        rating.setOrder(10);

        ratingForm = new RatingForm();
        ratingForm.setId(1);
        ratingForm.setMoodysRating(Integer.parseInt(rating.getMoodysRating()));
        ratingForm.setSandPRating(Integer.parseInt(rating.getSandPRating()));
        ratingForm.setFitchRating(Integer.parseInt(rating.getFitchRating()));
        ratingForm.setOrder(rating.getOrder());
    }

    @Test
    @WithMockUser
    public void getAllTrade_thenReturnTradeListView() throws Exception {
        // Arrange
        List<Rating> ratings = List.of(rating);

        when(ratingService.getAllRating()).thenReturn(ratings);
        when(loginService.getDisplayName(any())).thenReturn("username");

        // Act
        ResultActions result = mockMvc.perform(get("/rating/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", ratings))
                .andExpect(content().string(containsString("username")))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void getAddRatingForm_thenReturnRatingFormView() throws Exception {
        // Arrange
        when(ratingService.ratingToForm(any())).thenReturn(ratingForm);

        // Act
        ResultActions result = mockMvc.perform(get("/rating/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser
    public void postRating_thenRedirectToRatingListView() throws Exception {
        // Arrange
        when(ratingService.formToRating(any())).thenReturn(rating);
        when(ratingService.addRating(any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("moodysRating", "30")
                .param("sandPRating", "30")
                .param("fitchRating", "30")
                .param("order", "10"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser
    public void postRating_whenInvalidParam_thenReturnRatingFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "30")
                .param("sandPRating", "test")
                .param("fitchRating", "30")
                .param("order", "10"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser
    public void getRatingById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(ratingService.getRating(any())).thenReturn(rating);
        when(ratingService.ratingToForm(any())).thenReturn(ratingForm);

        // Act
        ResultActions result = mockMvc.perform(get("/rating/update/{id}", ratingForm.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", ratingForm));
    }

    @Test
    @WithMockUser
    public void updateRating_withIntegerParams_thenRedirectToRatingListView() throws Exception {
        // Arrange
        when(ratingService.updateRating(anyInt(), any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(post("/rating/update/{id}", rating.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "10")
                .param("sandPRating", "10")
                .param("fitchRating", "10")
                .param("order", "10"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser
    public void updateRating_withStringParams_thenErrorForm() throws Exception {
        // Arrange
        when(ratingService.updateRating(anyInt(), any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(post("/rating/update/{id}", rating.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Moodys")
                .param("sandPRating", "S&P")
                .param("fitchRating", "Fitch")
                .param("order", "10"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    public void deleteRating_thenRedirectRatingListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/rating/delete/{id}", rating.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
