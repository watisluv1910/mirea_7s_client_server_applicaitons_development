package com.wladischlau.app.pract4_server.controller;

import com.wladischlau.app.pract4.dto.Car;
import com.wladischlau.app.pract4_server.service.CarService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Fire-and-Forget

    @MessageMapping("addCar")
    public Mono<Void> addCar(Car car) {
        return carService.addCar(car);
    }

    // Request-Response

    @MessageMapping("getCar")
    public Mono<Car> getCar(Long carId) {
        return carService.getCarById(carId);
    }

    // Request-Stream

    @MessageMapping("getAllCars")
    public Flux<Car> getAllCars() {
        return carService.getAllCars();
    }
}
