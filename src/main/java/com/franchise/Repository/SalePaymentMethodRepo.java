package com.franchise.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.SalePaymentMethod;
@Repository
public interface SalePaymentMethodRepo extends JpaRepository<SalePaymentMethod, Long> {

}
