package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.Expenses;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

}
