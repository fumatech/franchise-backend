package com.franchise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.franchise.Entity.ProductStock;
import com.franchise.Service.ProductStockService;

@RestController
@RequestMapping("/product-stock")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    // Save ProductStock
    @PostMapping("/save")
    public ResponseEntity<?> saveProductStock(@RequestBody List<ProductStock> productStocks) {
        productStockService.addProductStock(productStocks);
        return ResponseEntity.ok("Product stock saved successfully.");
    }

    // Update ProductStock by productId and variationId
    @PutMapping("/update/{productId}/{variationId}")
    public ResponseEntity<ProductStock> updateProductStock(
            @PathVariable Long productId,
            @PathVariable Long variationId,
            @RequestBody ProductStock productStock) {
        ProductStock updatedStock = productStockService.updateProductStock(productId, variationId, productStock);
        return ResponseEntity.ok(updatedStock);
    }

    // Delete ProductStock by productId and variationId
    @DeleteMapping("/delete/{productId}/{variationId}")
    public ResponseEntity<String> deleteProductStock(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        productStockService.deleteProductStock(productId, variationId);
        return ResponseEntity.ok("Product stock deleted successfully for productId: " + productId + " and variationId: " + variationId);
    }

    // Get ProductStock by productId and variationId
    @GetMapping("/get/{productId}/{variationId}")
    public ResponseEntity<ProductStock> getProductStockByProductIdAndVariationId(
            @PathVariable Long productId,
            @PathVariable Long variationId) {
        ProductStock productStock = productStockService.getProductStockByProductIdAndVariationId(productId, variationId);
        return ResponseEntity.ok(productStock);
    }

    // Get All ProductStock
    @GetMapping("/getall")
    public ResponseEntity<List<ProductStock>> getAllProductStock() {
        List<ProductStock> productStocks = productStockService.getAllProductStock();
        return ResponseEntity.ok(productStocks);
    }

    // Get ProductStock by Product ID
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ProductStock>> getProductStockByProductId(@PathVariable Long productId) {
        List<ProductStock> productStocks = productStockService.getProductStockByProductId(productId);
        return ResponseEntity.ok(productStocks);
    }

    // Get Total Stock by Variation ID
    @GetMapping("/getstock/{variationId}")
    public ResponseEntity<Long> getTotalStockByVariationId(@PathVariable Long variationId) {
        Long totalStock = productStockService.getTotalStockByVariationId(variationId);
        return ResponseEntity.ok(totalStock);
    }
}
