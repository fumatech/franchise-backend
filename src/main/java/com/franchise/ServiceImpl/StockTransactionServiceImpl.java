package com.franchise.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.StockTransaction;
import com.franchise.Repository.StockTransactionRepo;
import com.franchise.Service.StockTransactionService;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

    @Autowired
    private StockTransactionRepo stockTransactionRepo;

    // New method to save a list of stock transactions
    public List<StockTransaction> saveStockTransactions(List<StockTransaction> stockTransactions) {
        return stockTransactionRepo.saveAll(stockTransactions); // Save the list of transactions
    }


    @Override
    public List<StockTransaction> getTransactionsByProduct(Long productId) {
        return stockTransactionRepo.findByProductId(productId);
    }

    @Override
    public List<StockTransaction> getTransactionsByVariation(Long variationId) {
        return stockTransactionRepo.findByVariationId(variationId);
    }

    @Override
    public List<StockTransaction> getAllTransactions() {
        return stockTransactionRepo.findAll();
    }
    
    @Override
    public List<StockTransaction> getTransactionsByProductAndVariation(Long productId, Long variationId) {
        return stockTransactionRepo.findByProductIdAndVariationId(productId, variationId);
    }
    
    @Override
    public int getCurrentStock(Long productId, Long variationId) {
        List<StockTransaction> transactions = stockTransactionRepo.findByProductIdAndVariationId(productId, variationId);
        int currentStock = 0;
        for (StockTransaction transaction : transactions) {
            // Add or subtract quantities based on the transaction type
            switch (transaction.getTransactionType()) {
                case "di_purchase":
                    currentStock += transaction.getQuantity();
                    break;
           
                case "po_purchase":
                    currentStock += transaction.getQuantity();
                    break;   
                case "open_stock":
                    currentStock += transaction.getQuantity();  // Handle open stock
                    break;
                case "sale":
                    currentStock -= transaction.getQuantity();
                    break;
                case "purchase_return":
                    currentStock -= transaction.getQuantity();
                    break;
                case "sale_return":
                    currentStock += transaction.getQuantity();
                    break;
                case "adjustment":
                    currentStock -= transaction.getQuantity();
                    break;
                case "warranty_claimed":
                    currentStock += transaction.getQuantity();
                    break;
                case "warranty_replaced":
                    currentStock -= transaction.getQuantity();
                    break;
                case "warranty_return":
                    currentStock -= transaction.getQuantity();
                    break;
            }
        }
        return currentStock;
    }


	@Override
	public int getCurrentStockByProduct(Long productId) {
		List<StockTransaction> transactions = stockTransactionRepo.findByProductId(productId);
        int currentStock = 0;
        for (StockTransaction transaction : transactions) {
            // Add or subtract quantities based on the transaction type
            switch (transaction.getTransactionType()) {
                case "po_purchase":
                    currentStock += transaction.getQuantity();
                    break;
                case "di_purchase":
                    currentStock += transaction.getQuantity();
                    break;
                case "open_stock":
                    currentStock += transaction.getQuantity();  // Handle open stock
                    break;
                case "sale":
                    currentStock -= transaction.getQuantity();
                    break;
                case "purchase_return":
                    currentStock -= transaction.getQuantity();
                    break;
                case "sale_return":
                    currentStock += transaction.getQuantity();
                    break;
                case "adjustment":
                    currentStock -= transaction.getQuantity();
                    break;
            
                case "warranty_claimed":
                    currentStock += transaction.getQuantity();
                    break;
                case "warranty_replaced":
                    currentStock -= transaction.getQuantity();
                    break;
                case "warranty_return":
                    currentStock -= transaction.getQuantity();
                    break;
            }}
        return currentStock;
	}


	@Override
	public int getCurrentStockByVariations(Long variationId) {
		List<StockTransaction> transactions = stockTransactionRepo.findByVariationId(variationId);
        int currentStock = 0;
        for (StockTransaction transaction : transactions) {
            // Add or subtract quantities based on the transaction type
            switch (transaction.getTransactionType()) {
                case "purchase":
                    currentStock += transaction.getQuantity();
                    break;
                case "open_stock":
                    currentStock += transaction.getQuantity();  // Handle open stock
                    break;
                case "sale":
                    currentStock -= transaction.getQuantity();
                    break;
                case "purchase_return":
                    currentStock -= transaction.getQuantity();
                    break;
                case "sale_return":
                    currentStock += transaction.getQuantity();
                    break;
                case "adjustment":
                    currentStock -= transaction.getQuantity();
                    break;
            
                case "warranty_claimed":
                    currentStock += transaction.getQuantity();
                    break;
                case "warranty_replaced":
                    currentStock -= transaction.getQuantity();
                    break;
                case "warranty_return":
                    currentStock -= transaction.getQuantity();
                    break;
            }}
        return currentStock;
	}
}

