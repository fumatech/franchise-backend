package com.franchise.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.franchise.Entity.ItemReportDTO;
import com.franchise.Repository.ItemReportRepo;

@Service
public class ItemReportService {

	private final ItemReportRepo itemReportRepo;

	public ItemReportService(ItemReportRepo itemReportRepo) {
		this.itemReportRepo = itemReportRepo;
	}

	public List<ItemReportDTO> getAllItemReports() {

		List<ItemReportDTO> combined = new ArrayList<>();

		combined.addAll(itemReportRepo.fetchPurchaseDI());
		combined.addAll(itemReportRepo.fetchPurchasePO());
		combined.addAll(itemReportRepo.fetchSale());

		return combined;
	}
}
