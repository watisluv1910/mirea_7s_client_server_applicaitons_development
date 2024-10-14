package com.wladischlau.app.pract4_server.controller;

import com.wladischlau.app.pract4.dto.SaleOfferResponse;
import com.wladischlau.app.pract4.dto.SaleOffer;
import com.wladischlau.app.pract4_server.service.CarDealershipService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class CarDealershipController {

    private final CarDealershipService dealershipService;

    public CarDealershipController(CarDealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    // Channel

    @MessageMapping("placeOffers")
    public Flux<SaleOfferResponse> handlePriceOffers(Flux<SaleOffer> offers) {
        return offers.flatMap(dealershipService::handleSaleOffer);
    }
}
