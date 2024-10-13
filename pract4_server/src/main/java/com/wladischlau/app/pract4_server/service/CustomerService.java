package com.wladischlau.app.pract4_server.service;

import com.wladischlau.app.pract4.dto.Customer;
import com.wladischlau.app.pract4_server.repo.CustomerRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final DatabaseClient databaseClient;
    private final CustomerRepository customerRepository;

    public CustomerService(DatabaseClient databaseClient, CustomerRepository customerRepository) {
        this.databaseClient = databaseClient;
        this.customerRepository = customerRepository;
    }

    public Mono<Void> addCustomer(Customer customer) {
        return customerRepository.save(customer).then();
    }

    public Mono<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Flux<Customer> updateCustomers(Flux<Customer> toUpdateCustomers) {
        return toUpdateCustomers
                .flatMap(toUpdate -> customerRepository.findById(toUpdate.id())
                        .flatMap(existing -> {
                            Customer updated = existing.toBuilder()
                                    .name(toUpdate.name() != null ? toUpdate.name() : existing.name())
                                    .phone(toUpdate.phone() != null ? toUpdate.phone() : existing.phone())
                                    .build();

                            return customerRepository.save(updated);
                        })
                );
    }

    public Mono<Double> calculateDiscount(Long customerId) {
        return databaseClient.sql("SELECT calculate_discount(:id)")
                .bind("id", customerId)
                .map((row, _) -> row.get(0, Double.class))
                .one();
    }
}
