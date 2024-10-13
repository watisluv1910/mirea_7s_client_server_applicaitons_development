package com.wladischlau.app.pract4_server.service;

import com.wladischlau.app.pract4.dto.Car;
import com.wladischlau.app.pract4.dto.SaleOffer;
import com.wladischlau.app.pract4.dto.SaleOfferResponse;
import com.wladischlau.app.pract4_server.repo.CarRepository;
import org.springframework.stereotype.Service;

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

    public SaleOfferResponse handleSaleOffer(SaleOffer offer) {
        BigDecimal offeredPrice = offer.offeredPrice();

        BigDecimal discount = customerService.calculateDiscount(offer.clientId())
                .map(BigDecimal::valueOf)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Optional<BigDecimal> defaultPrice = carRepository.findById(offer.carId())
                .map(Car::price)
                .blockOptional();

        boolean isApproved = false; // Can't approve sale if default price isn't set
        if (defaultPrice.isPresent()) {
            isApproved = offeredPrice.compareTo(defaultPrice.get().multiply(discount)) > 0;
        }

        return new SaleOfferResponse(offer.carId(), isApproved);
    }
}
