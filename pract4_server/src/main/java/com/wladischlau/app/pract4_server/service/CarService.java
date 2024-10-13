package com.wladischlau.app.pract4_server.service;

import com.wladischlau.app.pract4.dto.Car;
import com.wladischlau.app.pract4_server.repo.CarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Mono<Void> addCar(Car car) {
        return carRepository.save(car).then();
    }

    public Mono<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Flux<Car> getAllCars() {
        return carRepository.findAll();
    }
}
