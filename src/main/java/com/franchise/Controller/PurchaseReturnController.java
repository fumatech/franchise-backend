package com.franchise.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.PurchaseOrder;
import com.franchise.Entity.PurchaseReturn;
import com.franchise.Service.PurchaseReturnService;

@RestController
@RequestMapping("/purchasereturn")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class PurchaseReturnController {
	
    @Autowired
    private PurchaseReturnService purchaseReturnService;
    
    
    @PostMapping("/save")
    public ResponseEntity<PurchaseReturn> createPurchase(@RequestBody PurchaseReturn purchaseReturn) {
    	PurchaseReturn savedPurchaseReturn = purchaseReturnService.savePurchaseReturn(purchaseReturn);
        return new ResponseEntity<>(savedPurchaseReturn, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PurchaseReturn>> getAllPurchaseReturns() {
        List<PurchaseReturn> returns = purchaseReturnService.getAllPurchaseReturns();
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<PurchaseReturn>> getPurchaseOrderById(@PathVariable Long id) {
        Optional<PurchaseReturn> returns = purchaseReturnService.getPurchaseReturnById(id);
        return returns.isPresent() ? new ResponseEntity<>(returns, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // New endpoint to update a purchase order
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseReturn> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseReturn purchaseReturn) {
    	PurchaseReturn updatedOrder = purchaseReturnService.updatePurchaseReturn(id, purchaseReturn);
        return updatedOrder != null ? new ResponseEntity<>(updatedOrder, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
    	purchaseReturnService.deletePurchaseReturn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/getAllOrderIds")
    public ResponseEntity<List<String>> getAllReturnIds() {
        List<String> returnIds = purchaseReturnService.getAllReturnIds();
        return new ResponseEntity<>(returnIds, HttpStatus.OK);
    }


    // Get a Purchase Order by ID
    @GetMapping("/getPoDataById/{id}")
    public ResponseEntity<PurchaseReturn> getPurchaseOrderByPoId(@PathVariable String id) {
        Optional<PurchaseReturn> order = purchaseReturnService.getPurchaseReturnByPRId(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
         
    
    @GetMapping("/getOrderIdsByStatus/{status}")
    public ResponseEntity<List<String>> getOrderIdsByStatus(@PathVariable Long status) {
        List<String> orderIds = purchaseReturnService.getPurchaseReturnIdsByStatus(status);
        return new ResponseEntity<>(orderIds, HttpStatus.OK);
    }
    
    

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PurchaseReturn> updatePurchaseStatus(@PathVariable Long id, @RequestBody PurchaseOrder updatedStatus) {
        Optional<PurchaseReturn> existingOrder = purchaseReturnService.getPurchaseReturnById(id);

        if (existingOrder.isPresent()) {
        	PurchaseReturn purchaseReturn = existingOrder.get();
        	purchaseReturn.setStatus(updatedStatus.getStatus());
        	PurchaseReturn updatedOrder = purchaseReturnService.savePurchaseReturn(purchaseReturn);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Endpoint for fetching pending orders
    @GetMapping("/getPendingOrders")
    public ResponseEntity<List<PurchaseReturn>> getPendingOrders() {
        List<PurchaseReturn> pendingOrders = purchaseReturnService.getPendingOrders();
        return new ResponseEntity<>(pendingOrders, HttpStatus.OK);
    }

    // Endpoint for fetching accepted orders
    @GetMapping("/getAcceptedOrders")
    public ResponseEntity<List<PurchaseReturn>> getAcceptedOrders() {
        List<PurchaseReturn> acceptedOrders = purchaseReturnService.getAcceptedOrders();
        return new ResponseEntity<>(acceptedOrders, HttpStatus.OK);
    }
    
    // Endpoint for fetching rejected orders
    @GetMapping("/getRejectedOrders")
    public ResponseEntity<List<PurchaseReturn>> getRejectedOrders() {
        List<PurchaseReturn> rejectedOrders = purchaseReturnService.getRejectedOrders();
        return new ResponseEntity<>(rejectedOrders, HttpStatus.OK);
    }
    
    // Endpoint for fetching ship orders
    @GetMapping("/getShipOrders")
    public ResponseEntity<List<PurchaseReturn>> getShipOrders() {
        List<PurchaseReturn> shipOrders = purchaseReturnService.getShipOrders();
        return new ResponseEntity<>(shipOrders, HttpStatus.OK);
    }
    
    


    @GetMapping("/getTotalShippedItems/{purchaseOrderId}")
    public ResponseEntity<Long> getTotalShippedItems(@PathVariable String purchaseReturnId) {
        Long totalShippedItems = purchaseReturnService.getTotalShippedItems(purchaseReturnId);
        return ResponseEntity.ok(totalShippedItems);
    }

    


}
