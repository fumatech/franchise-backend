package com.franchise.Repository;

import com.franchise.Entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRateRepo extends JpaRepository<TaxRate, Long> {
}
