package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.form.RatingForm;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the RatingService interface.
 * Provides methods to manage ratings, including CRUD operations and conversions between Rating and RatingForm.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    private final Logger log = LogManager.getLogger(RatingServiceImpl.class);

    /**
     * Retrieves all ratings from the database.
     *
     * @return a list of all ratings
     */
    @Override
    public List<Rating> getAllRating() {

        return ratingRepository.findAll();
    }

    /**
     * Adds a new rating to the database.
     *
     * @param rating the rating object to be added
     * @return the added rating object
     * @throws IllegalArgumentException if the rating is null or invalid
     */
    @Override
    public Rating addRating(Rating rating) throws IllegalArgumentException {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        return ratingRepository.save(rating);
    }

    /**
     * Retrieves a rating by its ID.
     *
     * @param id the ID of the rating to retrieve
     * @return the rating object with the specified ID, or null if not found
     */
    @Override
    public Rating getRating(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Rating ID cannot be null");
        }

        return ratingRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing rating in the database.
     *
     * @param id the ID of the rating to update
     * @param rating the updated rating object
     * @return the updated rating object, or null if the rating with the specified ID does not exist
     * @throws IllegalArgumentException if the ID or rating is null or invalid
     */
    @Override
    public Rating updateRating(Integer id, Rating rating) {
        if (id == null || rating == null) {
            throw new IllegalArgumentException("Rating ID and Rating cannot be null");
        }

        Rating existingRating = ratingRepository.findById(id).orElse(null);

        if (existingRating != null) {
            existingRating.setMoodysRating(rating.getMoodysRating());
            existingRating.setSandPRating(rating.getSandPRating());
            existingRating.setFitchRating(rating.getFitchRating());
            existingRating.setOrder(rating.getOrder());

            Rating result =  ratingRepository.save(existingRating);

            return result;
        }

        return null;
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param id the ID of the rating to delete
     */
    @Override
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    /**
     * Converts a RatingForm object to a Rating object.
     *
     * @param ratingForm the RatingForm object to convert
     * @return the converted Rating object
     * @throws IllegalArgumentException if the ratingForm is null or invalid
     */
    @Override
    public Rating formToRating(RatingForm ratingForm) throws IllegalArgumentException {
        if (ratingForm != null) {
            Rating rating = new Rating();
            rating.setMoodysRating(Integer.toString(ratingForm.getMoodysRating()));
            rating.setSandPRating(Integer.toString(ratingForm.getSandPRating()));
            rating.setFitchRating(Integer.toString(ratingForm.getFitchRating()));
            rating.setOrder(ratingForm.getOrder());

            return rating;
        }

        log.info("Rating form is null");
        throw new IllegalArgumentException("Rating form cannot be null");
    }

    /**
     * Converts a Rating object to a RatingForm object.
     *
     * @param rating the Rating object to convert
     * @return the converted RatingForm object
     * @throws IllegalArgumentException if the rating is null or invalid
     */
    @Override
    public RatingForm ratingToForm(Rating rating) throws IllegalArgumentException {
        log.debug("Converting Rating to RatingForm");
        if (rating == null) {
            log.info("Rating is null");
            throw new IllegalArgumentException("Rating cannot be null");
        }

        RatingForm ratingForm = new RatingForm();
        ratingForm.setId(rating.getId());
        ratingForm.setMoodysRating(Integer.parseInt(rating.getMoodysRating()));
        ratingForm.setSandPRating(Integer.parseInt(rating.getSandPRating()));
        ratingForm.setFitchRating(Integer.parseInt(rating.getFitchRating()));
        ratingForm.setOrder(rating.getOrder());
        log.debug("Converted Rating to RatingForm");

        return ratingForm;
    }
}
