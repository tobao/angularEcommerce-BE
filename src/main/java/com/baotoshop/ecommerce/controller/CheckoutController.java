package com.baotoshop.ecommerce.controller;

import com.baotoshop.ecommerce.dto.PaymentInfo;
import com.baotoshop.ecommerce.dto.Purchase;
import com.baotoshop.ecommerce.dto.PurchaseResponse;
import com.baotoshop.ecommerce.service.CheckoutService;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<Order> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)
            throws IOException, HttpException {
        // 1. Tạo "PaymentIntent" (thực chất là Order của PayPal)
        Order order = checkoutService.createPaymentIntent(paymentInfo);

        // 2. (Tùy chọn) Bạn có thể trả thẳng toàn bộ Order object
         return ResponseEntity.ok(order);

        // 3. Hoặc chỉ trả về những gì frontend cần: orderID + link approve
//        String orderId = order.id();
//        String approveLink = order.links().stream()
//                .filter(l -> "approve".equals(l.rel()))
//                .findFirst()
//                .map(l -> l.href())
//                .orElseThrow(() -> new IllegalStateException("No approval link"));
//
//        Map<String,String> resp = new HashMap<>();
//        resp.put("orderID", orderId);
//        resp.put("approveUrl", approveLink);
//
//        return ResponseEntity.ok(resp);
        //        Fix -> public ResponseEntity<Map<String, String>> createPaymentIntent(....) throws IOException, HttpException {}

    }
}
