package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Vendor;
import com.franchise.Repository.VendorRepo;
import com.franchise.Service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepo vendorrepo;

	@Override
	public Vendor saveVendor(Vendor vendor) {
		// Save the vendor to the database
		return vendorrepo.save(vendor);
	}

	@Override
	public List<Vendor> getAllVendors() {
		// Retrieve all vendors from the database
		return vendorrepo.findAll();
	}

	@Override
	public Vendor updateVendor(Long vendorId, Vendor updatedVendor) {
		Optional<Vendor> existingVendorOptional = vendorrepo.findById(vendorId);

		if (existingVendorOptional.isPresent()) {
			Vendor existingVendor = existingVendorOptional.get();

			existingVendor.setPrefix(updatedVendor.getPrefix());
			existingVendor.setFirstname(updatedVendor.getFirstname());
			existingVendor.setLastname(updatedVendor.getLastname());
			existingVendor.setEmail(updatedVendor.getEmail());
			existingVendor.setVendorId(updatedVendor.getVendorId());
			existingVendor.setFirmName(updatedVendor.getFirmName());
			existingVendor.setTaxOrGstNumber(updatedVendor.getTaxOrGstNumber());
			existingVendor.setShopActNumber(updatedVendor.getShopActNumber());
			existingVendor.setCinNumber(updatedVendor.getCinNumber());
			existingVendor.setPanNumber(updatedVendor.getPanNumber());
			existingVendor.setIsActive(updatedVendor.getIsActive());
			existingVendor.setUsername(updatedVendor.getUsername());
			existingVendor.setPassword(updatedVendor.getPassword());
			existingVendor.setAllowLogin(updatedVendor.getAllowLogin());
			existingVendor.setLanguage(updatedVendor.getLanguage());
			existingVendor.setDateOfBirth(updatedVendor.getDateOfBirth());
			existingVendor.setGender(updatedVendor.getGender());
			existingVendor.setMaritalStatus(updatedVendor.getMaritalStatus());
			existingVendor.setBloodGroup(updatedVendor.getBloodGroup());
			existingVendor.setMobileNumber(updatedVendor.getMobileNumber());
			existingVendor.setAlternateContactNumber(updatedVendor.getAlternateContactNumber());
			existingVendor.setFamilyContactNumber(updatedVendor.getFamilyContactNumber());
			existingVendor.setFacebookLink(updatedVendor.getFacebookLink());
			existingVendor.setTwitterLink(updatedVendor.getTwitterLink());
			existingVendor.setSocialMedia1(updatedVendor.getSocialMedia1());
			existingVendor.setSocialMedia2(updatedVendor.getSocialMedia2());
			existingVendor.setCustomField1(updatedVendor.getCustomField1());
			existingVendor.setCustomField2(updatedVendor.getCustomField2());
			existingVendor.setCustomField3(updatedVendor.getCustomField3());
			existingVendor.setCustomField4(updatedVendor.getCustomField4());
			existingVendor.setGuardianName(updatedVendor.getGuardianName());
			existingVendor.setIdProofName(updatedVendor.getIdProofName());
			existingVendor.setIdProofNumber(updatedVendor.getIdProofNumber());
			existingVendor.setPermanentAddress(updatedVendor.getPermanentAddress());
			existingVendor.setCurrentAddress(updatedVendor.getCurrentAddress());
			existingVendor.setCountry(updatedVendor.getCountry());
			existingVendor.setState(updatedVendor.getState());
			existingVendor.setCity(updatedVendor.getCity());
			existingVendor.setZipCode(updatedVendor.getZipCode());
			existingVendor.setBankFields(updatedVendor.getBankFields());

			return vendorrepo.save(existingVendor);
		} else {
			return null;
		}
	}

	@Override
	public Vendor getVendorById(Long id) {
		// Retrieve a vendor by its ID
		Optional<Vendor> vendorOptional = vendorrepo.findById(id);
		return vendorOptional.orElse(null); // Return null if not found
	}

	@Override
	public void deleteVendorById(Long id) {
		// Delete the vendor from the database by ID
		vendorrepo.deleteById(id);
	}

	@Override
	public boolean isActiveUser(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Vendor> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	public boolean authenticate(String email, String password) {
		Optional<Vendor> userOpt = vendorrepo.findByEmail(email);
		return userOpt.isPresent() && userOpt.get().getPassword().equals(password) && userOpt.get().getIsActive();
	}

	@Override
	public Vendor getUserWithRolesAndPermissions(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getUserName(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> findFirmNameByEmail(String email) {
		// TODO Auto-generated method stub
		return vendorrepo.findFirmNameByEmail(email);
	}

}