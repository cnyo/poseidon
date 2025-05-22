package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
    @Override
    public List<BidList> getAllBidList() {
        return List.of();
    }

    @Override
    public BidList getBidList(Integer id) {
        return null;
    }

    @Override
    public BidList saveBid(BidList bidList) {
        return null;
    }

    @Override
    public BidList updateBid(Integer id, BidListForm bidListForm) {
        return null;
    }

    @Override
    public Boolean deleteBidList(Integer id) {
        return null;
    }

    @Override
    public void deleteBid(Integer bidListId) {

    }

    @Override
    public BidList bidFormToBid(BidListForm bidForm) {
        return null;
    }

    @Override
    public BidListForm initBidFormFromBid(BidList bid) {
        return null;
    }
}
