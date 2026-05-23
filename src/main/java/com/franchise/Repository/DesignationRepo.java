package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Designation;

@Repository
public interface DesignationRepo extends JpaRepository<Designation, Long> {

}
