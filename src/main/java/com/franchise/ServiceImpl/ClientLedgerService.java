package com.franchise.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.ClientLedgerDTO;
import com.franchise.Repository.PurchaseDIOrderRepo;
import com.franchise.Repository.PurchasePoOrderRepo;
import com.franchise.Repository.SaleRepo;
import com.franchise.Repository.SaleReturnRepo;

@Service
public class ClientLedgerService {

	@Autowired
	private PurchasePoOrderRepo purchasePoRepo;

	@Autowired
	private PurchaseDIOrderRepo purchaseDIRepo;

	@Autowired
	private SaleRepo saleRepo;

	@Autowired
	private SaleReturnRepo saleReturnRepo; // ✅ USED NOW

	public List<ClientLedgerDTO> getClientLedger() {

		Map<String, ClientLedgerDTO> ledgerMap = new HashMap<>();

		// ================= PURCHASE PO =================
		purchasePoRepo.findAll().forEach(p -> {
			if (p.getVendor() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, p.getVendor());
			ledger.setTotalPurchase(ledger.getTotalPurchase().add(getOrZero(p.getNetTotalAmount())));
		});

		// ================= PURCHASE DI =================
		purchaseDIRepo.findAll().forEach(p -> {
			if (p.getVendor() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, p.getVendor());
			ledger.setTotalPurchase(ledger.getTotalPurchase().add(getOrZero(p.getNetTotalAmount())));
		});

		// ================= SALES =================
		saleRepo.findAll().forEach(s -> {
			if (s.getCustomer() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, s.getCustomer());
			ledger.setTotalSale(ledger.getTotalSale().add(getOrZero(s.getNetTotalAmount())));
		});

		// ================= SALE RETURN =================
		saleReturnRepo.findAll().forEach(sr -> {
			if (sr.getCustomer() == null)
				return;

			ClientLedgerDTO ledger = getLedger(ledgerMap, sr.getCustomer());
			ledger.setTotalSaleReturn(ledger.getTotalSaleReturn().add(getOrZero(sr.getNetTotalAmount())));
		});

		// ================= FINAL DUE CALC =================
		ledgerMap.values().forEach(l -> {

			BigDecimal netPurchase = l.getTotalPurchase(); // ❌ no purchase return

			BigDecimal netSale = l.getTotalSale().subtract(l.getTotalSaleReturn()); // ✅ sale return applied

			BigDecimal due = netPurchase.add(netSale).subtract(l.getPaidAmount());

			l.setDue(due);
		});

		return new ArrayList<>(ledgerMap.values());
	}

	// ================= HELPERS =================

	private ClientLedgerDTO getLedger(Map<String, ClientLedgerDTO> map, String contact) {
		return map.computeIfAbsent(contact, this::createLedger);
	}

	private ClientLedgerDTO createLedger(String contact) {
		ClientLedgerDTO dto = new ClientLedgerDTO();
		dto.setContact(contact);
		dto.setTotalPurchase(BigDecimal.ZERO);
		dto.setTotalPurchaseReturn(BigDecimal.ZERO); // ❌ not used
		dto.setTotalSale(BigDecimal.ZERO);
		dto.setTotalSaleReturn(BigDecimal.ZERO); // ✅ used
		dto.setPaidAmount(BigDecimal.ZERO);
		dto.setDue(BigDecimal.ZERO);
		return dto;
	}

	private BigDecimal getOrZero(BigDecimal val) {
		return val == null ? BigDecimal.ZERO : val;
	}
}
