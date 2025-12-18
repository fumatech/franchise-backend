package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.ProductPurchaseReportDTO;
import com.franchise.Entity.PurchasePoItem;

@Repository
public interface PurchasePoItemRepo extends JpaRepository<PurchasePoItem, Long> {

	@Query("""
			    SELECT new com.franchise.Entity.ProductPurchaseReportDTO(
			        i.productName,
			        i.productSku,
			        o.vendor,
			        o.referenceNumber,
			        o.orderDate,
			        i.quantity,
			        i.unitCostAfterDiscount,
			        i.lineTotal,
			        null,
			        i.productVariationName,
			        null
			    )
			    FROM PurchasePoItem i
			    JOIN i.purchasePoOrder o
			""")
	List<ProductPurchaseReportDTO> fetchAllPOItems();
}
