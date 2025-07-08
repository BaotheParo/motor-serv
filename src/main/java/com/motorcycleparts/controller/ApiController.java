package com.motorcycleparts.controller;

import com.motorcycleparts.entity.Customer;
import com.motorcycleparts.entity.Order;
import com.motorcycleparts.entity.Part;
import com.motorcycleparts.service.CustomerService;
import com.motorcycleparts.service.OrderService;
import com.motorcycleparts.service.PartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PartService partService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        if (!customerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customer.setId(id); // Gán id để đảm bảo cập nhật đúng bản ghi
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/parts")
    public List<Part> getAllParts() {
        return partService.getAllParts(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }

    @GetMapping("/parts/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        Optional<Part> part = partService.getPartById(id);
        return part.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/parts")
    public Part createPart(@Valid @RequestBody Part part) {
        return partService.savePart(part);
    }

    @PutMapping("/parts/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @Valid @RequestBody Part part) {
        if (!partService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        part.setId(id); // Gán id để đảm bảo cập nhật đúng bản ghi
        return ResponseEntity.ok(partService.savePart(part));
    }

    @DeleteMapping("/parts/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        try {
            partService.deletePart(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.saveOrder(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        if (!orderService.getOrderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            orderService.deleteOrder(id); // Hoàn lại stock trước khi cập nhật
            order.setId(id); // Gán id để đảm bảo cập nhật đúng bản ghi
            return ResponseEntity.ok(orderService.saveOrder(order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}