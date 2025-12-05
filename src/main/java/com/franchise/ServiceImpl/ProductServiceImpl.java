package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Product;
import com.franchise.Repository.ProductRepo;
import com.franchise.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Product saveProduct(Product product) {
		boolean exists = productRepo.existsByProductIdAndVariationIdAndProductNameAndVariationName(
				product.getProductId(), product.getVariationId(), product.getProductName(), product.getVariationName());

		if (exists) {
			return null; // Will be handled in controller with message
		}

		return productRepo.save(product);
	}

	@Override
	public Product updateProduct(Long id, Product updatedProduct) {
		Optional<Product> existing = productRepo.findById(id);
		if (existing.isPresent()) {
			Product product = existing.get();
			product.setProductId(updatedProduct.getProductId());
			product.setVariationId(updatedProduct.getVariationId());
			product.setProductName(updatedProduct.getProductName());
			product.setVariationName(updatedProduct.getVariationName());
			product.setPrice(updatedProduct.getPrice());
			return productRepo.save(product);
		}
		return null;
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepo.findById(id);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public List<Product> findByProductId(Long productId) {
		return productRepo.findByProductId(productId);
	}

	@Override
	public List<Product> findByVariationId(Long variationId) {
		return productRepo.findByVariationId(variationId);
	}

	@Override
	public List<Product> findByProductName(String productName) {
		return productRepo.findByProductName(productName);
	}

	@Override
	public List<Product> findByVariationName(String variationName) {
		return productRepo.findByVariationName(variationName);
	}

	@Override
	public boolean existsByAllFields(Long productId, Long variationId, String productName, String variationName) {
		return productRepo.existsByProductIdAndVariationIdAndProductNameAndVariationName(productId, variationId,
				productName, variationName);
	}
}
