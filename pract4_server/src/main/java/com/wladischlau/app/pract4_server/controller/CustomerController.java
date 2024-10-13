package com.wladischlau.app.pract4_server.controller;

import com.wladischlau.app.pract4.dto.Customer;
import com.wladischlau.app.pract4_server.service.CustomerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Fire-and-Forget

    @MessageMapping("addCustomer")
    public Mono<Void> addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    // Request-Response

    @MessageMapping("getCustomer")
    public Mono<Customer> getCustomerById(Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    // Request-Stream

    @MessageMapping("getAllCustomers")
    public Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Channel

    @MessageMapping("updateCustomer")
    public Flux<Customer> updateCustomers(Flux<Customer> customers) {
        return customerService.updateCustomers(customers);
    }
}
