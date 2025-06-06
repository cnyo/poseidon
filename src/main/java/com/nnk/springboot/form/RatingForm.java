package com.nnk.springboot.form;

import jakarta.validation.constraints.NotNull;

public class RatingForm {
    @NotNull(message = "Moodys Rating is mandatory")
    private Integer id;

    @NotNull(message = "Moodys Rating is mandatory")
    private Integer moodysRating;

    @NotNull(message = "SandP Rating is mandatory")
    private Integer sandPRating;

    @NotNull(message = "Fitch Rating is mandatory")
    private Integer fitchRating;

    @NotNull(message = "Order Number is mandatory")
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(Integer moodysRating) {
        this.moodysRating = moodysRating;
    }

    public Integer getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(Integer sandPRating) {
        this.sandPRating = sandPRating;
    }

    public Integer getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(Integer fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
