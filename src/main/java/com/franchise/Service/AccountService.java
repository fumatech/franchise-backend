package com.franchise.Service;

import java.util.List;
import java.util.Optional;

import com.franchise.Entity.Account;
 
public interface AccountService {
	Account createAccount(Account account);
	
	Optional<Account> getAccountById(Long id);
	
	List<Account> getAllAccounts();
	
	void deleteAccount(Long id);
	
	Account updateAccount(Long id , Account account);                          
}
