package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

/**
 * Service interface for managing CurvePoints.
 * Provides methods to retrieve, update, delete, and add CurvePoints.
 */
public interface CurvePointService {

    /**
     * Retrieves all CurvePoints.
     *
     * @return a list of all CurvePoints
     */
    List<CurvePoint> getAllCurvePoint();

    /**
     * Retrieves a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to retrieve
     * @return the CurvePoint with the specified ID, or null if not found
     */
    CurvePoint getCurvePoint(Integer id);

    /**
     * Updates an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update
     * @param curvePoint the updated CurvePoint data
     * @return the updated CurvePoint
     */
    CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);

    /**
     * Deletes a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to delete
     */
    void deleteCurvePoint(Integer id);

    /**
     * Adds a new CurvePoint.
     *
     * @param curvePoint the CurvePoint to add
     * @return the added CurvePoint
     * @throws IllegalArgumentException if the curvePoint is null or invalid
     */
    CurvePoint addCurvePoint(CurvePoint curvePoint) throws IllegalArgumentException;
}
