package com.franchise.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.franchise.Entity.PaymentAccount;
import com.franchise.Entity.SaleReturn;
import com.franchise.Entity.SaleReturnItem;
import com.franchise.Entity.ShippingSaleReturnDetails;
import com.franchise.Entity.StockTransaction;
import com.franchise.Entity.Transaction;
import com.franchise.Repository.PaymentAccountRepo;
import com.franchise.Repository.SaleReturnRepo;
import com.franchise.Service.SaleReturnService;

@Service
public class SaleReturnServiceImpl implements SaleReturnService {
	@Autowired 
	SaleReturnRepo  saleReturnRepo;
	
	 @Autowired
	    private PaymentAccountRepo paymentAccountRepo;


	@Override
	public SaleReturn saveSaleReturn(SaleReturn saleReturn) {
		 if (saleReturn.getSaleReturnItem() != null) {
	            for (SaleReturnItem item : saleReturn.getSaleReturnItem()) {
	                item.setSaleReturn(saleReturn);
	            }
	        }
		  
 
		  if(saleReturn.getShippingSaleReturnDetails() !=null)
		  {
			  for(ShippingSaleReturnDetails ship:saleReturn.getShippingSaleReturnDetails())
			  {
				  ship.setSaleReturn(saleReturn);
			  }
		  }
		  
		  if(saleReturn.getStockTransaction() != null) {
			  for(StockTransaction stock:saleReturn.getStockTransaction()) {
				  stock.setSaleReturn(saleReturn);
			  }
		  }
		  
		 
	        // Handle associated transactions and update payment account balances
	        if (saleReturn.getTransaction() != null) {
	        	
	             for (Transaction transaction : saleReturn.getTransaction()) {
	                transaction.setSaleReturn(saleReturn);

	                // Ensure the payment account is valid
	                if (transaction.getPaymentAccount() == null && transaction.getPaymentAccountId() != null) {
	                    PaymentAccount paymentAccount = paymentAccountRepo.findById(transaction.getPaymentAccountId())
	                            .orElseThrow(() -> new RuntimeException("Payment Account not found for ID: " + transaction.getPaymentAccountId()));

	                    transaction.setPaymentAccount(paymentAccount);
	                }

	                if (transaction.getPaymentAccount() == null) {
	                    throw new RuntimeException("Transaction must be associated with a valid Payment Account.");
	                }

	                // Calculate the new balance based on the transaction type
	                BigDecimal previousBalance = transaction.getPaymentAccount().getBalance() != null
	                        ? transaction.getPaymentAccount().getBalance()
	                        : BigDecimal.ZERO;
	                BigDecimal newBalance = previousBalance;

	                switch (transaction.getTransactionType().toLowerCase()) {
	                    case "deposit":
	                    case "purchase":
	                    case "sale_return":

	                    case "opening_balance":
	                        newBalance = previousBalance.add(transaction.getAmount());
	                        break;
	                    case "sale":
	                    case "expense":
	                        newBalance = previousBalance.subtract(transaction.getAmount());
	                        break;
	                    default:
	                        throw new RuntimeException("Unknown transaction type: " + transaction.getTransactionType());
	                }

	                // Update transaction balance and save payment account
	                transaction.setBalance(newBalance);
	                transaction.getPaymentAccount().setBalance(newBalance);
	                paymentAccountRepo.save(transaction.getPaymentAccount());
	            }
	        }
		  
	        return saleReturnRepo.save(saleReturn);
	    }

	@Override
	public List<SaleReturn> getAllSaleReturn() {
		// TODO Auto-generated method stub
		return saleReturnRepo.findAll();
	}

	@Override
	public Optional<SaleReturn> getSaleReturnById(Long id) {
		// TODO Auto-generated method stub
		return saleReturnRepo.findById(id);
	}

	@Override
	public SaleReturn updateSaleReturn(Long id, SaleReturn saleReturn) {
		 Optional<SaleReturn> existingOrder =  saleReturnRepo.findById(id);

	        if (existingOrder.isPresent()) {
	        	SaleReturn purchaseDIOrder = existingOrder.get();
	        	purchaseDIOrder.setOrderId(saleReturn.getOrderId());
	        	purchaseDIOrder.setOrderRefernceNumber(saleReturn.getOrderRefernceNumber());
	        	purchaseDIOrder.setReferenceNumber(saleReturn.getReferenceNumber());
	        	purchaseDIOrder.setOrderedBy(saleReturn.getOrderedBy());
	            purchaseDIOrder.setAddedBy(saleReturn.getAddedBy());
	            purchaseDIOrder.setOrderDate(saleReturn.getOrderDate());
	            purchaseDIOrder.setSaleDate(saleReturn.getSaleDate());
	            purchaseDIOrder.setPayTermNumber(saleReturn.getPayTermNumber());
	            purchaseDIOrder.setPayTermType(saleReturn.getPayTermType());
	            purchaseDIOrder.setLocation(saleReturn.getLocation());
	            purchaseDIOrder.setNetTotalAmount(saleReturn.getNetTotalAmount());
	            purchaseDIOrder.setDiscountType(saleReturn.getDiscountType());
	            purchaseDIOrder.setDiscountAmount(saleReturn.getDiscountAmount());
	            purchaseDIOrder.setSaleTax(saleReturn.getSaleTax());
	            purchaseDIOrder.setTaxAmount(saleReturn.getTaxAmount());
	            
	            purchaseDIOrder.setTotalItems(saleReturn.getTotalItems());
	            purchaseDIOrder.setAdditionalNotes(saleReturn.getAdditionalNotes());
	          

	            // Update the order items
	            purchaseDIOrder.getSaleReturnItem().clear();
	            purchaseDIOrder.getSaleReturnItem().addAll(saleReturn.getSaleReturnItem());
	         

	            // Set purchase order reference in each item
	            for (SaleReturnItem item : purchaseDIOrder.getSaleReturnItem()) {
	                item.setSaleReturn(saleReturn);
	            }
	            
	            
  	           
	            
	            return saleReturnRepo.save(purchaseDIOrder);
	        }

	        return null; 
	}

	@Override
	public void deleteSaleReturn(Long id) {

		// TODO Auto-generated method stub
		 if (saleReturnRepo.existsById(id)) {
			 saleReturnRepo.deleteById(id);
	        }		
	}

	@Override
	public List<String> getAllReturnIds() {
		// TODO Auto-generated method stub
		return null; 
	}
	
	@Override
	public Optional<SaleReturn> getSaleOrderById(String orderId) {
		// TODO Auto-generated method stub
        return Optional.ofNullable(saleReturnRepo.findByOrderId(orderId));
	}

}
