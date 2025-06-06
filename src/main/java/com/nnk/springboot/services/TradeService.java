package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

/**
 * Service interface for managing trades.
 * Provides methods to retrieve, update, add, and delete trades.
 */
public interface TradeService {

    /**
     * Retrieves all trades from the repository.
     *
     * @return a list of all trades
     */
    List<Trade> getAllTrade();

    /**
     * Retrieves a trade by its ID.
     *
     * @param id the ID of the trade to retrieve
     * @return the trade with the specified ID, or null if not found
     */
    Trade getTrade(Integer id);

    /**
     * Adds a new trade to the repository.
     *
     * @param trade the trade to add
     * @return the added trade
     * @throws IllegalArgumentException if the trade is null or invalid
     */
    Trade saveTrade(Trade trade);

    /**
     * Updates an existing trade.
     *
     * @param id the ID of the trade to update
     * @param trade the updated trade data
     * @return the updated trade
     * @throws IllegalArgumentException if id or trade is null, or if the trade does not exist
     */
    Trade updateTrade(Integer id, Trade trade);

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete
     * @throws IllegalArgumentException if id is null
     */
    void deleteTrade(Integer id);

    /**
     * Adds a new trade.
     *
     * @param trade the trade to add
     * @return the added trade
     * @throws IllegalArgumentException if the trade is null or invalid
     */
    Trade addTrade(Trade trade) throws IllegalArgumentException;
}
