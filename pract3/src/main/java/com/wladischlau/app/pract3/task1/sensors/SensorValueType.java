package com.wladischlau.app.pract3.task1.sensors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SensorValueType {
    
    TEMPERATURE(25),
    CO2(70);
    
    private final int threshold;
}
