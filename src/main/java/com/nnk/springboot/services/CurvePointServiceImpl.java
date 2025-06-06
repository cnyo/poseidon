package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint getCurvePoint(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("CurvePoint ID cannot be null");
        }

        return curvePointRepository.findById(id).orElse(null);
    }

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

    @Override
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }

    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) throws IllegalArgumentException {
        if (curvePoint == null) {
            throw new IllegalArgumentException("CurvePoint cannot be null");
        }

        return curvePointRepository.save(curvePoint);
    }
}
