package com.franchise.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.TaxGroup;
import com.franchise.Repository.TaxGroupRepo;
import com.franchise.Service.TaxGroupService;
import com.franchise.Service.TaxRateService;

import java.util.List;

@Service
public class TaxGroupServiceImpl implements TaxGroupService {

    @Autowired
    private TaxGroupRepo taxGroupRepo;

    @Autowired
    private TaxRateService taxRateService;

    @Override
    public List<TaxGroup> getAllTaxGroups() {
        return taxGroupRepo.findAll();
    }

    @Override
    public TaxGroup createTaxGroup(TaxGroup taxGroup) {
        return taxGroupRepo.save(taxGroup);
    }

    @Override
    public TaxGroup updateTaxGroup(Long id, TaxGroup taxGroup) {
        if (taxGroupRepo.existsById(id)) {
            taxGroup.setId(id); // Ensuring the correct ID is set
            return taxGroupRepo.save(taxGroup);
        }
        return null;
    }

    @Override
    public void deleteTaxGroup(Long id) {
        if (taxGroupRepo.existsById(id)) {
            taxGroupRepo.deleteById(id);
        }
    }
}
