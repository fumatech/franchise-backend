package com.franchise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.ItemReportDTO;
import com.franchise.Entity.PurchaseDIItem;

@Repository
public interface ItemReportRepo extends JpaRepository<PurchaseDIItem, Long> {

	// ===================== PURCHASE DI ITEMS =====================
	@Query("""
			    SELECT new com.franchise.Entity.ItemReportDTO(
			        pdi.id,
			        pdi.productName,
			        pdi.productId,
			        pdi.productVariationId,
			        pdi.productSku,
			        porder.additionalNotes,
			        porder.orderDate,
			        pdi.lineTotal,
			        pdi.productVariationName,
			        porder.vendor,
			        pdi.unitCostAfterDiscount,
			        null,
			        null,
			        null,
			        porder.location,
			        null,
			        null,
			        null
			    )
			    FROM PurchaseDIItem pdi
			    JOIN pdi.purchaseDIOrder porder
			""")
	List<ItemReportDTO> fetchPurchaseDI();

	// ===================== PURCHASE PO ITEMS =====================
	@Query("""
			    SELECT new com.franchise.Entity.ItemReportDTO(
			        ppi.id,
			        ppi.productName,
			        ppi.productId,
			        ppi.productVariationId,
			        ppi.productSku,
			        porder.additionalNotes,
			        porder.orderDate,
			        ppi.lineTotal,
			        ppi.productVariationName,
			        porder.vendor,
			        ppi.unitCostAfterDiscount,
			        null,
			        null,
			        null,
			        porder.location,
			        null,
			        null,
			        null
			    )
			    FROM PurchasePoItem ppi
			    JOIN ppi.purchasePoOrder porder
			""")
	List<ItemReportDTO> fetchPurchasePO();

	// ===================== SALE ITEMS =====================
	@Query("""
			    SELECT new com.franchise.Entity.ItemReportDTO(
			        ssi.id,
			        ssi.productName,
			        ssi.productId,
			        ssi.productVariationId,
			        ssi.productSku,
			        sorder.saleNotes,
			        null,
			        null,
			        ssi.productVariationName,
			        null,
			        null,
			        sorder.saleDate,
			        ssi.lineTotal,
			        sorder.customer,
			        sorder.location,
			        ssi.quantity,
			        ssi.unitSellingPrice,
			        ssi.lineTotal
			    )
			    FROM SaleItems ssi
			    JOIN ssi.sale sorder
			""")
	List<ItemReportDTO> fetchSale();
}
