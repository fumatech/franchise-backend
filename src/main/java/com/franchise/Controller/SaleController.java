package com.franchise.Controller;

import java.util.ArrayList;
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
import com.franchise.Entity.Sale;
import com.franchise.Service.SaleService;

@RestController
@RequestMapping("/sale")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class SaleController {

    @Autowired
    private SaleService saleService;

    // Create a new sale
    @PostMapping("/save")
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale newSale = saleService.createSale(sale);
        return ResponseEntity.ok(newSale);
    }

    // Retrieve all sales
    @GetMapping("/getall")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        
        if (sales == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
        
        return ResponseEntity.ok(sales);
    }


    // Retrieve a sale by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing sale by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale saleDetails) {
        try {
            Sale updatedSale = saleService.updateSale(id, saleDetails);
            return ResponseEntity.ok(updatedSale);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a sale by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getAllOrderIds")
    public ResponseEntity<List<String>> getAllOrderIds() {
        List<String> orderIds = saleService.getAllOrderIds();
        return new ResponseEntity<>(orderIds, HttpStatus.OK);
    }

    
    // Get a Purchase Order by ID
    @GetMapping("/getSaleDataById/{id}")
    public ResponseEntity<Sale> getSaleDataById(@PathVariable String id) {
        Optional<Sale> order = saleService.getSaleOrderById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
}
