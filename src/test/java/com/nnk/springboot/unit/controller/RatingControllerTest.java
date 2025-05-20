package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RatingService ratingService;

    @Test
    @WithMockUser(username = "user")
    public void getAllRating_thenReturnRatingListView() throws Exception {
//    public void getAllRatings_whenUserIsAuthenticated_thenReturnRatingListView() throws Exception {
//    public void testGetAllRatingForm() throws Exception {
//    public void methodName_whenCondition_thenExpectedResult() throws Exception {
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

    @Test
    @WithMockUser(username = "user")
    public void postRating_thenRedirectToList() throws Exception {
        // Arrange
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        when(ratingService.addRating(any())).thenReturn(rating);

        // Act
        ResultActions result = mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderNumber", "10")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("version", "1"));

        // Assert
        result
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(redirectedUrl("/rating/list"))
                .andReturn();
    }

}
