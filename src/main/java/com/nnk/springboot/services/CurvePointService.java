package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurvePointService {
    List<CurvePoint> getAllCurvePoint();

    CurvePoint getCurvePoint(Integer id);

    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);

    void deleteCurvePoint(Integer id);

    CurvePoint addCurvePoint(CurvePoint curvePoint) throws IllegalArgumentException;
}
