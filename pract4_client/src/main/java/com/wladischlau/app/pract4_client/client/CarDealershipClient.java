package com.wladischlau.app.pract4_client.client;

import com.wladischlau.app.pract4.dto.SaleOffer;
import com.wladischlau.app.pract4.dto.SaleOfferResponse;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CarDealershipClient {

    private final RSocketRequester rSocketRequester;

    public CarDealershipClient(RSocketRequester.Builder rSocketRequesterBuilder) {
        this.rSocketRequester = rSocketRequesterBuilder.tcp("localhost", 7000);
    }

    public Flux<SaleOfferResponse> sendSaleOffers(Flux<SaleOffer> offers) {
        return rSocketRequester.route("placeOffers")
                .data(offers)
                .retrieveFlux(SaleOfferResponse.class);
    }
}