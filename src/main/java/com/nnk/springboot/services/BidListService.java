package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;

import java.util.List;

/**
 * Service interface for managing BidLists.
 * Provides methods to retrieve, save, update, and delete BidLists.
 */
public interface BidListService {

    /**
     * Retrieves all BidLists.
     *
     * @return a list of all BidLists
     */
    List<BidList> getAllBidList();

    /**
     * Retrieves a BidList by its ID.
     *
     * @param id the ID of the BidList to retrieve
     * @return the BidList with the specified ID, or null if not found
     * @throws IllegalArgumentException if the ID is null
     */
    BidList getBidList(Integer id) throws IllegalArgumentException;

    /**
     * Updates an existing BidList.
     *
     * @param id the ID of the BidList to update
     * @param bidList the updated BidList data
     * @return the updated BidList
     * @throws IllegalArgumentException if the ID or bidList is null
     */
    BidList updateBid(Integer id, BidList bidList) throws IllegalArgumentException;

    /**
     * Deletes a BidList by its ID.
     *
     * @param bidListId the ID of the BidList to delete
     * @throws IllegalArgumentException if the bidListId is null
     */
    void deleteBid(Integer bidListId) throws IllegalArgumentException;

    /**
     * Converts a BidListForm to a BidList.
     *
     * @param bidForm the BidListForm to convert
     * @return the converted BidList
     * @throws IllegalArgumentException if the bidForm is null or invalid
     */
    BidList bidFormToBid(BidListForm bidForm) throws IllegalArgumentException;

    /**
     * Initializes a BidListForm from an existing BidList.
     *
     * @param bid the BidList to initialize the form from
     * @return the initialized BidListForm
     * @throws IllegalArgumentException if the bid is null
     */
    BidListForm initBidFormFromBid(BidList bid) throws IllegalArgumentException;

    /**
     * Adds a new BidList.
     *
     * @param bidList the BidList to add
     * @return the added BidList
     * @throws IllegalArgumentException if the bidList is null
     */
    BidList addBidList(BidList bidList) throws IllegalArgumentException;
}
