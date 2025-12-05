package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Entity.SaleReturn;

 
public interface SaleReturnRepo extends JpaRepository<SaleReturn, Long> {
	 
	SaleReturn findByOrderId(String orderId);
}
 