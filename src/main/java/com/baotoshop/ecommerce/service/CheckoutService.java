package com.baotoshop.ecommerce.service;

import com.baotoshop.ecommerce.dto.PaymentInfo;
import com.baotoshop.ecommerce.dto.Purchase;
import com.baotoshop.ecommerce.dto.PurchaseResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.Order;

import java.io.IOException;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);

    Order createPaymentIntent(PaymentInfo paymentInfo) throws IOException, HttpException;
}
