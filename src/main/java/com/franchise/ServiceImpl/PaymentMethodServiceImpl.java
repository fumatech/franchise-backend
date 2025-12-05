package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.PaymentMethod;
import com.franchise.Repository.PaymentMethodRepo;
import com.franchise.Service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
	
	@Autowired
	private PaymentMethodRepo paymentMethodRepo;
	

	@Override
	public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		return paymentMethodRepo.save(paymentMethod);
	}

	@Override
	public List<PaymentMethod> getAllPaymentMethod() {
		// TODO Auto-generated method stub
		return paymentMethodRepo.findAll();
	}

	@Override
	public Optional<PaymentMethod> getPaymentMethod(Long id) {
		// TODO Auto-generated method stub
		return paymentMethodRepo.findById(id);
	}
	
	@Override
	public List<String> getAllActivePaymentMethodNames() {
	    return paymentMethodRepo.findAllActivePaymentMethodNames();
	}
	

	@Override
	public void updatePaymentMethodStatus(Long id, boolean isActive) {
	    Optional<PaymentMethod> paymentMethod = paymentMethodRepo.findById(id);
	    if (paymentMethod.isPresent()) {
	        PaymentMethod method = paymentMethod.get();
	        method.setIsActive(isActive);  // Directly set the isActive to the value sent from frontend (true or false)
	        paymentMethodRepo.save(method); // Save the updated payment method
	    }
	}



}
