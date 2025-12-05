package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.PurchasePoItem;
import com.franchise.Entity.PurchasePoOrder;
import com.franchise.Entity.ShippingPoDetails;
import com.franchise.Entity.StockTransaction;
import com.franchise.Repository.PurchasePoOrderRepo;

import jakarta.transaction.Transactional;

@Service
public class PurchasePoOrderServiceImpl implements com.franchise.Service.PurchasePoOrderService {

	@Autowired
	private PurchasePoOrderRepo purchaseOrderRepo;

	@Transactional
	@Override
	public PurchasePoOrder savePurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		// Set relationships for child entities
		if (purchasePoOrder.getPurchasePoItem() != null) {
			for (PurchasePoItem item : purchasePoOrder.getPurchasePoItem()) {
				item.setPurchasePoOrder(purchasePoOrder);
			}
		}

		if (purchasePoOrder.getShippingPoDetails() != null) {
			for (ShippingPoDetails ship : purchasePoOrder.getShippingPoDetails()) {
				ship.setPurchasePoOrder(purchasePoOrder);
			}
		}

		if (purchasePoOrder.getStockTransactions() != null) {
			for (StockTransaction stock : purchasePoOrder.getStockTransactions()) {
				stock.setPurchasePoOrder(purchasePoOrder);
			}
		}

		// ✅ **Save Purchase Order along with all related entities**
		return purchaseOrderRepo.save(purchasePoOrder);
	}

	@Override
	public List<PurchasePoOrder> getAllPurchasePoOrders() {
		return purchaseOrderRepo.findAll();
	}

	@Override
	public Optional<PurchasePoOrder> getPurchasePoOrderById(Long id) {
		return purchaseOrderRepo.findById(id);
	}

	@Override
	public PurchasePoOrder updatePurchasePoOrder(Long id, PurchasePoOrder updatedPurchasePoOrder) {
		Optional<PurchasePoOrder> existingOrderOptional = purchaseOrderRepo.findById(id);
		if (existingOrderOptional.isPresent()) {
			PurchasePoOrder existingOrder = existingOrderOptional.get();

			// Update basic fields
			existingOrder.setStatus(updatedPurchasePoOrder.getStatus());
			existingOrder.setVendor(updatedPurchasePoOrder.getVendor());
			existingOrder.setAddedBy(updatedPurchasePoOrder.getAddedBy());
			existingOrder.setOrderedBy(updatedPurchasePoOrder.getOrderedBy());
			existingOrder.setReferenceNumber(updatedPurchasePoOrder.getReferenceNumber());
			existingOrder.setPurchaseReferenceNumber(updatedPurchasePoOrder.getPurchaseReferenceNumber());
			existingOrder.setOrderDate(updatedPurchasePoOrder.getOrderDate());
			existingOrder.setPurchaseDate(updatedPurchasePoOrder.getPurchaseDate());
			existingOrder.setPayTermNumber(updatedPurchasePoOrder.getPayTermNumber());
			existingOrder.setPayTermType(updatedPurchasePoOrder.getPayTermType());
			existingOrder.setLocation(updatedPurchasePoOrder.getLocation());
			existingOrder.setFile(updatedPurchasePoOrder.getFile());
			existingOrder.setTotalItems(updatedPurchasePoOrder.getTotalItems());
			existingOrder.setNetTotalAmount(updatedPurchasePoOrder.getNetTotalAmount());
			existingOrder.setDiscountType(updatedPurchasePoOrder.getDiscountType());
			existingOrder.setDiscountAmount(updatedPurchasePoOrder.getDiscountAmount());
			existingOrder.setPurchaseTax(updatedPurchasePoOrder.getPurchaseTax());
			existingOrder.setTaxAmount(updatedPurchasePoOrder.getTaxAmount());
			existingOrder.setAdditionalNotes(updatedPurchasePoOrder.getAdditionalNotes());

			// Handle PurchasePoItems (handle orphan removal and updates)
			if (updatedPurchasePoOrder.getPurchasePoItem() != null) {
				existingOrder.getPurchasePoItem().clear(); // Clear existing items
				for (PurchasePoItem item : updatedPurchasePoOrder.getPurchasePoItem()) {
					item.setPurchasePoOrder(existingOrder); // Set parent relation
					existingOrder.getPurchasePoItem().add(item); // Add to the list
				}
			}

			// Handle ShippingPoDetails (handle orphan removal and updates)
			if (updatedPurchasePoOrder.getShippingPoDetails() != null) {
				existingOrder.getShippingPoDetails().clear(); // Clear existing items
				for (ShippingPoDetails shippingDetails : updatedPurchasePoOrder.getShippingPoDetails()) {
					shippingDetails.setPurchasePoOrder(existingOrder); // Set parent relation
					existingOrder.getShippingPoDetails().add(shippingDetails); // Add to the list
				}
			}

			// Handle StockTransactions (handle orphan removal and updates)
			if (updatedPurchasePoOrder.getStockTransactions() != null) {
				existingOrder.getStockTransactions().clear(); // Clear existing items
				for (StockTransaction transactions : updatedPurchasePoOrder.getStockTransactions()) {
					transactions.setPurchasePoOrder(existingOrder); // Set parent relation
					existingOrder.getStockTransactions().add(transactions); // Add to the list
				}
			}

			// Save the updated order
			return purchaseOrderRepo.save(existingOrder);
		} else {
			return null; // Order not found, handle as needed (e.g., throw exception or return an error)
		}
	}

	@Override
	public void deletePurchasePoOrder(Long id) {
		if (purchaseOrderRepo.existsById(id)) {
			purchaseOrderRepo.deleteById(id);
		}
	}

	@Override
	public List<String> getAllOrderIds() {
		// This method currently returns null, you can implement fetching order ids here
		// if needed
		return null;
	}
}
