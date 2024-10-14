package com.wladischlau.app.pract4_client.controller;

import com.wladischlau.app.pract4.dto.Car;
import com.wladischlau.app.pract4_client.client.CarClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CarController {

    private final CarClient carClient;

    public CarController(CarClient carClient) {
        this.carClient = carClient;
    }

    @GetMapping("/cars")
    public Mono<String> listCars(Model model) {
        Flux<Car> cars = carClient.getAll(Car.class);
        return cars.collectList().flatMap(res -> {
            model.addAttribute("cars", res);
            return Mono.just("cars");
        });
    }

    @PostMapping("/cars")
    public Mono<String> addCar(Car car) {
        return carClient.add(car).then(Mono.just("redirect:/cars"));
    }
}
