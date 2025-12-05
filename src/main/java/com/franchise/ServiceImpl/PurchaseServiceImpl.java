package com.franchise.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.PaymentMethod;
import com.franchise.Entity.Purchase;
import com.franchise.Repository.PaymentMethodRepo;
import com.franchise.Repository.PurchaseRepo;
import com.franchise.Service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepo purchaseRepository;

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    // Create a new purchase
    @Override
    public Purchase createPurchase(Purchase purchase) {
        // Ensure that PaymentMethod is saved before associating it with Purchase
        if (purchase.getPaymentMethod() != null && purchase.getPaymentMethod().getId() == null) {
            PaymentMethod savedPaymentMethod = paymentMethodRepo.save(purchase.getPaymentMethod());
            purchase.setPaymentMethod(savedPaymentMethod);
        }
        return purchaseRepository.save(purchase);
    }

    // Retrieve all purchases
    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // Retrieve a purchase by ID
    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    // Update an existing purchase
    @Override
    public Purchase updatePurchase(Long id, Purchase purchaseDetails) {
        Optional<Purchase> existingPurchase = purchaseRepository.findById(id);
        if (existingPurchase.isPresent()) {
            Purchase purchase = existingPurchase.get();
            
            // Ensure PaymentMethod is saved if it's new
            if (purchaseDetails.getPaymentMethod() != null && purchaseDetails.getPaymentMethod().getId() == null) {
                PaymentMethod savedPaymentMethod = paymentMethodRepo.save(purchaseDetails.getPaymentMethod());
                purchase.setPaymentMethod(savedPaymentMethod);
            } else {
                purchase.setPaymentMethod(purchaseDetails.getPaymentMethod());
            }

            // Update the other fields of the purchase
            purchase.setVendor(purchaseDetails.getVendor());
            purchase.setReferenceNumber(purchaseDetails.getReferenceNumber());
            purchase.setStatus(purchaseDetails.getStatus());
            purchase.setAddedBy(purchaseDetails.getAddedBy());
            purchase.setPurchaseDate(purchaseDetails.getPurchaseDate());
            purchase.setLocation(purchaseDetails.getLocation());
            purchase.setFile(purchaseDetails.getFile());
            purchase.setDiscountType(purchaseDetails.getDiscountType());
            purchase.setDiscountAmount(purchaseDetails.getDiscountAmount());
            purchase.setPurchaseTax(purchaseDetails.getPurchaseTax());
            purchase.setTaxAmount(purchaseDetails.getTaxAmount());
            purchase.setAdditionalNotes(purchaseDetails.getAdditionalNotes());
            purchase.setShippingDetails(purchaseDetails.getShippingDetails());
            purchase.setShippingCharges(purchaseDetails.getShippingCharges());

            return purchaseRepository.save(purchase);
        } else {
            throw new RuntimeException("Purchase not found with id " + id);
        }
    }

    // Delete a purchase by ID
    @Override
    public void deletePurchase(Long id) {
        if (purchaseRepository.existsById(id)) {
            purchaseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Purchase not found with id " + id);
        }
    }
}
