package com.franchise.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.TaxRate;
import com.franchise.Service.TaxRateService;

@RestController
@RequestMapping("/taxrate")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class TaxRateController {

    @Autowired
    private TaxRateService taxRateService;

    @GetMapping("/getall")
    public List<TaxRate> getAllTaxRates() {
        return taxRateService.getAllTaxRates();
    }

    @PostMapping("/create")
    public TaxRate createTaxRate(@RequestBody TaxRate taxRate) {
        return taxRateService.createTaxRate(taxRate);
    }

    @PutMapping("/update/{id}")
    public TaxRate updateTaxRate(@PathVariable Long id, @RequestBody TaxRate taxRate) {
        return taxRateService.updateTaxRate(id, taxRate);
    }

    @DeleteMapping("/delete/{id}") 
    public void deleteTaxRate(@PathVariable Long id) {
    	System.out.print("Req recived");
        taxRateService.deleteTaxRate(id);
    }
}