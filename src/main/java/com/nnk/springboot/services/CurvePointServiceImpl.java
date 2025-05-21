package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return null;
    }

    @Override
    public CurvePoint getCurvePoint(Integer id) {
        return null;
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return null;
    }

    @Override
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        return null;
    }

    @Override
    public Boolean deleteCurvePoint(Integer id) {
        return true;
    }
}
