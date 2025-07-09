package com.baotoshop.ecommerce.service;

import com.baotoshop.ecommerce.dao.CustomerRepository;
import com.baotoshop.ecommerce.dto.PaymentInfo;
import com.baotoshop.ecommerce.dto.Purchase;
import com.baotoshop.ecommerce.dto.PurchaseResponse;
import com.baotoshop.ecommerce.entity.Customer;
import com.baotoshop.ecommerce.entity.Order;
import com.baotoshop.ecommerce.entity.OrderItem;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.exceptions.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private CustomerRepository customerRepository;
    private final PayPalHttpClient payPalClient;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${paypal.client.id}") String clientId,
                               @Value("${paypal.client.secret}") String clientSecret,
                               @Value("${paypal.mode}") String mode) {
        this.customerRepository = customerRepository;
        // Khởi tạo PayPal SDK client
        PayPalEnvironment env;
        if ("live".equalsIgnoreCase(mode)) {
            env = new PayPalEnvironment.Live(clientId, clientSecret);
        } else {
            env = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        }
        this.payPalClient = new PayPalHttpClient(env);  // tạo client PayPal
    }

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer ...
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            // we found them ... let's assign them accordingly
            customer = customerFromDB;
        }

        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public com.paypal.orders.Order createPaymentIntent(PaymentInfo paymentInfo) throws IOException, HttpException {
        return null;
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}
