package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.form.RatingForm;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Service interface for managing ratings.
 * Provides methods to retrieve, add, update, and delete ratings,
 * as well as to convert between Rating and RatingForm objects.
 */
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

    /**
     * Retrieves a rating by its ID.
     *
     * @param id the ID of the rating to retrieve
     * @return the rating object with the specified ID, or null if not found
     */
    Rating getRating(Integer id);

    /**
     * Updates an existing rating in the database.
     *
     * @param id the ID of the rating to update
     * @param rating the updated rating object
     * @return the updated rating object
     * @throws IllegalArgumentException if the ID or rating is null or invalid
     */
    Rating updateRating(Integer id, Rating rating);

    /**
     * Deletes a rating by its ID.
     *
     * @param id the ID of the rating to delete
     */
    void deleteRating(Integer id);

    /**
     * Converts a RatingForm object to a Rating object.
     *
     * @param ratingForm the RatingForm object to convert
     * @return the converted Rating object
     * @throws IllegalArgumentException if the ratingForm is null or invalid
     */
    Rating formToRating(@Valid RatingForm ratingForm) throws IllegalArgumentException;

    /**
     * Converts a Rating object to a RatingForm object.
     *
     * @param rating the Rating object to convert
     * @return the converted RatingForm object
     * @throws IllegalArgumentException if the rating is null or invalid
     */
    RatingForm ratingToForm(Rating rating) throws IllegalArgumentException;
}
