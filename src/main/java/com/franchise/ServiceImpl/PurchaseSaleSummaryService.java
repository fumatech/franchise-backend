package com.franchise.ServiceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.PurchaseSaleSummaryDTO;
import com.franchise.Repository.PurchaseDIOrderRepo;
import com.franchise.Repository.PurchasePoOrderRepo;
import com.franchise.Repository.SaleRepo;
import com.franchise.Repository.SaleReturnRepo;
import com.franchise.Repository.TransactionRepo;

@Service
public class PurchaseSaleSummaryService {

	@Autowired
	private PurchasePoOrderRepo purchasePoRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIRepo;

	@Autowired
	private SaleRepo saleRepo;

	@Autowired
	private SaleReturnRepo saleReturnRepo;

	@Autowired
	private TransactionRepo transactionRepo;

	public PurchaseSaleSummaryDTO getSummary() {

		PurchaseSaleSummaryDTO dto = new PurchaseSaleSummaryDTO();

		// ================= PURCHASE =================

		BigDecimal purchasePo = purchasePoRepo.totalPurchasePo();
		BigDecimal purchasePoTax = purchasePoRepo.totalPurchasePoWithTax();

		BigDecimal purchaseDi = purchaseDIRepo.totalPurchaseDI();
		BigDecimal purchaseDiTax = purchaseDIRepo.totalPurchaseDIWithTax();

		BigDecimal totalPurchase = purchasePo.add(purchaseDi);
		BigDecimal totalPurchaseWithTax = purchasePoTax.add(purchaseDiTax);

		// ❌ NO PURCHASE RETURN
		BigDecimal purchasePaid = transactionRepo.totalPurchasePaid();

		BigDecimal purchaseDue = totalPurchaseWithTax.subtract(purchasePaid);

		// ================= SALE =================

		BigDecimal totalSale = saleRepo.totalSale();
		BigDecimal totalSaleWithTax = saleRepo.totalSaleWithTax();

		// ✅ Sale Return exists
		BigDecimal saleReturn = saleReturnRepo.totalSaleReturnWithTax();

		BigDecimal saleReceived = transactionRepo.totalSaleReceived();

		BigDecimal saleDue = totalSaleWithTax.subtract(saleReturn).subtract(saleReceived);

		// ================= DTO SET =================

		dto.setTotalPurchase(totalPurchase);
		dto.setPurchaseIncludingTax(totalPurchaseWithTax);
		dto.setTotalPurchaseReturnIncludingTax(BigDecimal.ZERO); // No purchase return
		dto.setPurchaseDue(purchaseDue);

		dto.setTotalSale(totalSale);
		dto.setSaleIncludingTax(totalSaleWithTax);
		dto.setTotalSaleReturnIncludingTax(saleReturn);
		dto.setSaleDue(saleDue);

		// ================= OVERALL =================

		dto.setSaleMinusPurchase(totalSale.subtract(totalPurchase));
		dto.setDueAmount(saleDue.subtract(purchaseDue));

		return dto;
	}
}
