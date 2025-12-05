package com.franchise.Controller;

 
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.franchise.Entity.PaymentAccount;
import com.franchise.Entity.Transaction;
import com.franchise.Service.PaymentAccountService;
import com.franchise.ServiceImpl.PaymentAccountServiceImpl;

@RestController
@RequestMapping("/payment-account")
@CrossOrigin(
	    origins = {
	        "http://fusionmastertech.com",
	        "https://fusionmastertech.com",
	        "http://localhost:3000",
	        "http://localhost:3001"
	    },
	    allowCredentials = "true"	)
public class PaymentAccountController {
     
    @Autowired
    private PaymentAccountService paymentAccountService;
    
    @Autowired
    private PaymentAccountServiceImpl paymentAccountServiceImpl;
    

    @PostMapping("/save")
    public ResponseEntity<PaymentAccount> savePaymentAccount(@RequestBody PaymentAccount paymentAccount) {
        PaymentAccount payment = paymentAccountService.savePaymentAccount(paymentAccount);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    
    

    @PostMapping("/{accountId}")
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable Long accountId,
            @RequestBody Transaction transactionRequest) {

        try {
            Transaction savedTransaction = paymentAccountService.createTransaction(accountId, transactionRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    
    @GetMapping("/balance/{id}")
    public ResponseEntity<BigDecimal> getCurrentBalance(@PathVariable Long id) {
        BigDecimal balance = paymentAccountService.getCurrentBalance(id);

        return ResponseEntity.ok(balance);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PaymentAccount>> getAllPaymentAccount() {
        List<PaymentAccount> payment = paymentAccountService.getAllPaymentAccounts();
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentAccount> getPaymentAccountById(@PathVariable Long id) {
        Optional<PaymentAccount> payment = paymentAccountService.getPaymentAccountById(id);
        return payment.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentAccount> updatePaymentAccount(
            @PathVariable Long id, @RequestBody PaymentAccount paymentAccount) {
        PaymentAccount updatedAccount = paymentAccountService.updatePaymentAccount(id, paymentAccount);
        if (updatedAccount != null) {
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePaymentAccount(@PathVariable Long id) {
        paymentAccountService.deletePaymentAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    
    
    @PutMapping("/transaction/update/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long transactionId,
            @RequestBody Transaction transactionRequest) {
        
        try {
            Transaction updatedTransaction = paymentAccountService.updateTransaction(
                    transactionId,
                    transactionRequest.getAmount(),
                    transactionRequest.getPaymentMethod(),
                    transactionRequest.getTransactionType(),
                    transactionRequest.getAddedBy(),
                    transactionRequest.getNote(),
                    transactionRequest.getDate(),
                    transactionRequest.getVendor()
            );

            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long id, @RequestParam Long status) {
        try {
            paymentAccountService.updateAccountStatus(id, status);
            return ResponseEntity.ok("Account status updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment account not found");
        }
    }
    
    
    @GetMapping("/getbyvendor/{vendor}")
    public ResponseEntity<Map<String, List<Object>>> getPaymentByVendor(@PathVariable String vendor) {
        Map<String, List<Object>> orders = paymentAccountServiceImpl.getPaymentByVendor(vendor);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
               
}