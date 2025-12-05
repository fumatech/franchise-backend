package com.franchise.Service;


import java.util.List;
import java.util.Optional;

import com.franchise.Entity.Purchase;

public interface PurchaseService {

    // Create a new purchase
    Purchase createPurchase(Purchase purchase);

    // Retrieve all purchases
    List<Purchase> getAllPurchases();

    // Retrieve a purchase by ID
    Optional<Purchase> getPurchaseById(Long id);

    // Update an existing purchase
    Purchase updatePurchase(Long id, Purchase purchase);

    // Delete a purchase by ID
    void deletePurchase(Long id);
}
