package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameServiceImpl ruleNameService;

    static RuleName ruleName;

    @BeforeEach
    public void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("RuleName");
        ruleName.setDescription("Description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Template");
        ruleName.setSql("SqlStr");
        ruleName.setSqlPart("SqlPart");
    }

    @Test
    @WithMockUser(username = "user")
    public void getAllBidList_thenReturnRuleNameListView() throws Exception {
        // Arrange
        List<RuleName> ruleNames = List.of(ruleName);

        when(ruleNameService.getAllRuleName()).thenReturn(ruleNames);

        // Act
        ResultActions result = mockMvc.perform(get("/ruleName/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attribute("ruleNames", ruleNames))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddRuleNameForm_thenReturnRuleNameFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/ruleName/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postRuleName_thenRedirectToRuleNameListView() throws Exception {
        // Arrange
        when(ruleNameService.addRuleName(any())).thenReturn(ruleName);

        // Act
        ResultActions result = mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sql", ruleName.getSql())
                .param("sqlPart", ruleName.getSqlPart()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getRuleNameById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(ruleNameService.getRuleName(any())).thenReturn(ruleName);

        // Act
        ResultActions result = mockMvc.perform(get("/ruleName/update/{id}", ruleName.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", ruleName));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateRuleName_thenRedirectToRuleNameListView() throws Exception {
        // Arrange
        when(ruleNameService.updateRuleName(anyInt(), any())).thenReturn(ruleName);

        // Act
        ResultActions result = mockMvc.perform(post("/ruleName/update/{id}", ruleName.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sql", ruleName.getSql())
                .param("sqlPart", ruleName.getSqlPart()));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteRuleName_thenRedirectRuleNameListView() throws Exception {
        // Arrange
        when(ruleNameService.deleteRuleName(any())).thenReturn(true);

        // Act
        ResultActions result = mockMvc.perform(get("/ruleName/delete/{id}", ruleName.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
