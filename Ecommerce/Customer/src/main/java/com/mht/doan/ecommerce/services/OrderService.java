package com.mht.doan.ecommerce.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mht.doan.ecommerce.models.CartItem;
import com.mht.doan.ecommerce.models.Customer;
import com.mht.doan.ecommerce.models.Order;
import com.mht.doan.ecommerce.models.OrderDetail;
import com.mht.doan.ecommerce.models.ShoppingCart;
import com.mht.doan.ecommerce.repositories.CustomerRepository;
import com.mht.doan.ecommerce.repositories.OrderDetailRepository;
import com.mht.doan.ecommerce.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartService cartService;

    @Transactional
    public Order save(ShoppingCart shoppingCart, String paymentMethod) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTax(2);
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");
        order.setQuantity(shoppingCart.getTotalItems());
        order.setPaymentMethod(paymentMethod);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            detailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        cartService.deleteCartById(shoppingCart.getId());
        return orderRepository.save(order);
    }

    public List<Order> findAll(String username) {
        Customer customer = customerRepository.findByUsername(username);
        List<Order> orders = customer.getOrders();
        return orders;
    }

    public List<Order> findALlOrders() {
        return orderRepository.findAll();
    }

    public Order acceptOrder(int id) {
        Order order = orderRepository.findById(id).get();
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        return orderRepository.save(order);
    }

    public void cancelOrder(int id) {
        orderRepository.deleteById(id);
    }
}
