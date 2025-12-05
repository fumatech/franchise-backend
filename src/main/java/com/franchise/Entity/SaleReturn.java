package com.franchise.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity

public class SaleReturn {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderId;
	
	private String orderRefernceNumber;
	
	private String referenceNumber;
	
	private String orderedBy;
	
	private String addedBy;
	
	private Date orderDate;
	
	private Date saleDate;
	
    private Long payTermNumber;
    
    private String payTermType;

    private String location;

    
    private Long totalItems;
    
    private Long totalSaleItems;

    private Long saleStatus;

    
    private BigDecimal netTotalAmount;

    private String discountType;
    
    private BigDecimal discountAmount;
    
    private String saleTax;

    private BigDecimal taxAmount;
  
    private String additionalNotes;
	
	
    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleReturnItem> saleReturnItem;
    
    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transaction = new ArrayList<>();

    // List of product items (one-to-many relationship)
    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingSaleReturnDetails> shippingSaleReturnDetails;
    
    @OneToMany(mappedBy = "saleReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockTransaction> stockTransaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderRefernceNumber() {
		return orderRefernceNumber;
	}

	public void setOrderRefernceNumber(String orderRefernceNumber) {
		this.orderRefernceNumber = orderRefernceNumber;
	}
	
	
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Long getPayTermNumber() {
		return payTermNumber;
	}

	public void setPayTermNumber(Long payTermNumber) {
		this.payTermNumber = payTermNumber;
	}

	public String getPayTermType() {
		return payTermType;
	}

	public void setPayTermType(String payTermType) {
		this.payTermType = payTermType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalSaleItems() {
		return totalSaleItems;
	}

	public void setTotalSaleItems(Long totalSaleItems) {
		this.totalSaleItems = totalSaleItems;
	}

	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

 

	public String getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(String saleTax) {
		this.saleTax = saleTax;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public List<SaleReturnItem> getSaleReturnItem() {
		return saleReturnItem;
	}

	public void setSaleReturnItem(List<SaleReturnItem> saleReturnItem) {
		this.saleReturnItem = saleReturnItem;
	}

 

	public List<ShippingSaleReturnDetails> getShippingSaleReturnDetails() {
		return shippingSaleReturnDetails;
	}

	public void setShippingSaleReturnDetails(List<ShippingSaleReturnDetails> shippingSaleReturnDetails) {
		this.shippingSaleReturnDetails = shippingSaleReturnDetails;
	}

	public Long getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Long saleStatus) {
		this.saleStatus = saleStatus;
	}

	public List<StockTransaction> getStockTransaction() {
		return stockTransaction;
	}

	public void setStockTransaction(List<StockTransaction> stockTransaction) {
		this.stockTransaction = stockTransaction;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

 


    
}
