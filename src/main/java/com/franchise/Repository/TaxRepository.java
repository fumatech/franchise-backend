package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}

