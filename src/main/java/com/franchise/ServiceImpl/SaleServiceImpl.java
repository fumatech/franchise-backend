package com.franchise.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Sale;
import com.franchise.Entity.SaleItems;
import com.franchise.Entity.StockTransaction;
import com.franchise.Repository.SaleRepo;
import com.franchise.Service.IdGenerator;
import com.franchise.Service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepo saleRepo;

	@Autowired
	private IdGenerator idGenerator;

	// Create a new sale
	@Override
	public Sale createSale(Sale sale) {
		// Ensure that SaleItems are correctly associated with the Sale
		if (sale.getSaleItems() != null) {
			sale.setIdGenerator(idGenerator); // Pass the IdGenerator to the entity
			for (SaleItems items : sale.getSaleItems()) {
				items.setSale(sale);
			}
		}

		// Ensure that StockTransactions are associated with the Sale
		if (sale.getStockTransaction() != null) {
			for (StockTransaction stock : sale.getStockTransaction()) {
				stock.setSale(sale);
			}
		}

		return saleRepo.save(sale);
	}

	// Retrieve all sales
	@Override
	public List<Sale> getAllSales() {
		return saleRepo.findAll();
	}

	@Override
	public Optional<Sale> getSaleById(Long id) {
		return saleRepo.findById(id);
	}

	@Override
	public Sale updateSale(Long id, Sale saleDetails) {
		Optional<Sale> existingSale = saleRepo.findById(id);
		if (existingSale.isPresent()) {
			Sale sale = existingSale.get();
			// Update the fields of the sale
			sale.setCustomer(saleDetails.getCustomer());
			sale.setPayTermNumber(saleDetails.getPayTermNumber());
			sale.setPayTermType(saleDetails.getPayTermType());
			sale.setSaleDate(saleDetails.getSaleDate());
			sale.setInvoiceNo(saleDetails.getInvoiceNo());
			sale.setDiscountType(saleDetails.getDiscountType());
			sale.setDiscountAmount(saleDetails.getDiscountAmount());
			sale.setSaleTax(saleDetails.getSaleTax());
			sale.setTaxAmount(saleDetails.getTaxAmount());
			sale.setSaleNotes(saleDetails.getSaleNotes());
			sale.setShippingDetails(saleDetails.getShippingDetails());
			sale.setShippingCharges(saleDetails.getShippingCharges());
			sale.setShippingStatus(saleDetails.getShippingStatus());
			sale.setDeliveredTo(saleDetails.getDeliveredTo());
			sale.setDeliveryPerson(saleDetails.getDeliveryPerson());
			sale.setNetTotalAmount(saleDetails.getNetTotalAmount());
			sale.getSaleItems().clear();
			sale.getSaleItems().addAll(saleDetails.getSaleItems());
			for (SaleItems item : sale.getSaleItems()) {
				item.setSale(sale);
			}

			return saleRepo.save(sale);
		} else {
			throw new RuntimeException("Sale not found with id " + id);
		}
	}

	@Override
	public void deleteSale(Long id) {
		if (saleRepo.existsById(id)) {
			saleRepo.deleteById(id);
		} else {
			throw new RuntimeException("Sale not found with id " + id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		return saleRepo.findAll().stream().map(Sale::getSaleOrderId).collect(Collectors.toList());
	}

	@Override
	public Optional<Sale> getSaleOrderById(String saleOrderId) {
		return Optional.ofNullable(saleRepo.findBysaleOrderId(saleOrderId));
	}

	@Override
	public Map<String, List<Object>> getAllSaleOrdersWithTax() {

		Map<String, List<Object>> result = new HashMap<>();

		List<Object> allOrders = new ArrayList<>();
		allOrders.addAll(saleRepo.findAllWithSaleTax());

		result.put("orders", allOrders);
		return result;
	}
}
