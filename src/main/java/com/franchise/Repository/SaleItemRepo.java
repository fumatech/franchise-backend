package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.franchise.Entity.SaleItems;
import com.franchise.Entity.SellReportDTO;

public interface SaleItemRepo extends JpaRepository<SaleItems, Long> {

	@Query("""
			    SELECT new com.franchise.Entity.SellReportDTO(
			        i.productName,
			        i.productSku,
			        null,
			        o.customer,
			        o.referenceNumber,
			        o.saleDate,
			        i.quantity,
			        i.unitCostBeforeDiscount,
			        i.discountPercent,
			        i.taxAmount,
			        unitSellingPrice,
			        i.lineTotal
			    )
			    FROM SaleItems i
			    JOIN i.sale o
			""")
	List<SellReportDTO> getSoSellReport();
}
