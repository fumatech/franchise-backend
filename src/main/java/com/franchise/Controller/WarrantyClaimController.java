package com.franchise.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.WarrantyClaim;
import com.franchise.Service.WarrantyClaimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/warranty-claim")
@CrossOrigin(origins = { "http://fusionmastertech.com", "https://fusionmastertech.com","http://localhost:3000","http://localhost:3001"    },
allowCredentials = "true")
public class WarrantyClaimController {

    private static final Logger logger = LoggerFactory.getLogger(WarrantyClaimController.class);

    @Autowired
    private WarrantyClaimService warrantyClaimService;

    // Save a new Warranty Claim
    @PostMapping("/save")
    public ResponseEntity<WarrantyClaim> saveWarrantyClaim(@RequestBody WarrantyClaim warrantyClaim) {
        try {
            WarrantyClaim savedWarrantyClaim = warrantyClaimService.saveWarrantyClaim(warrantyClaim);
            logger.info("Saved new warranty claim with ID: {}", savedWarrantyClaim.getId());
            return new ResponseEntity<>(savedWarrantyClaim, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving warranty claim: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all Warranty Claims
    @GetMapping("/getall")
    public ResponseEntity<List<WarrantyClaim>> getAllWarrantyClaims() {
        try {
            List<WarrantyClaim> warrantyClaims = warrantyClaimService.getAllWarrantyClaims();
            if (warrantyClaims.isEmpty()) {
                logger.info("No warranty claims found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(warrantyClaims, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving warranty claims: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a Warranty Claim by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<WarrantyClaim> getWarrantyClaimById(@PathVariable("id") Long id) {
        Optional<WarrantyClaim> warrantyClaim = warrantyClaimService.getWarrantyClaimById(id);
        return warrantyClaim.map(
                claim -> {
                    logger.info("Warranty claim found with ID: {}", id);
                    return new ResponseEntity<>(claim, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.warn("Warranty claim with ID: {} not found", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    // Update an existing Warranty Claim
    @PutMapping("/update/{id}")
    public ResponseEntity<WarrantyClaim> updateWarrantyClaim(
            @PathVariable("id") Long id, @RequestBody WarrantyClaim warrantyClaim) {
        try {
            WarrantyClaim updatedWarrantyClaim = warrantyClaimService.updateWarrantyClaim(id, warrantyClaim);
            logger.info("Updated warranty claim with ID: {}", id);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating warranty claim with ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Warranty Claim by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWarrantyClaim(@PathVariable("id") Long id) {
        try {
            warrantyClaimService.deleteWarrantyClaim(id);
            logger.info("Deleted warranty claim with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting warranty claim with ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update the status of a Warranty Claim by ID
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<WarrantyClaim> updateWarrantyClaimStatus(
            @PathVariable("id") Long id, @RequestBody Long newStatus) {
        try {
            WarrantyClaim updatedWarrantyClaim = warrantyClaimService.updateWarrantyClaimStatus(id, newStatus);
            logger.info("Updated warranty claim status with ID: {}", id);
            return new ResponseEntity<>(updatedWarrantyClaim, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating warranty claim status with ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
