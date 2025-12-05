package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	List<Product> findByProductId(Long productId);

	List<Product> findByVariationId(Long variationId);

	List<Product> findByProductName(String productName);

	List<Product> findByVariationName(String variationName);

	boolean existsByProductIdAndVariationIdAndProductNameAndVariationName(Long productId, Long variationId,
			String productName, String variationName);
}
