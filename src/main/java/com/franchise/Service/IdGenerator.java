package com.franchise.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

 
import com.franchise.Repository.PurchaseOrderRepo;
import com.franchise.Repository.PurchaseDIOrderRepo;

import com.franchise.Repository.PurchaseReturnRepo;
import com.franchise.Repository.SaleRepo;

@Service
public class IdGenerator {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    
    private  SaleRepo saleRepo;
    
    
    @Autowired
    
    private PurchaseReturnRepo  PurchaseReturnRepo;

	private PurchaseDIOrderRepo purchaseDIOrderRepo;

    public synchronized String generatePurchaseOrderId() {
        Long lastId = purchaseOrderRepo.getLastPurchaseOrderId();
        Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
        return "FUMAPO" + nextId;
    }
    public synchronized String generatePurchaseDIOrderId() {
		Long lastId = purchaseDIOrderRepo.getLastPurchaseDIOrderId();
		Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
		return "FUMAPDI" + nextId;
	}
  
    
    public synchronized String generatePurchaseReturnId() {
        Long lastId = PurchaseReturnRepo.getLastPurchaseReturnId();
        Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
        return "FUMAPR" + nextId;
    }
    
    public synchronized String generateSaleOrderId() {
        Long lastId = saleRepo.getLastSaleOrderId();
        Long nextId = (lastId != null) ? lastId + 1 : 1; // If no IDs exist, start from 1
        return "FUMASID" + nextId;
    }


 

    
}
