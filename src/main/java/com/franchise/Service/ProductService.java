package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.Product;

public interface ProductService {
	Product saveProduct(Product product);

	Product updateProduct(Long id, Product product);

	void deleteProduct(Long id);

	Optional<Product> getProductById(Long id);

	List<Product> getAllProducts();

	List<Product> findByProductId(Long productId);

	List<Product> findByVariationId(Long variationId);

	List<Product> findByProductName(String productName);

	List<Product> findByVariationName(String variationName);

	boolean existsByAllFields(Long productId, Long variationId, String productName, String variationName);
}
