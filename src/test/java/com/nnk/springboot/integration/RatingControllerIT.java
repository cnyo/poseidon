package com.nnk.springboot.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RatingControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user")
    public void getAllRating_thenReturnRatingListView() throws Exception {
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
