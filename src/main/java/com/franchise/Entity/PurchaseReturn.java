package com.franchise.Entity;

import java.sql.Date;
import java.util.List; 

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class PurchaseReturn {
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String purchaseReturnId;
	    
	    private Long status;

	    private String vendor;
	   
	    private String addedBy;

	    private String referenceNumber;

	    private Date orderDate;

	    private String location;
	      
	    private String file;

	    private Long totalItems;
	    
	    private Long totalAmount;
	    
 	    private double subtotal;
 	    
	    private double totalTax;
 
	    
	    private Long totalShippedItems;

	    private String additionalNotes;

	    
	    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<PurchaseReturnItems> purchaseReturnItems;
	
	    
	    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<StockTransaction> stockTransaction;
	    
	    
	    @Transient // Mark this as not persisted in the database
	    private com.franchise.Service.IdGenerator idGenerator;
     
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		

		public String getPurchaseReturnId() {
			return purchaseReturnId;
		}

		public void setPurchaseReturnId(String purchaseReturnId) {
			this.purchaseReturnId = purchaseReturnId;
		}

		public Long getStatus() {
			return status;
		}

		public void setStatus(Long status) {
			this.status = status;
		}

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getAddedBy() {
			return addedBy;
		}

		public void setAddedBy(String addedBy) {
			this.addedBy = addedBy;
		}

		public String getReferenceNumber() {
			return referenceNumber;
		}

		public void setReferenceNumber(String referenceNumber) {
			this.referenceNumber = referenceNumber;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

		public Long getTotalItems() {
			return totalItems;
		}

		public void setTotalItems(Long totalItems) {
			this.totalItems = totalItems;
		}
		
		

		public Long getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Long totalAmount) {
			this.totalAmount = totalAmount;
		}
		

		public double getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(double subtotal) {
			this.subtotal = subtotal;
		}

		public double getTotalTax() {
			return totalTax;
		}

		public void setTotalTax(double totalTax) {
			this.totalTax = totalTax;
		}

		public Long getTotalShippedItems() {
			return totalShippedItems;
		}

		public void setTotalShippedItems(Long totalShippedItems) {
			this.totalShippedItems = totalShippedItems;
		}

		public String getAdditionalNotes() {
			return additionalNotes;
		}

		public void setAdditionalNotes(String additionalNotes) {
			this.additionalNotes = additionalNotes;
		}

		public List<PurchaseReturnItems> getPurchaseReturnItems() {
			return purchaseReturnItems;
		}

		public void setPurchaseReturnItems(List<PurchaseReturnItems> purchaseReturnItems) {
			this.purchaseReturnItems = purchaseReturnItems;
		}

		public com.franchise.Service.IdGenerator getIdGenerator() {
			return idGenerator;
		}

		public void setIdGenerator(com.franchise.Service.IdGenerator idGenerator) {
			this.idGenerator = idGenerator;
		}
	    
		  @PrePersist
		    private void generatePurchaseReturnId() {
		        if (this.purchaseReturnId == null && idGenerator != null) {
		            this.purchaseReturnId = idGenerator.generatePurchaseReturnId();
		        }
		    }

		public List<StockTransaction> getStockTransaction() {
			return stockTransaction;
		}

		public void setStockTransaction(List<StockTransaction> stockTransaction) {
			this.stockTransaction = stockTransaction;
		}
	    
	    
}
