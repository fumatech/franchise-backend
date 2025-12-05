package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.OrderItems;
import com.franchise.Entity.PurchaseOrder;
import com.franchise.Repository.PurchaseOrderRepo;
import com.franchise.Service.IdGenerator;
import com.franchise.Service.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;
    @Autowired
    private IdGenerator idGenerator;


    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getOrderItems() != null) {
            purchaseOrder.setIdGenerator(idGenerator); // Pass the IdGenerator to the entity

            for (OrderItems item : purchaseOrder.getOrderItems()) {
                item.setPurchaseOrder(purchaseOrder);
            }
        }
        return purchaseOrderRepo.save(purchaseOrder);
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepo.findAll();
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
        return purchaseOrderRepo.findById(id);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPurchaseOrder) {
        Optional<PurchaseOrder> existingOrder = purchaseOrderRepo.findById(id);

        if (existingOrder.isPresent()) {
            PurchaseOrder purchaseOrder = existingOrder.get();
            purchaseOrder.setVendor(updatedPurchaseOrder.getVendor());
            purchaseOrder.setStatus(updatedPurchaseOrder.getStatus());
            purchaseOrder.setAddedBy(updatedPurchaseOrder.getAddedBy());
            purchaseOrder.setReferenceNumber(updatedPurchaseOrder.getReferenceNumber());
            purchaseOrder.setOrderDate(updatedPurchaseOrder.getOrderDate());
            purchaseOrder.setLocation(updatedPurchaseOrder.getLocation());
            purchaseOrder.setFile(updatedPurchaseOrder.getFile());
            purchaseOrder.setTotalItems(updatedPurchaseOrder.getTotalItems());
            purchaseOrder.setTotalShippedItems(updatedPurchaseOrder.getTotalShippedItems());
            purchaseOrder.setAdditionalNotes(updatedPurchaseOrder.getAdditionalNotes());

            // Update the order items
            purchaseOrder.getOrderItems().clear();
            purchaseOrder.getOrderItems().addAll(updatedPurchaseOrder.getOrderItems());

            // Set purchase order reference in each item
            for (OrderItems item : purchaseOrder.getOrderItems()) {
                item.setPurchaseOrder(purchaseOrder);
            }

            return purchaseOrderRepo.save(purchaseOrder);
        }

        return null; // or throw an exception if not found
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        if (purchaseOrderRepo.existsById(id)) {
            purchaseOrderRepo.deleteById(id);
        }
    }

    @Override
    public List<String> getAllOrderIds() {
        return purchaseOrderRepo.findAll().stream()
            .map(PurchaseOrder::getPurchaseOrderId)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderByPoId(String purchaseOrderId) {
        return Optional.ofNullable(purchaseOrderRepo.findByPurchaseOrderId(purchaseOrderId));
    }
    
    
    @Override
    public List<String> getPurchaseOrderIdsByStatus(Long status) {
        return purchaseOrderRepo.findPurchaseOrderIdsByStatus(status);
    }
    
    
    @Override
    public List<PurchaseOrder> getPendingOrders() {
        return purchaseOrderRepo.findByPurchaseStatus(0L); // Fetch orders with status 0
    }

    @Override
    public List<PurchaseOrder> getAcceptedOrders() {
        return purchaseOrderRepo.findByPurchaseStatus(1L); // Fetch orders with status 1
    }
    
    @Override
    public List<PurchaseOrder> getRejectedOrders() {
        return purchaseOrderRepo.findByPurchaseStatus(2L); // Fetch orders with status 1
    }
    
    @Override
    public List<PurchaseOrder> getShipOrders() {
        return purchaseOrderRepo.findByPurchaseStatus(3L); // Fetch orders with status 1
    }
    
    
    @Override
    public Long getTotalShippedItems(String purchaseOrderId) {
        return purchaseOrderRepo.getTotalShippedItems(purchaseOrderId);
    }
    
	
	

}
