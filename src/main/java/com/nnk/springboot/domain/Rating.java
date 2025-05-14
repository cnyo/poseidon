package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * Rating Java Bean
 * This class is a representation of the rating entity in the database.
 * It contains fields that correspond to the columns in the rating table.
 */
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    @Length(max = 125)
    private String moodysRating;

    @NotBlank(message = "SandP Rating is mandatory")
    @Length(max = 125)
    private String sandPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    @Length(max = 125)
    private String fitchRating;

    @NotNull(message = "Order Number is mandatory")
    private Integer orderNumber;

    public Rating() {}

    // "Moodys Rating", "Sand PRating", "Fitch Rating", 10
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        setMoodysRating(moodysRating);
        setSandPRating(sandPRating);
        setFitchRating(fitchRating);
        setOrderNumber(orderNumber);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
