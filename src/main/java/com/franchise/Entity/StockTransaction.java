package com.franchise.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StockTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private Long variationId;

	private BigDecimal price;

	private int quantity;
	private String transactionType;

	private Date date;
	private String note;

	// Many-to-one relationship with PurchasePoOrder
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_po_order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchasePoOrder purchasePoOrder;

	// Many-to-one relationship with PurchasePoOrder
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_di_order_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseDIOrder purchaseDIOrder;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_return_id")
	@JsonBackReference
	@JsonIgnore
	private PurchaseReturn purchaseReturn;

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

	@ManyToOne
	@JoinColumn(name = "stock_Adjustment_id")
	@JsonBackReference
	@JsonIgnore
	private StockAdjustment stockAdjustment;

	@ManyToOne
	@JoinColumn(name = "warranty_claim_id")
	@JsonBackReference
	@JsonIgnore
	private WarrantyClaim warrantyClaim;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getVariationId() {
		return variationId;
	}

	public void setVariationId(Long variationId) {
		this.variationId = variationId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PurchasePoOrder getPurchasePoOrder() {
		return purchasePoOrder;
	}

	public void setPurchasePoOrder(PurchasePoOrder purchasePoOrder) {
		this.purchasePoOrder = purchasePoOrder;
	}

	public PurchaseReturn getPurchaseReturn() {
		return purchaseReturn;
	}

	public void setPurchaseReturn(PurchaseReturn purchaseReturn) {
		this.purchaseReturn = purchaseReturn;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public SaleReturn getSaleReturn() {
		return saleReturn;
	}

	public void setSaleReturn(SaleReturn saleReturn) {
		this.saleReturn = saleReturn;
	}

	public StockAdjustment getStockAdjustment() {
		return stockAdjustment;
	}

	public void setStockAdjustment(StockAdjustment stockAdjustment) {
		this.stockAdjustment = stockAdjustment;
	}

	public WarrantyClaim getWarrantyClaim() {
		return warrantyClaim;
	}

	public void setWarrantyClaim(WarrantyClaim warrantyClaim) {
		this.warrantyClaim = warrantyClaim;
	}

	public PurchaseDIOrder getPurchaseDIOrder() {
		return purchaseDIOrder;
	}

	public void setPurchaseDIOrder(PurchaseDIOrder purchaseDIOrder) {
		this.purchaseDIOrder = purchaseDIOrder;
	}

}
