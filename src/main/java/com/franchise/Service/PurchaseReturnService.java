package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.PurchaseReturn;

public interface PurchaseReturnService {
	
	PurchaseReturn savePurchaseReturn(PurchaseReturn purchaseReturn);
	
	List<PurchaseReturn> getAllPurchaseReturns();
	
	
    Optional<PurchaseReturn> getPurchaseReturnById(Long id);

    PurchaseReturn updatePurchaseReturn(Long id, PurchaseReturn purchaseReturn); // New method for update
    
    void deletePurchaseReturn(Long id); // New method for delete
    
    public List<String> getAllReturnIds();
    
    Optional<PurchaseReturn> getPurchaseReturnByPRId(String id);
    
    List<String> getPurchaseReturnIdsByStatus(Long status);
    
    
    

    List<PurchaseReturn> getPendingOrders();  // New method for pending orders
    List<PurchaseReturn> getAcceptedOrders(); // New method for accepted orders
    List<PurchaseReturn> getRejectedOrders();
    List<PurchaseReturn> getShipOrders();


    Long getTotalShippedItems(String purchaseReturnId);

    


}
