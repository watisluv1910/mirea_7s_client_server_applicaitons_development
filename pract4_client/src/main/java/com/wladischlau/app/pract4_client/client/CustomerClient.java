package com.wladischlau.app.pract4_client.client;

import com.wladischlau.app.pract4.dto.Customer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
public non-sealed class CustomerClient extends BasicCrudClient<Customer> {

    protected CustomerClient(RSocketRequester.Builder rSocketRequesterBuilder) {
        super(rSocketRequesterBuilder);
    }
}
