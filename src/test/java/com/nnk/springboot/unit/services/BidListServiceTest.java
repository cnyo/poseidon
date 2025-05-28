package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListServiceImpl;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    private BidList bidList;

    @BeforeEach
    public void setUp() {
        bidList = new BidList("Account Test", "Type Test", 10d);
        bidList.setId(1);
    }

    @Test
    public void getAllBidList_mustReturnBidListList() throws IllegalArgumentException {
        // Arrange
        List<BidList> bidLists = List.of(bidList);

        when(bidListRepository.findAll()).thenReturn(bidLists);

        // Act
        List<BidList> result = bidListService.getAllBidList();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals("Account Test", result.stream().findFirst().get().getAccount());
    }

    @Test
    public void saveBidList_mustReturnBidList() throws IllegalArgumentException {
        // Arrange
        when(bidListRepository.save(any())).thenReturn(bidList);

        // Act
        BidList result = bidListService.addBidList(bidList);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertEquals("Account Test", result.getAccount());
    }

    @Test
    public void saveBidList_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bidListService.addBidList(null));

        String expectedMessage = "BidList cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getBidList_mustReturnBidList() throws IllegalArgumentException {
        // Arrange
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.ofNullable(bidList));

        // Act
        BidList result = bidListService.getBidList(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(BidList.class, result);
        Assertions.assertEquals("Account Test", result.getAccount());
    }

    @Test
    public void updateBidList_mustReturnBidList() throws IllegalArgumentException {
        // Arrange
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.ofNullable(bidList));
        bidList.setAccount("Account Test updated");
        when(bidListRepository.save(any())).thenReturn(bidList);

        // Act
        BidList result = bidListService.updateBid(10, bidList);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(BidList.class, result);
        Assert.assertEquals("Account Test updated", bidList.getAccount());
    }

    @Test
    public void initBidFormFromBid_mustReturnFormBidList() throws IllegalArgumentException {
        // Act
        BidListForm result = bidListService.initBidFormFromBid(bidList);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(BidListForm.class, result);
        Assert.assertEquals("Account Test", result.getAccount());
    }

    @Test
    public void bidFormToBid_mustReturnBidList() throws IllegalArgumentException {
        // Arrange
        BidListForm bidListForm = new BidListForm();
        bidListForm.setAccount(bidList.getAccount());
        bidListForm.setType(bidList.getType());
        bidListForm.setBidQuantity(bidList.getBidQuantity());

        // Act
        BidList result = bidListService.bidFormToBid(bidListForm);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(BidList.class, result);
        Assert.assertEquals("Account Test", result.getAccount());
    }

    @Test
    public void deleteBidList_shouldCallDeleteById() throws IllegalArgumentException {
        // Act
        bidListService.deleteBid(bidList.getId());

        // Assert
        verify(bidListRepository, times(1)).deleteById(bidList.getId());
    }
}
