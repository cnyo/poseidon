package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    /**
     * Retrieves all ratings from the database.
     *
     * @param rating the rating object to be used for filtering (if needed)
     * @return a list of all ratings
     */
    List<Rating> getAllRating();

    /**
     * Adds a new rating to the database.
     *
     * @param rating the rating object to be added
     * @return the added rating object
     * @throws IllegalArgumentException if the rating is null or invalid
     */
    Rating addRating(Rating rating) throws IllegalArgumentException;

    Rating getRating(Integer id);

    Rating updateRating(Integer id, Rating rating);

    void deleteRating(Integer id);
}
