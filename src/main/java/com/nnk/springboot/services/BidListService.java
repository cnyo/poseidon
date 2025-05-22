package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.form.BidListForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidListService {
    List<BidList> getAllBidList();

    BidList getBidList(Integer id);

    BidList saveBid(BidList bidList);

    BidList updateBid(Integer id, BidListForm bidListForm);

    Boolean deleteBidList(Integer id);

    void deleteBid(Integer bidListId);

    BidList bidFormToBid(BidListForm bidForm);

    BidListForm initBidFormFromBid(BidList bid);
}
