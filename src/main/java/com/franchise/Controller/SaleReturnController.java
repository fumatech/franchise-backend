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

 import com.franchise.Entity.SaleReturn;
import com.franchise.Service.SaleReturnService;

@RestController
@RequestMapping("/saleReturn")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class SaleReturnController {
	  @Autowired
	    private SaleReturnService saleReturnService; 
	  

	    public SaleReturnController(SaleReturnService saleReturnService) {
		super();
		this.saleReturnService = saleReturnService;
	}

		@PostMapping("/save")
	    public ResponseEntity<SaleReturn> createSale(@RequestBody SaleReturn saleReturn) {
	    	SaleReturn savedOrderSale = saleReturnService.saveSaleReturn(saleReturn);
	        return new ResponseEntity<>(savedOrderSale, HttpStatus.CREATED);
	    }

	    @GetMapping("/getall")
	    public ResponseEntity<List<SaleReturn>> getAllSales() {
	        List<SaleReturn> orders = saleReturnService.getAllSaleReturn();
	        return new ResponseEntity<>(orders, HttpStatus.OK);
	    }

	    @GetMapping("/get/{id}")
	    public ResponseEntity<Optional<SaleReturn>> getSaleOrderById(@PathVariable Long id) {
	        Optional<SaleReturn> orders = saleReturnService.getSaleReturnById(id);
	        return orders.isPresent() ? new ResponseEntity<>(orders, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }


	    @PutMapping("/update/{id}")
	    public ResponseEntity<SaleReturn> updateSaleSoOrder(
	            @PathVariable Long id, 
	            @RequestBody SaleReturn saleReturn) {
	        
	    	SaleReturn updatedOrder = saleReturnService.updateSaleReturn(id, saleReturn);
	        
	        if (updatedOrder != null) {
	            return ResponseEntity.ok(updatedOrder);  // Return updated PO Order
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Handle not found case
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
	    	saleReturnService.deleteSaleReturn(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    @GetMapping("/getAllOrderIds")
	    public ResponseEntity<List<String>> getAllOrderIds() {
	        List<String> orderIds = saleReturnService.getAllReturnIds();
	        return new ResponseEntity<>(orderIds, HttpStatus.OK);
	    }
	    
	    // Get a Purchase Order by ID
	    @GetMapping("/getSaleDataById/{id}")
	    public ResponseEntity<SaleReturn> getSaleDataById(@PathVariable String id) { 
	        Optional<SaleReturn> order = saleReturnService.getSaleOrderById(id); 
	        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }

}
