package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.PurchaseOrder;

public interface PurchaseOrderService {
	PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);
	
	List<PurchaseOrder> getAllPurchaseOrders();
	
	
    Optional<PurchaseOrder> getPurchaseOrderById(Long id);

    PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder); // New method for update
    
    void deletePurchaseOrder(Long id); // New method for delete
    
    public List<String> getAllOrderIds();
    
    Optional<PurchaseOrder> getPurchaseOrderByPoId(String id);
    
    List<String> getPurchaseOrderIdsByStatus(Long status);
    
    
    

    List<PurchaseOrder> getPendingOrders();  // New method for pending orders
    List<PurchaseOrder> getAcceptedOrders(); // New method for accepted orders
    List<PurchaseOrder> getRejectedOrders();
    List<PurchaseOrder> getShipOrders();


    Long getTotalShippedItems(String purchaseOrderId);

    
    



}
