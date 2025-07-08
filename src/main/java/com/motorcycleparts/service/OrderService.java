package com.motorcycleparts.service;

import com.motorcycleparts.entity.Order;
import com.motorcycleparts.entity.Part;
import com.motorcycleparts.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PartService partService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        if (!customerService.existsById(order.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID does not exist");
        }
        Part part = partService.getPartById(order.getPartId())
                .orElseThrow(() -> new IllegalArgumentException("Part ID does not exist"));
        if (part.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for part");
        }
        order.setTotalPrice(part.getPrice() * order.getQuantity());
        part.setStock(part.getStock() - order.getQuantity());
        partService.savePart(part);
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order ID does not exist"));
        Part part = partService.getPartById(order.getPartId())
                .orElseThrow(() -> new IllegalArgumentException("Part ID does not exist"));
        part.setStock(part.getStock() + order.getQuantity());
        partService.savePart(part);
        orderRepository.deleteById(id);
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Double getTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public Map<Long, Double> getRevenueByCustomer() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomerId,
                        Collectors.summingDouble(Order::getTotalPrice)
                ));
    }

    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }
}