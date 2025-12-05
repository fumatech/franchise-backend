package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

}
