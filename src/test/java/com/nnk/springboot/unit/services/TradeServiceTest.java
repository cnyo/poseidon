package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;
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
public class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        trade = new Trade("Trade Account", "Type", 1D);
        trade.setId(1);
    }

    @Test
    public void getAllTrade_mustReturnTradeList() throws IllegalArgumentException {
        // Arrange
        List<Trade> trades = List.of(trade);

        when(tradeRepository.findAll()).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.getAllTrade();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals("Trade Account", result.stream().findFirst().get().getAccount());
    }

    @Test
    public void saveTrade_mustReturnTrade() throws IllegalArgumentException {
        // Arrange
        when(tradeRepository.save(any())).thenReturn(trade);

        // Act
        Trade result = tradeService.addTrade(trade);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertEquals("Trade Account", result.getAccount());
    }

    @Test
    public void saveTrade_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.addTrade(null));

        String expectedMessage = "Trade cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getTrade_mustReturnTrade() throws IllegalArgumentException {
        // Arrange
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(trade));

        // Act
        Trade result = tradeService.getTrade(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Trade.class, result);
        Assertions.assertEquals("Trade Account", result.getAccount());
    }

    @Test
    public void updateTrade_mustReturnTrade() throws IllegalArgumentException {
        // Arrange
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(trade));
        trade.setAccount("Trade Account updated");
        when(tradeRepository.save(any())).thenReturn(trade);

        // Act
        Trade result = tradeService.updateTrade(10, trade);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Trade.class, result);
        Assert.assertEquals("Trade Account updated", trade.getAccount());
    }

    @Test
    public void deleteTrade_shouldCallDeleteById() throws IllegalArgumentException {
        // Act
        tradeService.deleteTrade(trade.getId());

        // Assert
        verify(tradeRepository, times(1)).deleteById(trade.getId());
    }
}
