package com.franchise.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.Entity.Account;
import com.franchise.Service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 

 
@RestController
@RequestMapping("/account")
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class AccountController {

    private final AccountService accountService;
	
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
	@PostMapping("/save")
	public ResponseEntity<Account>createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
		return ResponseEntity.ok(createdAccount);
	}
	
	   @GetMapping("get/{id}")
	    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
	        Optional<Account> account = accountService.getAccountById(id);
	        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	
	 

	    // Update account
		@PutMapping("update/{id}")
	    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
	        Account updatedAccount = accountService.updateAccount(id, account);
	        return updatedAccount != null ? ResponseEntity.ok(updatedAccount) : ResponseEntity.notFound().build();
	    } 
	   
	   
	   // Get all accounts
		@GetMapping("/getall")
	    public ResponseEntity<List<Account>> getAllAccounts() {
	        List<Account> accounts = accountService.getAllAccounts();
	        return ResponseEntity.ok(accounts);
	    }


	    // Delete account
		@DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
	        accountService.deleteAccount(id);
	        return ResponseEntity.noContent().build();
	    }
}
