package com.wladischlau.app.pract4_client.controller;

import com.wladischlau.app.pract4.dto.SaleOffer;
import com.wladischlau.app.pract4.dto.SaleOfferResponse;
import com.wladischlau.app.pract4_client.client.CarDealershipClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CarDealershipController {

    private final CarDealershipClient carDealershipClient;

    public CarDealershipController(CarDealershipClient carDealershipClient) {
        this.carDealershipClient = carDealershipClient;
    }

    @GetMapping({"/", "/sale-offers"})
    public Mono<String> saleOffers(Model model) {
        model.addAttribute("responses", List.of());
        return Mono.just("sale-offers");
    }

    @PostMapping("/submit-sale-offers")
    public Mono<String> submitSaleOffer(SaleOffer saleOffer, Model model) {
        Flux<SaleOfferResponse> responses = carDealershipClient.sendSaleOffers(Flux.just(saleOffer));
        return responses.collectList().flatMap(saleOfferResponses -> {
            model.addAttribute("responses", saleOfferResponses);
            return Mono.just("sale-offers"); // Return the view after the responses are available
        });
    }
}