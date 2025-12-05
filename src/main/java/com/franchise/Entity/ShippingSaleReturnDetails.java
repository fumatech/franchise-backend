package com.franchise.Entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class ShippingSaleReturnDetails {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String shippingDetails;
	    private BigDecimal shippingCharges;

	   
	    @ElementCollection
	    @CollectionTable(name = "additional_Return_expenses", joinColumns = @JoinColumn(name = "shipping_Return_details_id"))
	    private List<String> additionalExpensesName;

	    @ElementCollection
	    @CollectionTable(name = "additional_Return_expenses_amount", joinColumns = @JoinColumn(name = "shipping_Return_details_id"))
	    private List<BigDecimal> amount;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "sale_Return_Order_id") 
	    @JsonBackReference
	    private SaleReturn saleReturn;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getShippingDetails() {
			return shippingDetails;
		}


		public void setShippingDetails(String shippingDetails) {
			this.shippingDetails = shippingDetails;
		}


		public BigDecimal getShippingCharges() {
			return shippingCharges;
		}


		public void setShippingCharges(BigDecimal shippingCharges) {
			this.shippingCharges = shippingCharges;
		}


		public List<String> getAdditionalExpensesName() {
			return additionalExpensesName;
		}


		public void setAdditionalExpensesName(List<String> additionalExpensesName) {
			this.additionalExpensesName = additionalExpensesName;
		}


		public List<BigDecimal> getAmount() {
			return amount;
		}


		public void setAmount(List<BigDecimal> amount) {
			this.amount = amount;
		}


		public SaleReturn getSaleReturn() {
			return saleReturn;
		}


		public void setSaleReturn(SaleReturn saleReturn) {
			this.saleReturn = saleReturn;
		}


 

}
