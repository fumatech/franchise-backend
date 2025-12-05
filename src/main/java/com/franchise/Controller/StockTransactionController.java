package com.franchise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Service.StockTransactionService;
import com.franchise.Entity.StockTransaction;

@RestController
@RequestMapping("/stock-transactions")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

     @PostMapping("/add")
    public ResponseEntity<List<StockTransaction>> addStockTransactions(@RequestBody List<StockTransaction> stockTransactions) {
        try {
            // Save all stock transactions
            List<StockTransaction> savedTransactions = stockTransactionService.saveStockTransactions(stockTransactions);
            return new ResponseEntity<>(savedTransactions, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
     @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByProduct(@PathVariable Long productId) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByProduct(productId);
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

     
     @GetMapping("/by-variation/{variationId}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByVariation(@PathVariable Long variationId) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByVariation(variationId);
        if (transactions.isEmpty()) { 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    // Endpoint to get stock transactions by product ID and variation ID
    @GetMapping("/by-product-variation/{productId}/{variationId}")
    public ResponseEntity<List<StockTransaction>> getTransactionsByProductAndVariation(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        List<StockTransaction> transactions = stockTransactionService.getTransactionsByProductAndVariation(productId, variationId);
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Endpoint to get current stock for a given productId and variationId
    @GetMapping("/current-stock/{productId}/{variationId}")
    public ResponseEntity<Integer> getCurrentStock(@PathVariable Long productId, @PathVariable Long variationId) {
        int currentStock = stockTransactionService.getCurrentStock(productId, variationId);
        return new ResponseEntity<>(currentStock, HttpStatus.OK);
    }
    
    @GetMapping("/current-stock/{productId}")
    public ResponseEntity<Integer> getCurrentStockByProduct(@PathVariable Long productId) {
        int currentStock = stockTransactionService.getCurrentStockByProduct(productId);
        return new ResponseEntity<>(currentStock, HttpStatus.OK);
    }
    
    @GetMapping("/current-stock-byvariation/{variationId}")
    public ResponseEntity<Integer> getcurrentStockByvariation(@PathVariable Long variationId) {
        int currentStock = stockTransactionService.getCurrentStockByVariations(variationId);
        return new ResponseEntity<>(currentStock, HttpStatus.OK);
    }

    
    
    // Endpoint to get all stock transactions (optional)
    @GetMapping("/getall")
    public ResponseEntity<List<StockTransaction>> getAllTransactions() {
        List<StockTransaction> transactions = stockTransactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
