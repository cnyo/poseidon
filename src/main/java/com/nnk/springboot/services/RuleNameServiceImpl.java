package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing RuleNames.
 * Provides methods to retrieve, update, add, and delete RuleNames.
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Retrieves all RuleNames from the repository.
     *
     * @return a list of all RuleNames
     */
    @Override
    public List<RuleName> getAllRuleName() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a RuleName by its ID.
     *
     * @param id the ID of the RuleName to retrieve
     * @return the RuleName with the specified ID, or null if not found
     * @throws IllegalArgumentException if id is null
     */
    @Override
    @Transactional
    public RuleName getRuleName(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("RuleName ID cannot be null");
        }

        return ruleNameRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param ruleName the updated RuleName object
     * @return the updated RuleName
     * @throws IllegalArgumentException if id or ruleName is null, or if the RuleName does not exist
     */
    @Override
    @Transactional
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        if (id == null || ruleName == null) {
            throw new IllegalArgumentException("RuleName ID and RuleName cannot be null");
        }

        RuleName existingRuleName = ruleNameRepository.findById(id).orElse(null);

        if (existingRuleName == null) {

            throw new IllegalArgumentException("RuleName with ID " + id + " does not exist");
        }

        existingRuleName.setName(ruleName.getName());
        existingRuleName.setDescription(ruleName.getDescription());
        existingRuleName.setJson(ruleName.getJson());
        existingRuleName.setTemplate(ruleName.getTemplate());
        existingRuleName.setSql(ruleName.getSql());
        existingRuleName.setSqlPart(ruleName.getSqlPart());

        return ruleNameRepository.save(existingRuleName);
    }

    /**
     * Adds a new RuleName to the repository.
     *
     * @param ruleName the RuleName to add
     * @return the added RuleName
     * @throws IllegalArgumentException if ruleName is null
     */
    @Override
    @Transactional
    public RuleName addRuleName(RuleName ruleName) {
        if (ruleName == null) {
            throw new IllegalArgumentException("RuleName cannot be null");
        }

        return ruleNameRepository.save(ruleName);
    }

    /**
     * Deletes a RuleName by its ID.
     *
     * @param id the ID of the RuleName to delete
     */
    @Override
    @Transactional
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
