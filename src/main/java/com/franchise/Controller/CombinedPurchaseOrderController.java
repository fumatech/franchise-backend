package com.franchise.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.ServiceImpl.CombinedPurchaseOrderService;

@RestController
@RequestMapping("/purchase-combined-orders")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class CombinedPurchaseOrderController {

	@Autowired
	private CombinedPurchaseOrderService combinedPurchaseOrderService;

	@GetMapping("/with-tax")
	public ResponseEntity<Map<String, List<Object>>> getAllOrdersWithTax() {
		return ResponseEntity.ok(combinedPurchaseOrderService.getAllPurchaseOrdersWithTax());
	}
}
