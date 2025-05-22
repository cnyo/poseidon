package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    @Override
    public List<RuleName> getAllRuleName() {
        return List.of();
    }

    @Override
    public RuleName getRuleName(Integer id) {
        return null;
    }

    @Override
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        return null;
    }

    @Override
    public Boolean deleteRuleName(Integer id) {
        return null;
    }

    @Override
    public RuleName addRuleName(RuleName ruleName) {
        return null;
    }
}
