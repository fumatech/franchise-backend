package com.franchise.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Vendor;


@Repository
public interface VendorRepo  extends JpaRepository<Vendor, Long>{

	Optional<Vendor> findByEmail(String email);
 
 
	
	@Query("SELECT v.firmName FROM Vendor v WHERE v.email = :email")
	Optional<String> findFirmNameByEmail(String email);


	
}
