package com.franchise.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.franchise.Entity.PaymentAccount;
import com.franchise.Entity.Sale;
import com.franchise.Entity.Transaction;
import com.franchise.Repository.PaymentAccountRepo;
import com.franchise.Repository.SaleRepo;
import com.franchise.Service.IdGenerator;
import com.franchise.Service.SaleService;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepo saleRepo;

	@Autowired
	private PaymentAccountRepo paymentAccountRepo;

	@Autowired
	private IdGenerator idGenerator;

	/*
	 * ===================================================== CREATE SALE
	 * =====================================================
	 */
	@Override
	public Sale createSale(Sale sale) {

		// Sale Items
		if (sale.getSaleItems() != null) {
			sale.setIdGenerator(idGenerator);
			sale.getSaleItems().forEach(item -> item.setSale(sale));
		}

		// Stock Transactions
		if (sale.getStockTransaction() != null) {
			sale.getStockTransaction().forEach(st -> st.setSale(sale));
		}

		// Financial Transactions
		applyTransactions(sale, sale.getTransaction());

		return saleRepo.save(sale);
	}

	/*
	 * ===================================================== UPDATE SALE (CRITICAL
	 * LOGIC) =====================================================
	 */
	@Override
	public Sale updateSale(Long id, Sale saleDetails) {

		Sale sale = saleRepo.findById(id).orElseThrow(() -> new RuntimeException("Sale not found with id " + id));

		// 🔴 1️⃣ Reverse OLD transactions (VERY IMPORTANT)
		reverseTransactions(sale.getTransaction());

		// 🔴 2️⃣ Clear old child records
		sale.getSaleItems().clear();
		sale.getStockTransaction().clear();
		sale.getTransaction().clear();

		// 🔴 3️⃣ Update sale fields
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

		// 🔴 4️⃣ Reattach Sale Items
		if (saleDetails.getSaleItems() != null) {
			saleDetails.getSaleItems().forEach(item -> {
				item.setSale(sale);
				sale.getSaleItems().add(item);
			});
		}

		// 🔴 5️⃣ Reattach Stock Transactions
		if (saleDetails.getStockTransaction() != null) {
			saleDetails.getStockTransaction().forEach(st -> {
				st.setSale(sale);
				sale.getStockTransaction().add(st);
			});
		}

		// 🔴 6️⃣ Apply NEW transactions
		applyTransactions(sale, saleDetails.getTransaction());
		if (saleDetails.getTransaction() != null) {
			sale.getTransaction().addAll(saleDetails.getTransaction());
		}

		return saleRepo.save(sale);
	}

	/*
	 * ===================================================== HELPER: APPLY
	 * TRANSACTIONS =====================================================
	 */
	private void applyTransactions(Sale sale, List<Transaction> transactions) {
		if (transactions == null)
			return;

		for (Transaction txn : transactions) {

			if (txn.getPaymentAccountId() == null) {
				throw new RuntimeException("Payment Account is required");
			}

			PaymentAccount account = paymentAccountRepo.findById(txn.getPaymentAccountId())
					.orElseThrow(() -> new RuntimeException("PaymentAccount not found: " + txn.getPaymentAccountId()));

			BigDecimal currentBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

			BigDecimal newBalance = currentBalance.add(txn.getDebit()).subtract(txn.getCredit());

			txn.setSale(sale);
			txn.setPaymentAccount(account);
			txn.setBalance(newBalance);
			account.setBalance(newBalance);
		}
	}

	/*
	 * ===================================================== HELPER: REVERSE
	 * TRANSACTIONS =====================================================
	 */
	private void reverseTransactions(List<Transaction> transactions) {
		if (transactions == null)
			return;

		for (Transaction txn : transactions) {
			PaymentAccount account = txn.getPaymentAccount();
			if (account == null)
				continue;

			BigDecimal balance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

			balance = balance.subtract(txn.getDebit()).add(txn.getCredit());

			account.setBalance(balance);
		}
	}

	/*
	 * ===================================================== OTHER METHODS
	 * =====================================================
	 */
	@Override
	public List<Sale> getAllSales() {
		return saleRepo.findAll();
	}

	@Override
	public Optional<Sale> getSaleById(Long id) {
		return saleRepo.findById(id);
	}

	@Override
	public void deleteSale(Long id) {
		if (!saleRepo.existsById(id)) {
			throw new RuntimeException("Sale not found with id " + id);
		}
		saleRepo.deleteById(id);
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
		result.put("orders", new ArrayList<>(saleRepo.findAllWithSaleTax()));
		return result;
	}
}
