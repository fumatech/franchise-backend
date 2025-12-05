package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Expenses;
import com.franchise.Repository.ExpensesRepository;
import com.franchise.Service.ExpensesService;

@Service
public class ExpensesServiceImpl implements ExpensesService {

	@Autowired
	private ExpensesRepository expensesRepository;

	@Override
	public Expenses saveExpense(Expenses expense) {
		return expensesRepository.save(expense);
	}

	@Override
	public List<Expenses> getAllExpenses() {
		return expensesRepository.findAll();
	}

	@Override
	public Expenses getExpenseById(Long id) {
		return expensesRepository.findById(id).orElse(null);
	}

	@Override
	public Expenses updateExpense(Long id, Expenses expenseDetails) {
		Optional<Expenses> existingExpenseOpt = expensesRepository.findById(id);

		if (existingExpenseOpt.isPresent()) {
			Expenses existingExpense = existingExpenseOpt.get();

			existingExpense.setExpenseName(expenseDetails.getExpenseName());
			existingExpense.setExpenseCode(expenseDetails.getExpenseCode());
			existingExpense.setDescription(expenseDetails.getDescription());
			existingExpense.setParentExpense(expenseDetails.getParentExpense());

			return expensesRepository.save(existingExpense);
		}

		return null;
	}

	@Override
	public void deleteExpenseById(Long id) {
		expensesRepository.deleteById(id);
	}
}
