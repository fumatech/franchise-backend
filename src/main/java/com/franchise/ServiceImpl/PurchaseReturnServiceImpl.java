package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.PurchaseReturn;
import com.franchise.Entity.PurchaseReturnItems;
import com.franchise.Entity.StockTransaction;
import com.franchise.Repository.PurchaseReturnRepo;
import com.franchise.Service.IdGenerator;
import com.franchise.Service.PurchaseReturnService;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {
	
	
	@Autowired
    private PurchaseReturnRepo purchaseReturnRepo;
    @Autowired
    private IdGenerator idGenerator;
    
    
    @Override
	public PurchaseReturn savePurchaseReturn(PurchaseReturn purchaseReturn) {
    	  if (purchaseReturn.getPurchaseReturnItems() != null) {
    		  purchaseReturn.setIdGenerator(idGenerator); // Pass the IdGenerator to the entity

              for (PurchaseReturnItems item : purchaseReturn.getPurchaseReturnItems()) {
                  item.setPurchaseReturn(purchaseReturn);
              }
          } 
    	  if(purchaseReturn.getStockTransaction() !=null) {
    		  for (StockTransaction stock : purchaseReturn.getStockTransaction()) {
    			  stock.setPurchaseReturn(purchaseReturn);
    		  }
    	  }
          return purchaseReturnRepo.save(purchaseReturn);
      }


	@Override
	public List<PurchaseReturn> getAllPurchaseReturns() {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findAll();
	}

	@Override
	public Optional<PurchaseReturn> getPurchaseReturnById(Long id) {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.findById(id);
	}

	@Override
	public PurchaseReturn updatePurchaseReturn(Long id, PurchaseReturn updatedPurchaseReturn) {
	    Optional<PurchaseReturn> existingOrder = purchaseReturnRepo.findById(id);

	    if (existingOrder.isPresent()) {
	        PurchaseReturn purchaseReturn = existingOrder.get();
	        purchaseReturn.setVendor(updatedPurchaseReturn.getVendor());
	        purchaseReturn.setStatus(updatedPurchaseReturn.getStatus());
	        purchaseReturn.setAddedBy(updatedPurchaseReturn.getAddedBy());
	        purchaseReturn.setReferenceNumber(updatedPurchaseReturn.getReferenceNumber());
	        purchaseReturn.setOrderDate(updatedPurchaseReturn.getOrderDate());
	        purchaseReturn.setLocation(updatedPurchaseReturn.getLocation());
	        purchaseReturn.setTotalItems(updatedPurchaseReturn.getTotalItems());
	        purchaseReturn.setTotalShippedItems(updatedPurchaseReturn.getTotalShippedItems());
	        purchaseReturn.setAdditionalNotes(updatedPurchaseReturn.getAdditionalNotes());

	        // Check if updatedPurchaseReturn.getPurchaseReturnItems() is not null
	        if (updatedPurchaseReturn.getPurchaseReturnItems() != null) {
	            // Clear and add new items
	            purchaseReturn.getPurchaseReturnItems().clear();
	            purchaseReturn.getPurchaseReturnItems().addAll(updatedPurchaseReturn.getPurchaseReturnItems());
	            
	            // Set purchase return reference for each item
	            for (PurchaseReturnItems item : purchaseReturn.getPurchaseReturnItems()) {
	                item.setPurchaseReturn(purchaseReturn);
	            }
	        }
	        
	        if (updatedPurchaseReturn.getStockTransaction()!= null) {
	        	purchaseReturn.getStockTransaction().clear();
	        	purchaseReturn.getStockTransaction().addAll(updatedPurchaseReturn.getStockTransaction());
	        	
	        	for(StockTransaction stock: purchaseReturn.getStockTransaction()) {
	        		stock.setPurchaseReturn(purchaseReturn);
	        	}
	        	
	        			
	        }

	        return purchaseReturnRepo.save(purchaseReturn);
	    }

	    return null; // or throw an exception if not found
	}


	@Override
	public void deletePurchaseReturn(Long id) {
		   if (purchaseReturnRepo.existsById(id)) {
			   purchaseReturnRepo.deleteById(id);
	        }
	    }


	@Override
	public List<String> getAllReturnIds() {
		 return purchaseReturnRepo.findAll().stream()
		            .map(PurchaseReturn::getPurchaseReturnId)
		            .collect(Collectors.toList());
		    }

	@Override
	public Optional<PurchaseReturn> getPurchaseReturnByPRId(String purchaseReturnId) {
		// TODO Auto-generated method stub
        return Optional.ofNullable(purchaseReturnRepo.findByPurchaseReturnId(purchaseReturnId));
	}

	@Override
	public List<String> getPurchaseReturnIdsByStatus(Long status) {
		// TODO Auto-generated method stub
        return purchaseReturnRepo.findPurchaseReturnIdsByStatus(status);
	}

	@Override
	public List<PurchaseReturn> getPendingOrders() {
		// TODO Auto-generated method stub
        return purchaseReturnRepo.findByPurchaseReturnStatus(0L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getAcceptedOrders() {
		// TODO Auto-generated method stub
        return purchaseReturnRepo.findByPurchaseReturnStatus(1L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getRejectedOrders() {
		// TODO Auto-generated method stub
        return purchaseReturnRepo.findByPurchaseReturnStatus(2L); // Fetch orders with status 0
	}

	@Override
	public List<PurchaseReturn> getShipOrders() {
		// TODO Auto-generated method stub
        return purchaseReturnRepo.findByPurchaseReturnStatus(3L); // Fetch orders with status 0
	}

	@Override
	public Long getTotalShippedItems(String purchaseReturnId) {
		// TODO Auto-generated method stub
		return purchaseReturnRepo.getTotalShippedItems(purchaseReturnId);
	}

	

}
