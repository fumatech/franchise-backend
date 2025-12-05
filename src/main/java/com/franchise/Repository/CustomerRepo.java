package com.franchise.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    // Find customer by email (returns full customer object)
    Optional<Customer> findByEmail(String email);
    
    // Check if email exists
    boolean existsByEmail(String email);
    
 
    
    // Find active customers
    List<Customer> findByIsActive(boolean isActive);
    
      
    // Find customers by mobile number
    Optional<Customer> findByMobileNumber(String mobileNumber);
    
    // Find customers by tax number
    Optional<Customer> findByTaxNumber(String taxNumber);
    
    // Find customers by name (first or last name) containing the given string
    List<Customer> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    // Find customers by city
    List<Customer> findByCity(String city);
    
    // Find customers by country
    List<Customer> findByCountry(String country);
    
    // Find customers by postal code
    List<Customer> findByZipCode(String zipCode);
    
    // Custom query to check if a customer is active by email
    @Query("SELECT c.isActive FROM Customer c WHERE c.email = :email")
    Optional<Boolean> findIsActiveByEmail(String email);
}