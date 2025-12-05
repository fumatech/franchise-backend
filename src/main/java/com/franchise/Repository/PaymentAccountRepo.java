package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.PaymentAccount;

@Repository
public interface PaymentAccountRepo extends JpaRepository<PaymentAccount, Long> {

}
