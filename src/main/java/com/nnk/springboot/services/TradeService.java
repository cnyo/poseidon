package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {
    List<Trade> getAllTrade();

    Trade getTrade(Integer id);

    Trade saveTrade(Trade trade);

    Trade updateTrade(Integer id, Trade trade);

    void deleteTrade(Integer id);

    Trade addTrade(Trade trade) throws IllegalArgumentException;
}
