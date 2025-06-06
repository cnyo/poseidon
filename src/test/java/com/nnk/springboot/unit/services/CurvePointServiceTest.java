package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);
    }

    @Test
    public void getAllCurvePoint_mustReturnCurvePointList() throws IllegalArgumentException {
        // Arrange
        List<CurvePoint> curvePoints = List.of(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // Act
        List<CurvePoint> result = curvePointService.getAllCurvePoint();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals(10d, result.stream().findFirst().get().getTerm());
    }

    @Test
    public void addCurvePoint_mustReturnCurvePoint() throws IllegalArgumentException {
        // Arrange
        when(curvePointRepository.save(any())).thenReturn(curvePoint);

        // Act
        CurvePoint result = curvePointService.addCurvePoint(curvePoint);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertTrue(result.getTerm() == 10d);
    }

    @Test
    public void addCurvePoint_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> curvePointService.addCurvePoint(null));

        String expectedMessage = "CurvePoint cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getCurvePoint_mustReturnCurvePoint() throws IllegalArgumentException {
        // Arrange
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.ofNullable(curvePoint));

        // Act
        CurvePoint result = curvePointService.getCurvePoint(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(CurvePoint.class, result);
        Assertions.assertEquals(10d, result.getTerm());
    }

    @Test
    public void getCurvePoint_withNullId_mustThrowException() throws IllegalArgumentException {
        // Act
        assertThrows(IllegalArgumentException.class, () -> curvePointService.getCurvePoint(null));
    }

    @Test
    public void updateCurvePoint_mustReturnCurvePoint() throws IllegalArgumentException {
        // Arrange
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.ofNullable(curvePoint));
        curvePoint.setTerm(30d);
        when(curvePointRepository.save(any())).thenReturn(curvePoint);

        // Act
        CurvePoint result = curvePointService.updateCurvePoint(10, curvePoint);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(CurvePoint.class, result);
        Assert.assertTrue(curvePoint.getTerm() == 30d);
    }

    @Test
    public void updateCurvePoint_whenNotExistsCurvePoint_mustReturnnull() throws IllegalArgumentException {
        // Arrange
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());
        curvePoint.setTerm(30d);

        // Act
        CurvePoint result = curvePointService.updateCurvePoint(10, curvePoint);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void updateCurvePoint_withNullParams_mustThrowException() throws IllegalArgumentException {
        // Act
        assertThrows(IllegalArgumentException.class, () -> curvePointService.updateCurvePoint(null, curvePoint));
        assertThrows(IllegalArgumentException.class, () -> curvePointService.updateCurvePoint(10, null));
    }

    @Test
    public void deleteCurvePoint_shouldCallDeleteById() throws IllegalArgumentException {
        // Act
        curvePointService.deleteCurvePoint(curvePoint.getId());

        // Assert
        verify(curvePointRepository, times(1)).deleteById(curvePoint.getId());
    }
}
