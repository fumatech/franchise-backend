package com.franchise.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.TaxGroup;


@Repository
public interface TaxGroupRepo extends JpaRepository<TaxGroup, Long> {
    Optional<TaxGroup> findByName(String name);
}