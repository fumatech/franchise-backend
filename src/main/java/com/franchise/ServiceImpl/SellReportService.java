package com.franchise.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.SellReportDTO;
import com.franchise.Repository.SaleItemRepo;

@Service
public class SellReportService {

	@Autowired
	private SaleItemRepo saleItemRepo;

	public List<SellReportDTO> getSellReport() {

		List<SellReportDTO> report = new ArrayList<>();

		report.addAll(saleItemRepo.getSoSellReport());

		return report;
	}
}
