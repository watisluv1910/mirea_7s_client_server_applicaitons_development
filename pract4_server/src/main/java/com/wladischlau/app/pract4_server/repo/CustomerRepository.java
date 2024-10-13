package com.wladischlau.app.pract4_server.repo;

import com.wladischlau.app.pract4.dto.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
}
