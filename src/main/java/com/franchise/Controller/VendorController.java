package com.franchise.Controller;

 import com.franchise.Entity.Vendor;
import com.franchise.Service.VendorService;

 
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/vendor")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)public class VendorController {

	@Autowired
	private VendorService vendorService;

	// Endpoint to create a new vendor
	@PostMapping("/save")
	public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
		Vendor savedVendor = vendorService.saveVendor(vendor);
		return ResponseEntity.ok(savedVendor); 
	}
	
	
 

	// Endpoint to get all vendors
	@GetMapping("/getall")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		List<Vendor> vendors = vendorService.getAllVendors();
		return ResponseEntity.ok(vendors);
	}

	// Endpoint to get a vendor by its ID
	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
		Vendor vendor = vendorService.getVendorById(id);
		if (vendor != null) {
			return ResponseEntity.ok(vendor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}      

	// Endpoint to update a vendor
	@PutMapping("/update/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor updatedVendor) {
		Vendor vendor = vendorService.updateVendor(id, updatedVendor);
		if (vendor != null) {
			return ResponseEntity.ok(vendor);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Endpoint to delete a vendor by its ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteVendorById(@PathVariable Long id) {
		vendorService.deleteVendorById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/firmname/email/{email}")
	public ResponseEntity<Map<String, String>> getFirmNameByEmail(@PathVariable String email) {
	    Optional<String> firmName = vendorService.findFirmNameByEmail(email);
	    if (firmName.isPresent()) {
	        // Return a JSON object with a key "firmName" and the firm name value
	        Map<String, String> response = new HashMap<>();
	        response.put("firmName", firmName.get());
	        return ResponseEntity.ok(response); // Return the response as a JSON object
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


	
}
