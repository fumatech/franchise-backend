package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

}
