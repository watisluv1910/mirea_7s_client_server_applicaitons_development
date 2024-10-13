package com.wladischlau.app.pract4_client.client;

import com.wladischlau.app.pract4.dto.Car;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
public non-sealed class CarClient extends BasicCrudClient<Car> {

    protected CarClient(RSocketRequester.Builder rSocketRequesterBuilder) {
        super(rSocketRequesterBuilder);
    }
}
