package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.PurchasePoOrder;
@Repository
public interface PurchasePoOrderRepo extends JpaRepository<PurchasePoOrder, Long> {
	

 

}