package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.Customer;



public interface CustomerService {
	
	Customer saveCustomer(Customer customer);

	List<Customer> getAllCustomers();

	Customer updateCustomer(Long CustomerId, Customer updatedCustomer);

	Customer getCustomerById(Long id);

	void deleteCustomerById(Long id);
	
	boolean isActiveUser(String email);
	    
	Optional<String> getUserName(String email);
	    
	Optional<String> findCustomerByEmail(String email);

}
