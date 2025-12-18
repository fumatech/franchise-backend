package com.franchise.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Repository.PurchaseDIOrderRepo;
import com.franchise.Repository.PurchasePoOrderRepo;

@Service
public class CombinedPurchaseOrderService {

	@Autowired
	private PurchasePoOrderRepo purchasePoOrderRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIOrderRepo;

	public Map<String, List<Object>> getAllPurchaseOrdersWithTax() {

		Map<String, List<Object>> result = new HashMap<>();

		List<Object> allOrders = new ArrayList<>();

		// PO orders with tax
		allOrders.addAll(purchasePoOrderRepo.findAllWithPurchaseTax());

		// DI orders with tax
		allOrders.addAll(purchaseDIOrderRepo.findAllWithPurchaseTax());

		result.put("orders", allOrders);

		return result;
	}
}
