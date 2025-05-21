package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidListService {
    List<BidList> getAllBidList();

    BidList getBidList(Integer id);

    BidList saveBidList(BidList bidList);

    BidList updateBidList(Integer id, BidList bidList);

    Boolean deleteBidList(Integer id);
}
