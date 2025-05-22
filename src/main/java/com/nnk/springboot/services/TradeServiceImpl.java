package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Override
    public List<Trade> getAllTrade() {
        return List.of();
    }

    @Override
    public Trade getTrade(Integer id) {
        return null;
    }

    @Override
    public Trade saveTrade(Trade trade) {
        return null;
    }

    @Override
    public Trade updateTrade(Integer id, Trade trade) {
        return null;
    }

    @Override
    public Boolean deleteTrade(Integer id) {
        return null;
    }
}
