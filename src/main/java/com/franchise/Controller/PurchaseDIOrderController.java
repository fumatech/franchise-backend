package com.franchise.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.franchise.Entity.PurchaseDIOrder;
import com.franchise.Service.PurchaseDIOrderService;

@RestController
@RequestMapping("/purchase-di-order")
@CrossOrigin(
	    origins = {
	      "http://localhost:3000",
	      "http://fusionmastertech.com",
	      "https://fusionmastertech.com",
	      "http://www.fusionmastertech.com",
	      "https://www.fusionmastertech.com"
	    },
	    allowCredentials = "true"
	)
public class PurchaseDIOrderController {

    @Autowired
    private PurchaseDIOrderService purchaseDIOrderService;

    @PostMapping("/save")
    public ResponseEntity<PurchaseDIOrder> createPurchase(@RequestBody PurchaseDIOrder purchaseDIOrder) {
    	PurchaseDIOrder savedOrderPurchase = purchaseDIOrderService.savePurchaseDIOrder(purchaseDIOrder);
        return new ResponseEntity<>(savedOrderPurchase, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PurchaseDIOrder>> getAllPurchases() {
        List<PurchaseDIOrder> orders = purchaseDIOrderService.getAllPurchaseDIOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<PurchaseDIOrder>> getPurchaseOrderById(@PathVariable Long id) {
        Optional<PurchaseDIOrder> orders = purchaseDIOrderService.getPurchaseDIOrderById(id);
        return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // New endpoint to update a purchase order
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseDIOrder> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseDIOrder purchaseOrder) {
    	PurchaseDIOrder updatedOrder = purchaseDIOrderService.updatePurchaseDIOrder(id, purchaseOrder);
        return updatedOrder != null ? new ResponseEntity<>(updatedOrder, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        purchaseDIOrderService.deletePurchaseDIOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/getAllOrderIds")
    public ResponseEntity<List<String>> getAllOrderIds() {
        List<String> orderIds = purchaseDIOrderService.getAllOrderIds();
        return new ResponseEntity<>(orderIds, HttpStatus.OK);
    }

}
