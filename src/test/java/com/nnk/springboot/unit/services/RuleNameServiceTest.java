package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setUp() {
        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleName.setId(1);
    }

    @Test
    public void getAllRuleName_mustReturnRuleNameList() throws IllegalArgumentException {
        // Arrange
        List<RuleName> ruleNames = List.of(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        // Act
        List<RuleName> result = ruleNameService.getAllRuleName();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals("Rule Name", result.stream().findFirst().get().getName());
    }

    @Test
    public void saveRuleName_mustReturnRuleName() throws IllegalArgumentException {
        // Arrange
        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        // Act
        RuleName result = ruleNameService.addRuleName(ruleName);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertEquals("Rule Name", result.getName());
    }

    @Test
    public void saveRuleName_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ruleNameService.addRuleName(null));

        String expectedMessage = "RuleName cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getRuleName_mustReturnRuleName() throws IllegalArgumentException {
        // Arrange
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.ofNullable(ruleName));

        // Act
        RuleName result = ruleNameService.getRuleName(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(RuleName.class, result);
        Assertions.assertEquals("Rule Name", result.getName());
    }

    @Test
    public void updateRuleName_mustReturnRuleName() throws IllegalArgumentException {
        // Arrange
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.ofNullable(ruleName));
        ruleName.setName("Rule Name updated");
        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        // Act
        RuleName result = ruleNameService.updateRuleName(10, ruleName);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(RuleName.class, result);
        Assert.assertEquals("Rule Name updated", ruleName.getName());
    }

    @Test
    public void deleteRuleName_shouldCallDeleteById() throws IllegalArgumentException {
        // Act
        ruleNameService.deleteRuleName(ruleName.getId());

        // Assert
        verify(ruleNameRepository, times(1)).deleteById(ruleName.getId());
    }
}
