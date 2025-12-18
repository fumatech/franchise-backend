package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Sale;

@Repository
public interface SaleRepo extends JpaRepository<Sale, Long> {

	// Corrected method name to match the field name in PurchaseOrder entity
	Sale findBysaleOrderId(String saleOrderId);

	// Query to get the last numeric part of the purchase order ID
	@Query("SELECT MAX(p.id) FROM Sale p")
	Long getLastSaleOrderId();

	@Query("""
			    SELECT p FROM Sale p
			    WHERE p.taxAmount IS NOT NULL
			""")
	List<Sale> findAllWithSaleTax();

}