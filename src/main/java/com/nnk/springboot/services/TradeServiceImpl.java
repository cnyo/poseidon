package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the TradeService interface.
 * Provides methods to manage trades, including retrieval, saving, updating, and deleting.
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> getAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * Retrieves a Trade by its ID.
     *
     * @param id the ID of the Trade to retrieve
     * @return the Trade with the specified ID, or null if not found
     * @throws IllegalArgumentException if the ID is null
     */
    @Override
    public Trade getTrade(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Trade ID cannot be null");
        }

        return tradeRepository.findById(id).orElse(null);
    }

    /**
     * Saves a Trade to the repository.
     *
     * @param trade the Trade to save
     * @return the saved Trade
     * @throws IllegalArgumentException if the Trade is null
     */
    @Override
    public Trade saveTrade(Trade trade) {
        if (trade == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        return tradeRepository.save(trade);
    }

    /**
     * Updates an existing Trade.
     *
     * @param id the ID of the Trade to update
     * @param trade the updated Trade data
     * @return the updated Trade, or null if the Trade does not exist
     * @throws IllegalArgumentException if id or trade is null
     */
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

    /**
     * Deletes a Trade by its ID.
     *
     * @param id the ID of the Trade to delete
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }

    /**
     * Adds a new Trade to the repository.
     *
     * @param trade the Trade to add
     * @return the added Trade
     * @throws IllegalArgumentException if the Trade is null or invalid
     */
    @Override
    public Trade addTrade(Trade trade) throws IllegalArgumentException {
        if (trade == null) {
            throw new IllegalArgumentException("Trade cannot be null");
        }

        return tradeRepository.save(trade);
    }
}
