package com.franchise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.PurchaseSaleSummaryDTO;
import com.franchise.ServiceImpl.PurchaseSaleSummaryService;

@RestController
@RequestMapping("/summary")
@CrossOrigin(origins = { "http://localhost:3000", "http://fusionmastertech.com", "https://fusionmastertech.com",
		"http://www.fusionmastertech.com", "https://www.fusionmastertech.com" }, allowCredentials = "true")
public class PurchaseSaleSummaryController {

	@Autowired
	private PurchaseSaleSummaryService purchaseSaleSummaryService;

	@GetMapping("/purchase-sale")
	public ResponseEntity<PurchaseSaleSummaryDTO> getPurchaseSaleSummary() {

		PurchaseSaleSummaryDTO summary = purchaseSaleSummaryService.getSummary();

		return ResponseEntity.ok(summary);
	}
}