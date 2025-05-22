package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RuleNameService {
    List<RuleName> getAllRuleName();

    RuleName getRuleName(Integer id);

    RuleName addRuleName(RuleName ruleName);

    RuleName updateRuleName(Integer id, RuleName ruleName);

    Boolean deleteRuleName(Integer id);
}
