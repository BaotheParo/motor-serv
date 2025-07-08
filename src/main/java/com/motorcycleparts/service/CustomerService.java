package com.motorcycleparts.service;

import com.motorcycleparts.entity.Customer;
import com.motorcycleparts.repository.CustomerRepository;
import com.motorcycleparts.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer ID does not exist");
        }
        if (!orderRepository.findByCustomerId(id).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete customer with existing orders");
        }
        customerRepository.deleteById(id);
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }
}