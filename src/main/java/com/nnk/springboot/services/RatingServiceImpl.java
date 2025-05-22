package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> getAllRating() {

        return ratingRepository.findAll();
    }

    @Override
    public Rating addRating(Rating rating) throws IllegalArgumentException {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        return ratingRepository.save(rating);
    }

    @Override
    public Rating getRating(Integer id) {
        return null;
    }

    @Override
    public Rating updateRating(Integer id, Rating rating) {
        return null;
    }

    @Override
    public void deleteRating(Integer id) {

    }
}
