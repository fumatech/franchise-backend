package com.franchise.Service;

import java.util.List;

import com.franchise.Entity.StockTransaction;

public interface StockTransactionService {
    // Method to save a new stock transaction
    List<StockTransaction> saveStockTransactions(List<StockTransaction> stockTransactions);

    // Method to retrieve all transactions for a specific product
    List<StockTransaction> getTransactionsByProduct(Long productId);

    // Method to retrieve all transactions for a specific variation
    List<StockTransaction> getTransactionsByVariation(Long variationId);

    // Method to get the history of all transactions (optional)
    List<StockTransaction> getAllTransactions();
    
    int getCurrentStock(Long productId, Long variationId); 
    
    int getCurrentStockByVariations(  Long variationId); 

    List<StockTransaction> getTransactionsByProductAndVariation(Long productId , Long variationId );

    int getCurrentStockByProduct(Long productId); 

}
