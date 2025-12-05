package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Account;
import com.franchise.Repository.AccountRepo;
import com.franchise.Service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	
	@Autowired
	private   AccountRepo accountRepo;

	
	@Override
	public Account createAccount(Account account) {
 		return accountRepo.save(account);
	}

	@Override
	public Optional<Account> getAccountById(Long id) {
 		return accountRepo.findById(id);
	}

	@Override
	public List<Account> getAllAccounts() {
 		return accountRepo.findAll();
	}

	@Override
	public void deleteAccount(Long id) {
 		accountRepo.deleteById(id);
	}

	@Override
	public Account updateAccount(Long id, Account account) {
		if(accountRepo.existsById(id)) {
			account.setId(id);
            return accountRepo.save(account);
		} else {
			return null;
		}
		
 	
	}

}
