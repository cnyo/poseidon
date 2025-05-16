package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    public void getAllRating_mustReturnRatingList() throws IllegalArgumentException {
        // Arrange
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setId(20);
        List<Rating> ratings = List.of(rating);

        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Rating> result = ratingService.getAllRating();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals(10, result.stream().findFirst().get().getOrderNumber());
    }

    @Test
    public void saveRating_mustReturnRating() throws IllegalArgumentException {
        // Arrange
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        Rating insertedRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        insertedRating.setId(1);

        when(ratingRepository.save(any())).thenReturn(insertedRating);

        // Act
        Rating result = ratingService.addRating(rating);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertTrue(result.getOrderNumber() == 10);

        // Update
//        rating.setOrderNumber(20);
//        rating = ratingRepository.save(rating);
//        Assert.assertTrue(rating.getOrderNumber() == 20);

        // Find
//        List<Rating> listResult = ratingRepository.findAll();
//        Assert.assertTrue(listResult.size() > 0);

        // Delete
//        Integer id = rating.getId();
//        ratingRepository.delete(rating);
//        Optional<Rating> ratingList = ratingRepository.findById(id);
//        Assert.assertFalse(ratingList.isPresent());
    }

    @Test
    public void saveRating_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(null));

        String expectedMessage = "Rating cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
