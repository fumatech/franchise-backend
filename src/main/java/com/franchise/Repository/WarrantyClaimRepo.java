package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Entity.WarrantyClaim;

public interface WarrantyClaimRepo extends JpaRepository<WarrantyClaim, Long> {

}
