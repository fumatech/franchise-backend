package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Entity.StockTransaction;

public interface StockTransactionRepo extends JpaRepository<StockTransaction, Long> {

    // Custom query to find stock transactions by productId
    List<StockTransaction> findByProductId(Long productId);

    // Custom query to find stock transactions by variationId
    List<StockTransaction> findByVariationId(Long variationId);

    // Custom query to find stock transactions by productId and variationId
    List<StockTransaction> findByProductIdAndVariationId(Long productId, Long variationId);
}
