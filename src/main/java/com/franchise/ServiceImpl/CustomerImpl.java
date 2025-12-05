package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Customer;
import com.franchise.Repository.CustomerRepo;
import com.franchise.Service.CustomerService;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerrepo;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerrepo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerrepo.findAll();
    }

    @Override
    public Customer updateCustomer(Long CustomerId, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerrepo.findById(CustomerId);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();

            // Basic Information
            existingCustomer.setPrefix(updatedCustomer.getPrefix());
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setActive(updatedCustomer.isActive());
            existingCustomer.setTaxNumber(updatedCustomer.getTaxNumber());
            existingCustomer.setOpeningBalance(updatedCustomer.getOpeningBalance());
            existingCustomer.setPayTerm(updatedCustomer.getPayTerm());
            existingCustomer.setPayTermType(updatedCustomer.getPayTermType());
            existingCustomer.setCreditLimit(updatedCustomer.getCreditLimit());

            // Personal Information
            existingCustomer.setLanguage(updatedCustomer.getLanguage());
            existingCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
            existingCustomer.setGender(updatedCustomer.getGender());
            existingCustomer.setMaritalStatus(updatedCustomer.getMaritalStatus());
            existingCustomer.setBloodGroup(updatedCustomer.getBloodGroup());
            existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber());
            existingCustomer.setAlternateContactNumber(updatedCustomer.getAlternateContactNumber());

            // Custom Fields and Identification
            existingCustomer.setCustomField1(updatedCustomer.getCustomField1());
            existingCustomer.setCustomField2(updatedCustomer.getCustomField2());
            existingCustomer.setCustomField3(updatedCustomer.getCustomField3());
            existingCustomer.setCustomField4(updatedCustomer.getCustomField4());
            existingCustomer.setIdProofName(updatedCustomer.getIdProofName());
            existingCustomer.setIdProofNumber(updatedCustomer.getIdProofNumber());

            // Address Information
            existingCustomer.setCountry(updatedCustomer.getCountry());
            existingCustomer.setState(updatedCustomer.getState());
            existingCustomer.setCity(updatedCustomer.getCity());
            existingCustomer.setZipCode(updatedCustomer.getZipCode());
            existingCustomer.setLandmark(updatedCustomer.getLandmark());
            existingCustomer.setStreetName(updatedCustomer.getStreetName());
            existingCustomer.setBuildingNumber(updatedCustomer.getBuildingNumber());

            // Additional fields
            existingCustomer.setOccupation(updatedCustomer.getOccupation());
          

            return customerrepo.save(existingCustomer);
        } else {
            return null;
        }
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customers = customerrepo.findById(id);
        return customers.orElse(null);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerrepo.deleteById(id);
    }

    @Override
    public boolean isActiveUser(String email) {
        Optional<Customer> customer = customerrepo.findByEmail(email);
        return customer.map(Customer::isActive).orElse(false);
    }

    @Override
    public Optional<String> getUserName(String email) {
        return customerrepo.findByEmail(email)
                .map(customer -> customer.getFirstName() + " " + customer.getLastName());
    }

    @Override
    public Optional<String> findCustomerByEmail(String email) {
        return customerrepo.findByEmail(email)
                .map(Customer::getEmail);
    }
}