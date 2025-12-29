package com.franchise.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String paymentMethod;
	private BigDecimal amount;
	private String transactionType;
	private String addedBy;
	private String note;
	private Date date;
	private BigDecimal balance;
	private String vendor;
	private String chequeNumber;
	private String cardType;
	private String cardNumber;
	private String cardHolderName;
	private String cardTransactionNumber;
	private Long cardMonth;
	private Long cardYear;
	private Long cardSecurity;
	private String cashDetails;

	@ManyToOne
	@JoinColumn(name = "purchase_Po_Order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchasePoOrder purchasePoOrder;

	@ManyToOne
	@JoinColumn(name = "add_Expenses_id")
	@JsonBackReference
	private AddExpenses addExpenses;

	@ManyToOne
	@JoinColumn(name = "purchase_DI_Order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseDIOrder purchaseDIOrder;

	@ManyToOne
	@JoinColumn(name = "sale_id")
	@JsonBackReference
	@JsonIgnore
	private Sale sale;

	@ManyToOne
	@JoinColumn(name = "sale_Return_Order_id")
	@JsonBackReference
	@JsonIgnore
	private SaleReturn saleReturn;

	@Transient
	private Long saleId;

	public Long getSaleId() {
		if (saleId == null && sale != null) {
			return sale.getId();
		}
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	@Transient
	private Long saleReturnId;

	public Long getSaleReturnId() {
		if (saleReturnId == null && saleReturn != null) {
			return saleReturn.getId();
		}
		return saleReturnId;
	}

	public void setSaleReturnId(Long saleReturnId) {
		this.saleReturnId = saleReturnId;
	}

	public SaleReturn getSaleReturn() {
		return saleReturn;
	}

	public void setSaleReturn(SaleReturn saleReturn) {
		this.saleReturn = saleReturn;
	}

	@ManyToOne
	@JoinColumn(name = "payment_account_id", nullable = false)
	@JsonBackReference
	@JsonIgnore
	private PaymentAccount paymentAccount;

	// ✅ **Add this transient field to store `paymentAccountId` from JSON request**
	@Transient
	private Long paymentAccountId;

	public Long getPaymentAccountId() {

		if (paymentAccountId == null && paymentAccount != null) {
			return paymentAccount.getId();
		}

		return paymentAccountId;
	}

	public void setPaymentAccountId(Long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	@Transient
	public BigDecimal getDebit() {
		if (transactionType == null)
			return BigDecimal.ZERO;

		switch (transactionType.toLowerCase()) {
		case "opening_balance":
		case "deposit":
		case "sale_return":
		case "purchase":
		case "po_purchase":
		case "di_purchase":
		case "credit note":
			return amount != null ? amount : BigDecimal.ZERO;
		default:
			return BigDecimal.ZERO;
		}
	}

	@Transient
	public BigDecimal getCredit() {
		if (transactionType == null)
			return BigDecimal.ZERO;

		switch (transactionType.toLowerCase()) {
		case "sale":
		case "payment":
		case "purchase_return":
		case "expense":
			return amount != null ? amount : BigDecimal.ZERO;
		default:
			return BigDecimal.ZERO;
		}
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardTransactionNumber() {
		return cardTransactionNumber;
	}

	public void setCardTransactionNumber(String cardTransactionNumber) {
		this.cardTransactionNumber = cardTransactionNumber;
	}

	public Long getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(Long cardMonth) {
		this.cardMonth = cardMonth;
	}

	public Long getCardYear() {
		return cardYear;
	}

	public void setCardYear(Long cardYear) {
		this.cardYear = cardYear;
	}

	public Long getCardSecurity() {
		return cardSecurity;
	}

	public void setCardSecurity(Long cardSecurity) {
		this.cardSecurity = cardSecurity;
	}

	public String getCashDetails() {
		return cashDetails;
	}

	public void setCashDetails(String cashDetails) {
		this.cashDetails = cashDetails;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public PurchasePoOrder getPurchasePoOrder() {
		return purchasePoOrder;
	}

	public void setPurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		this.purchasePoOrder = purchasePoOrder;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public PurchaseDIOrder getPurchaseDIOrder() {
		return purchaseDIOrder;
	}

	public void setPurchaseDIOrder(PurchaseDIOrder purchaseDIOrder) {
		this.purchaseDIOrder = purchaseDIOrder;
	}

	public AddExpenses getAddExpenses() {
		return addExpenses;
	}

	public void setAddExpenses(AddExpenses addExpenses) {
		this.addExpenses = addExpenses;
	}

}
