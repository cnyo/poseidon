package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointServiceImpl;
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

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointServiceImpl curvePointService;

    @MockitoBean
    private LoginServiceImpl loginService;

    static CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(10);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
    }

    @Test
    @WithMockUser
    public void getAllCurvePoint_thenReturnCurvePointListView() throws Exception {
        // Arrange
        List<CurvePoint> curvePoints = List.of(curvePoint);

        when(curvePointService.getAllCurvePoint()).thenReturn(curvePoints);
        when(loginService.getDisplayName(any())).thenReturn("username");

        // Act
        ResultActions result = mockMvc.perform(get("/curvePoint/list"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attribute("curvePoints", curvePoints))
                .andExpect(content().string(containsString("username")))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user")
    public void getAddCurvePointForm_thenReturnCurvePointFormView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/curvePoint/add"));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user")
    public void postCurvePoint_thenRedirectToCurvePointListView() throws Exception {
        // Arrange
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
        curvePoint.setAsOfDate(new Timestamp(System.currentTimeMillis()));
        when(curvePointService.addCurvePoint(any())).thenReturn(curvePoint);

        // Act
        ResultActions result = mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "10")
                .param("term", "10d")
                .param("value", "30d"));

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getCurvePointById_thenShowUpdateFormView() throws Exception {
        // Arrange
        when(curvePointService.getCurvePoint(any())).thenReturn(curvePoint);

        // Act
        ResultActions result = mockMvc.perform(get("/curvePoint/update/{id}", curvePoint.getId()));

        // Assert
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", curvePoint));
    }

    @Test
    @WithMockUser(username = "user")
    public void updateCurvePoint_thenRedirectToCurvePointListView() throws Exception {
        // Arrange
        when(curvePointService.updateCurvePoint(anyInt(), any())).thenReturn(curvePoint);

        // Act
        ResultActions result = mockMvc.perform(post("/curvePoint/update/{id}", curvePoint.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "10")
                .param("term", "10d")
                .param("value", "30d")
        );

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user")
    public void deleteCurvePoint_thenRedirectCurvePointListView() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(delete("/curvePoint/delete/{id}", curvePoint.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        // Assert
        result.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
