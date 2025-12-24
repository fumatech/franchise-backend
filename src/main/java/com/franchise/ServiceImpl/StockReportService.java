package com.franchise.ServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.StockReportDTO;
import com.franchise.Entity.StockTransaction;
import com.franchise.Repository.StockTransactionRepo;

@Service
public class StockReportService {

	@Autowired
	private StockTransactionRepo stockTransactionRepo;

	public List<StockReportDTO> getStockReport() {

		Map<String, StockReportDTO> map = new HashMap<>();

		List<StockTransaction> transactions = stockTransactionRepo.findAll();

		for (StockTransaction tx : transactions) {

			String key = tx.getProductId() + "_" + tx.getVariationId();

			StockReportDTO dto = map.computeIfAbsent(key,
					k -> new StockReportDTO(tx.getProductId(), tx.getVariationId(), null, null, null, null, null, null,
							BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO,
							BigDecimal.ZERO));

			int qty = tx.getQuantity();
			BigDecimal price = tx.getPrice() == null ? BigDecimal.ZERO : tx.getPrice();

			switch (tx.getTransactionType()) {

			case "po_purchase":
			case "di_purchase":
			case "purchase":
			case "sale_return":

				dto.setTotalPurchased(dto.getTotalPurchased() + qty);
				dto.setDefaultPurchasePrice(price); // last purchase price
				break;

			case "sale":
			case "purchase_return":
				dto.setTotalSold(dto.getTotalSold() + qty);
				dto.setUnitSellingPrice(price); // last selling price
				break;

			case "adjustment_in":
				dto.setTotalAdjusted(dto.getTotalAdjusted() + qty);
				break;

			case "adjustment_out":
				dto.setTotalAdjusted(dto.getTotalAdjusted() - qty);
				break;
			}
		}

		// ================= FINAL CALC =================
		map.values().forEach(dto -> {

			int currentStock = dto.getTotalPurchased() + dto.getTotalAdjusted() - dto.getTotalSold();

			dto.setCurrentStock(currentStock);

			BigDecimal purchaseValue = dto.getDefaultPurchasePrice().multiply(BigDecimal.valueOf(currentStock));

			BigDecimal saleValue = dto.getUnitSellingPrice().multiply(BigDecimal.valueOf(currentStock));

			dto.setCurrentStockValueByPurchase(purchaseValue);
			dto.setCurrentStockValueBySale(saleValue);
			dto.setPotentialProfit(saleValue.subtract(purchaseValue));
		});

		return List.copyOf(map.values());
	}
}
