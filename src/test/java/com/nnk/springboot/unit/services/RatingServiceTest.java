package com.nnk.springboot.unit.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;
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
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setId(20);
    }

    @Test
    public void getAllRating_mustReturnRatingList() throws IllegalArgumentException {
        // Arrange
        List<Rating> ratings = List.of(rating);

        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Rating> result = ratingService.getAllRating();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.stream().findFirst().isPresent());
        Assertions.assertNotNull(result.stream().findFirst().get().getId());
        Assertions.assertEquals(10, result.stream().findFirst().get().getOrder());
    }

    @Test
    public void saveRating_mustReturnRating() throws IllegalArgumentException {
        // Arrange
        Rating insertedRating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        insertedRating.setId(1);

        when(ratingRepository.save(any())).thenReturn(insertedRating);

        // Act
        Rating result = ratingService.addRating(rating);

        // Assert
        Assert.assertNotNull(result.getId());
        Assert.assertTrue(result.getOrder() == 10);
    }

    @Test
    public void saveRating_withNullData_mustReturnException() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(null));

        String expectedMessage = "Rating cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getRating_mustReturnRating() throws IllegalArgumentException {
        // Arrange
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.ofNullable(rating));

        // Act
        Rating result = ratingService.getRating(10);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Rating.class, result);
        Assertions.assertEquals(10, result.getOrder());
    }

    @Test
    public void updateRating_mustReturnRating() throws IllegalArgumentException {
        // Arrange
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.ofNullable(rating));
        rating.setOrder(30);
        when(ratingRepository.save(any())).thenReturn(rating);

        // Act
        Rating result = ratingService.updateRating(10, rating);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Rating.class, result);
        Assert.assertTrue(rating.getOrder() == 30);
    }

    @Test
    public void deleteRating() throws IllegalArgumentException {
        // Act
        ratingService.deleteRating(rating.getId());

        // Assert
        verify(ratingRepository, times(1)).deleteById(rating.getId());
    }
}
