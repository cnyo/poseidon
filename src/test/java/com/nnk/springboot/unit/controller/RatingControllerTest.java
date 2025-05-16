package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = RatingController.class)
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RatingService ratingService;

    @Test
    @WithMockUser(username = "user")
    public void testGetAllRatingForm() throws Exception {
        // Arrange
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setOrderNumber(20);
        List<Rating> ratings = List.of(rating);

        when(ratingService.getAllRating()).thenReturn(ratings);

        // Act
        ResultActions result = mockMvc.perform(get("/rating/list"));

        // Assert
        result
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andReturn();
    }

}
