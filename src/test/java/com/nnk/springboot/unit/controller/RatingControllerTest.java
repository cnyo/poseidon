package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
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

    static Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys");
        rating.setSandPRating("S&P");
        rating.setFitchRating("Fitch");
        rating.setOrder(10);
    }

    @Test
    @WithMockUser(username = "user")
    public void getAllTrade_thenReturnTradeListView() throws Exception {
        // Arrange
        List<Rating> ratings = List.of(rating);

        when(ratingService.getAllRating()).thenReturn(ratings);

        // Act
        ResultActions result = mockMvc.perform(get("/rating/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", ratings))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddRatingForm_thenReturnRatingFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/rating/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postRating_thenRedirectToRatingListView() throws Exception {
        // Arrange
        when(ratingService.addRating(any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("moodysRating", "Moodys")
                .param("sandPRating", "S&P")
                .param("fitchRating", "Fitch")
                .param("order", "10"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getRatingById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(ratingService.getRating(any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(get("/rating/update/{id}", rating.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateRating_thenRedirectToRatingListView() throws Exception {
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
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteRating_thenRedirectRatingListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/rating/delete/{id}", rating.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
