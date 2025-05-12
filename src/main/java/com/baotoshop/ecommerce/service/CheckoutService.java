package com.baotoshop.ecommerce.service;

import com.baotoshop.ecommerce.dto.Purchase;
import com.baotoshop.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
