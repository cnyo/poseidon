package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the CurvePointService interface.
 * Provides methods to manage CurvePoints, including retrieval, saving, updating, and deleting.
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    private final Logger log = LogManager.getLogger(CurvePointServiceImpl.class);

    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to retrieve
     * @return the CurvePoint with the specified ID, or null if not found
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public CurvePoint getCurvePoint(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("CurvePoint ID cannot be null");
        }

        return curvePointRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update
     * @param curvePoint the updated CurvePoint data
     * @return the updated CurvePoint
     * @throws IllegalArgumentException if the ID or curvePoint is null
     */
    @Override
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        if (id == null || curvePoint == null) {
            throw new IllegalArgumentException("CurvePoint ID and CurvePoint cannot be null");
        }

        CurvePoint existingCurvePoint = curvePointRepository.findById(id).orElse(null);

        if (existingCurvePoint != null) {
            existingCurvePoint.setCurveId(curvePoint.getCurveId());
            existingCurvePoint.setTerm(curvePoint.getTerm());
            existingCurvePoint.setValue(curvePoint.getValue());
            existingCurvePoint.setCreationDate(curvePoint.getCreationDate());
            existingCurvePoint.setAsOfDate(curvePoint.getAsOfDate());

            return curvePointRepository.save(existingCurvePoint);
        }

        return null;
    }

    /**
     * Deletes a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to delete
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }

    /**
     * Adds a new CurvePoint.
     *
     * @param curvePoint the CurvePoint to add
     * @return the added CurvePoint
     * @throws IllegalArgumentException if the curvePoint is null or invalid
     */
    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) throws IllegalArgumentException {
        if (curvePoint == null) {
            log.error("Attempted to add a null CurvePoint");
            throw new IllegalArgumentException("CurvePoint cannot be null");
        }

        log.debug("Adding a new CurvePoint");
        return curvePointRepository.save(curvePoint);
    }
}
