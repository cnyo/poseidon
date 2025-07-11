package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;
import com.nnk.springboot.services.LoginServiceImpl;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListServiceImpl bidListService;

    @MockitoBean
    private LoginServiceImpl loginService;

    static BidList bid;

    @BeforeEach
    public void setUp() {
        bid = new BidList();
        bid.setId(1);
        bid.setBidQuantity(2D);
        bid.setAskQuantity(3D);
        bid.setBid(4D);
        bid.setAsk(5D);
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBenchmark("Benchmark");
        bid.setCommentary("Commentary");
        bid.setSecurity("Security");
        bid.setStatus("Status");
        bid.setBidListDate(new java.sql.Timestamp(System.currentTimeMillis()));
        bid.setTrader("Trader");
        bid.setBook("Book");
        bid.setCreationName("Message");
        bid.setCreationDate(new java.sql.Timestamp(System.currentTimeMillis()));
        bid.setRevisionName("Revision");
        bid.setRevisionDate(new java.sql.Timestamp(System.currentTimeMillis()));
        bid.setDealName("Deal");
        bid.setDealType("DealType");
        bid.setSourceListId("SourceListId");
        bid.setSide("Side");
    }

    @Test
    @WithMockUser
    public void getAllBidList_thenReturnBidListView() throws Exception {
        // Arrange
        List<BidList> bidLists = List.of(bid);

        when(bidListService.getAllBidList()).thenReturn(bidLists);
        when(loginService.getDisplayName(any())).thenReturn("username");

        // Act
        ResultActions result = mockMvc.perform(get("/bidList/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attribute("bidLists", bidLists))
                .andExpect(content().string(containsString("username")))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddBidListForm_thenReturnBidListFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/bidList/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postBidList_thenRedirectToBidListListView() throws Exception {
        // Arrange
        when(bidListService.bidFormToBid(any())).thenReturn(bid);
        when(bidListService.addBidList(any())).thenReturn(bid);

        // Act
        ResultActions result = mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", bid.getAccount())
                .param("type", "Type")
                .param("bidQuantity", "2"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getBidListById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(bidListService.getBidList(any())).thenReturn(bid);

        // Act
        ResultActions result = mockMvc.perform(get("/bidList/update/{id}", bid.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", bid));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateBidList_thenRedirectToBidListListView() throws Exception {
        // Arrange
        when(bidListService.bidFormToBid(any())).thenReturn(bid);
        when(bidListService.updateBid(anyInt(), any())).thenReturn(bid);

        // Act
        ResultActions result = mockMvc.perform(post("/bidList/update/{id}", bid.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", bid.getAccount())
                .param("type", "Type")
                .param("bidQuantity", "2"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteBidList_thenRedirectBidListListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/bidList/delete/{id}", bid.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
