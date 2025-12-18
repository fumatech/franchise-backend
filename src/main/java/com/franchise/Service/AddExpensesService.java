package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.AddExpenses;

public interface AddExpensesService {

	AddExpenses saveAddExpenses(AddExpenses addExpenses);

	AddExpenses updateAddExpenses(Long id, AddExpenses addExpenses);

	Optional<AddExpenses> getAddExpensesById(Long id);

	List<AddExpenses> getAllAddExpenses();

	void deleteAddExpenses(Long id);

	List<AddExpenses> getAllAddExpensesWithTax();

}
