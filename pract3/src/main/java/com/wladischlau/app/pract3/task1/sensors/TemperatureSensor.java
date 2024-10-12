package com.wladischlau.app.pract3.task1.sensors;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TemperatureSensor implements Sensor {
    
    private final Random random = new Random();
    
    @Override
    public SensorValueType getValueType() {
        return SensorValueType.TEMPERATURE;
    }
    
    @Override
    public Observable<Integer> getDataStream() {
        return Observable
                .interval(1, TimeUnit.SECONDS)
                .map(_ -> 15 + random.nextInt(16));
    }
}
