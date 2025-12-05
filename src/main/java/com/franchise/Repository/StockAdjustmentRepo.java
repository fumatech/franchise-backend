package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Entity.StockAdjustment;

public interface StockAdjustmentRepo extends JpaRepository<StockAdjustment, Long> {

}
