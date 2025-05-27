package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> getAllTrade() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTrade(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Trade ID cannot be null");
        }

        return tradeRepository.findById(id).orElse(null);
    }

    @Override
    public Trade saveTrade(Trade trade) {
        if (trade == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        return tradeRepository.save(trade);
    }

    @Override
    public Trade updateTrade(Integer id, Trade trade) {
        if (id == null || trade == null) {
            throw new IllegalArgumentException("Trade ID and Trade cannot be null");
        }

        Trade existingTrade = tradeRepository.findById(id).orElse(null);

        if (existingTrade != null) {
            existingTrade.setAccount(trade.getAccount());
            existingTrade.setType(trade.getType());
            existingTrade.setBuyQuantity(trade.getBuyQuantity());
            existingTrade.setSellQuantity(trade.getSellQuantity());
            existingTrade.setBenchmark(trade.getBenchmark());
            existingTrade.setSecurity(trade.getSecurity());
            existingTrade.setStatus(trade.getStatus());
            existingTrade.setTrader(trade.getTrader());
            existingTrade.setBook(trade.getBook());
            existingTrade.setCreationDate(trade.getCreationDate());
            existingTrade.setRevisionDate(trade.getRevisionDate());
            existingTrade.setDealName(trade.getDealName());
            existingTrade.setDealType(trade.getDealType());
            existingTrade.setSourceListId(trade.getSourceListId());
            existingTrade.setSide(trade.getSide());

            return tradeRepository.save(existingTrade);
        }

        return null;
    }

    @Override
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }

    @Override
    public Trade addTrade(Trade trade) throws IllegalArgumentException {
        if (trade == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        return tradeRepository.save(trade);
    }
}
