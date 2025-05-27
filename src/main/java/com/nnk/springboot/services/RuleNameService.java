package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

/** * Service interface for managing RuleNames.
 * Provides methods to retrieve, update, add, and delete RuleNames.
 */
@Service
public interface RuleNameService {

    /**
     * Retrieves all RuleNames from the repository.
     *
     * @return a list of all RuleNames
     */
    List<RuleName> getAllRuleName();

    /**
     * Retrieves a RuleName by its ID.
     *
     * @param id the ID of the RuleName to retrieve
     * @return the RuleName with the specified ID, or null if not found
     * @throws IllegalArgumentException if id is null
     */
    RuleName getRuleName(Integer id);

    /**
     * Adds a new RuleName to the repository.
     *
     * @param ruleName the RuleName to add
     * @return the added RuleName
     * @throws IllegalArgumentException if ruleName is null
     */
    RuleName addRuleName(RuleName ruleName);

    /**
     * Updates an existing RuleName.
     *
     * @param id the ID of the RuleName to update
     * @param ruleName the updated RuleName object
     * @return the updated RuleName
     * @throws IllegalArgumentException if id or ruleName is null, or if the RuleName does not exist
     */
    RuleName updateRuleName(Integer id, RuleName ruleName);

    /**
     * Deletes a RuleName by its ID.
     *
     * @param id the ID of the RuleName to delete
     * @throws IllegalArgumentException if id is null
     */
    void deleteRuleName(Integer id);
}
