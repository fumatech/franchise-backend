package com.franchise.Repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.franchise.Entity.SaleReturn;

public interface SaleReturnRepo extends JpaRepository<SaleReturn, Long> {

	SaleReturn findByOrderId(String orderId);

	@Query("SELECT COALESCE(SUM(pr.netTotalAmount),0) FROM SaleReturn pr")
	BigDecimal totalSaleReturnWithTax();
}
