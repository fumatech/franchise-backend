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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.franchise.Entity.Product;
import com.franchise.Service.ProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = { "http://fusionmastertech.com", "https://fusionmastertech.com", "http://localhost:3000",
		"http://localhost:3001" }, allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;

	// ✅ Create Product
	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {
		if (productService.existsByAllFields(product.getProductId(), product.getVariationId(), product.getProductName(),
				product.getVariationName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					"Product with same Product ID, Variation ID, Product Name and Variation Name already exists.");
		}

		Product saved = productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	// ✅ Update Product
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
		Product updated = productService.updateProduct(id, product);
		if (updated == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		return ResponseEntity.ok(updated);
	}

	// ✅ Delete Product
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted successfully.");
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getById(@PathVariable Long id) {
		Optional<Product> product = productService.getProductById(id);
		return product.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
	}

	// ✅ Get All
	@GetMapping("/all")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	// ✅ Get by Product ID
	@GetMapping("/by-product-id/{productId}")
	public List<Product> getByProductId(@PathVariable Long productId) {
		return productService.findByProductId(productId);
	}

	// ✅ Get by Variation ID
	@GetMapping("/by-variation-id/{variationId}")
	public List<Product> getByVariationId(@PathVariable Long variationId) {
		return productService.findByVariationId(variationId);
	}

	// ✅ Get by Product Name
	@GetMapping("/by-product-name/{productName}")
	public List<Product> getByProductName(@PathVariable String productName) {
		return productService.findByProductName(productName);
	}

	// ✅ Get by Variation Name
	@GetMapping("/by-variation-name/{variationName}")
	public List<Product> getByVariationName(@PathVariable String variationName) {
		return productService.findByVariationName(variationName);
	}

	// ✅ Check if exists
	@GetMapping("/check-exists")
	public ResponseEntity<?> checkIfExists(@RequestParam Long productId, @RequestParam Long variationId,
			@RequestParam String productName, @RequestParam String variationName) {
		boolean exists = productService.existsByAllFields(productId, variationId, productName, variationName);
		return ResponseEntity.ok(exists ? "Product already exists." : "Product does not exist.");
	}
}
