package com.franchise.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.franchise.Entity.Sale;

public interface SaleService {

	// Create a new Sale
	Sale createSale(Sale sale);

	// Retrieve all Sales
	List<Sale> getAllSales();

	// Retrieve a Sale by ID
	Optional<Sale> getSaleById(Long id);

	// Update an existing Sale
	Sale updateSale(Long id, Sale sale);

	// Delete a Sale by ID
	void deleteSale(Long id);

	public List<String> getAllOrderIds();

	Optional<Sale> getSaleOrderById(String saleOrderId);

	Map<String, List<Object>> getAllSaleOrdersWithTax();

}
