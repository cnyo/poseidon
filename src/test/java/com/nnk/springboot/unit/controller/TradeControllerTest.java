package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.LoginServiceImpl;
import com.nnk.springboot.services.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeServiceImpl tradeService;

    @MockitoBean
    private LoginServiceImpl loginService;

    static Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade();
        trade.setId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(10.0);
    }

    @Test
    @WithMockUser
    public void getAllTrade_thenReturnTradeListView() throws Exception {
        // Arrange
        List<Trade> trades = List.of(trade);

        when(tradeService.getAllTrade()).thenReturn(trades);
        when(loginService.getDisplayName(any())).thenReturn("username");

        // Act
        ResultActions result = mockMvc.perform(get("/trade/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("trades", trades))
                .andExpect(content().string(containsString("username")))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddTradeForm_thenReturnTradeFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/trade/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postTrade_thenRedirectToTradeListView() throws Exception {
        // Arrange
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        when(tradeService.saveTrade(any())).thenReturn(trade);

        // Act
        ResultActions result = mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account")
                .param("type", "type")
                .param("buyQuantity", "10.0"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getTradeById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(tradeService.getTrade(any())).thenReturn(trade);

        // Act
        ResultActions result = mockMvc.perform(get("/trade/update/{id}", trade.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateTrade_thenRedirectToTradeListView() throws Exception {
        // Arrange
        when(tradeService.updateTrade(anyInt(), any())).thenReturn(trade);

        // Act
        ResultActions result = mockMvc.perform(post("/trade/update/{id}", trade.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "account")
                .param("type", "type")
                .param("buyQuantity", "10.0"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteTrade_thenRedirectTradeListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/trade/delete/{id}", trade.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
