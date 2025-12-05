package com.franchise.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.Customer;
import com.franchise.Service.CustomerService;
import com.franchise.Tenant.TenantContextHolder;
@RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class CustomerController {

    @Autowired
    private CustomerService customerservice;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(
            @RequestBody Customer customer,
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        Customer savedCustomer = customerservice.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Customer>> getAllCustomers(
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        List<Customer> customers = customerservice.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id,
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        Customer customer = customerservice.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer,
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        Customer customer = customerservice.updateCustomer(id, updatedCustomer);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomerById(
            @PathVariable Long id,
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        customerservice.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/CustomerName/email/{email}")
    public ResponseEntity<Map<String, String>> getFirmNameByEmail(
            @PathVariable String email,
            @RequestHeader(value = "X-TenantID", required = false) String tenantId) {
        if (tenantId != null) {
            TenantContextHolder.setTenantDbName(tenantId);
        }

        Optional<String> franchiseName = customerservice.findCustomerByEmail(email);
        if (franchiseName.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("franchiseName", franchiseName.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
