package com.wladischlau.app.pract4_server.service;

import com.wladischlau.app.pract4.dto.Car;
import com.wladischlau.app.pract4.dto.SaleOffer;
import com.wladischlau.app.pract4.dto.SaleOfferResponse;
import com.wladischlau.app.pract4_server.repo.CarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarDealershipService {

    private final CustomerService customerService;
    private final CarRepository carRepository;

    public CarDealershipService(CustomerService customerService, CarRepository carRepository) {
        this.customerService = customerService;
        this.carRepository = carRepository;
    }

    public Mono<SaleOfferResponse> handleSaleOffer(SaleOffer offer) {
        Mono<BigDecimal> discountMono = customerService.calculateDiscount(offer.customerId())
                .map(BigDecimal::valueOf)
                .switchIfEmpty(Mono.error(new RuntimeException("Client not found")));

        Mono<Optional<BigDecimal>> priceMono = carRepository.findById(offer.carId())
                .map(Car::price)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty());

        return Mono.zip(discountMono, priceMono)
                .flatMap(tuple -> {
                    BigDecimal discount = tuple.getT1();
                    Optional<BigDecimal> defaultPrice = tuple.getT2();

                    boolean isApproved = false;
                    if (defaultPrice.isPresent()) {
                        BigDecimal offeredPrice = offer.offeredPrice();
                        BigDecimal calculatedPrice = defaultPrice.get().multiply(BigDecimal.ONE.subtract(discount));
                        isApproved = offeredPrice.compareTo(calculatedPrice) >= 0;
                    }

                    return Mono.just(new SaleOfferResponse(offer.carId(), isApproved));
                });
    }
}
