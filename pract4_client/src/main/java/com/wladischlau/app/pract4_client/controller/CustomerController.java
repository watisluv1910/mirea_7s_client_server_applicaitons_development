package com.wladischlau.app.pract4_client.controller;

import com.wladischlau.app.pract4.dto.Customer;
import com.wladischlau.app.pract4_client.client.CustomerClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    private final CustomerClient customerClient;

    public CustomerController(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @GetMapping("/customers")
    public Mono<String> listCustomers(Model model) {
        Flux<Customer> customers = customerClient.getAll(Customer.class);
        return customers.collectList().flatMap(res -> {
            model.addAttribute("customers", res);
            return Mono.just("customers");
        });
    }

    @PostMapping("/customers")
    public Mono<String> addCustomer(String name, String phone) {
        Customer customer = Customer.builder().name(name).phone(phone).build();
        return customerClient.add(customer).then(Mono.just("redirect:/customers"));
    }
}
