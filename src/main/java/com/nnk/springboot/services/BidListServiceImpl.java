package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the BidListService interface.
 * Provides methods to manage BidLists, including retrieval, saving, updating, and deleting.
 */
@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Retrieves all BidLists from the repository.
     *
     * @return a list of all BidLists
     */
    @Override
    public List<BidList> getAllBidList() {

        return bidListRepository.findAll();
    }

    /**
     * Retrieves a BidList by its ID.
     *
     * @param id the ID of the BidList to retrieve
     * @return the BidList with the specified ID, or null if not found
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public BidList getBidList(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("BidList ID cannot be null");
        }

        return bidListRepository.findById(id).orElse(null);
    }

    /**
     * Saves a BidList to the repository.
     *
     * @param bidList the BidList to save
     * @return the saved BidList
     * @throws IllegalArgumentException if the bidList is null
     */
    @Override
    public BidList updateBid(Integer id, BidList bidList) throws IllegalArgumentException {
        if (id == null || bidList == null) {
            throw new IllegalArgumentException("BidList ID and BidList cannot be null");
        }

        BidList existingBidList = bidListRepository.findById(id).orElse(null);
        if (existingBidList != null) {
            existingBidList.setAccount(bidList.getAccount());
            existingBidList.setType(bidList.getType());
            existingBidList.setBidQuantity(bidList.getBidQuantity());

            return bidListRepository.save(existingBidList);
        }

        return null;
    }

    /**
     * Deletes a BidList by its ID.
     *
     * @param bidListId the ID of the BidList to delete
     * @throws IllegalArgumentException if the bidListId is null
     */
    @Override
    public void deleteBid(Integer bidListId) throws IllegalArgumentException {
        if (bidListId == null) {
            throw new IllegalArgumentException("BidList ID cannot be null");
        }

        bidListRepository.deleteById(bidListId);
    }

    /**
     * Converts a BidListForm to a BidList.
     *
     * @param bidForm the BidListForm to convert
     * @return the converted BidList
     * @throws IllegalArgumentException if the bidForm is null
     */
    @Override
    public BidList bidFormToBid(BidListForm bidForm) throws IllegalArgumentException {
        if (bidForm == null) {
            throw new IllegalArgumentException("BidListForm cannot be null");
        }

        BidList bidList = new BidList();
        bidList.setAccount(bidForm.getAccount());
        bidList.setType(bidForm.getType());
        bidList.setBidQuantity(bidForm.getBidQuantity());

        return bidList;
    }

    /**
     * Initializes a BidListForm from a BidList.
     *
     * @param bid the BidList to initialize the form from
     * @return the initialized BidListForm
     * @throws IllegalArgumentException if the bid is null
     */
    @Override
    public BidListForm initBidFormFromBid(BidList bid) throws IllegalArgumentException {
        if (bid == null) {
            throw new IllegalArgumentException("BidList cannot be null");
        }

        BidListForm bidListForm = new BidListForm();
        bidListForm.setAccount(bid.getAccount());
        bidListForm.setType(bid.getType());
        bidListForm.setBidQuantity(bid.getBidQuantity());

        return bidListForm;
    }

    /**
     * Adds a new BidList to the repository.
     *
     * @param bidList the BidList to add
     * @return the saved BidList
     * @throws IllegalArgumentException if the bidList is null
     */
    @Override
    public BidList addBidList(BidList bidList) throws IllegalArgumentException {
        if (bidList == null) {
            throw new IllegalArgumentException("BidList cannot be null");
        }

        return bidListRepository.save(bidList);
    }
}
