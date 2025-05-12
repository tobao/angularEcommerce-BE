package com.baotoshop.ecommerce.dto;

import com.baotoshop.ecommerce.entity.Address;
import com.baotoshop.ecommerce.entity.Customer;
import com.baotoshop.ecommerce.entity.Order;
import com.baotoshop.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
