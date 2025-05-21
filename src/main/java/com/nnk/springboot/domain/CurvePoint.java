package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * CurvePoint Java Bean
 * This class is a representation of the curve point entity in the database.
 * It contains fields that correspond to the columns in the curve point table.
 */
@Entity
@Table(name = "curve_point")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer curveId;

    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    private Double term;

    @NotNull(message = "Value is mandatory")
    private Double value;

    private Timestamp creationDate;

    public void setId(Integer id) {
        this.id = id;
    }

    public CurvePoint() {}

    public CurvePoint(Integer curveId, Double term, Double value) {
        setCurveId(curveId);
        setTerm(term);
        setValue(value);
    }

    public Integer getId() {
        return id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
