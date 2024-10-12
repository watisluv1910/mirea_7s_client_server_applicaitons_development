package com.wladischlau.app.pract3.task1.sensors;

import io.reactivex.rxjava3.core.Observable;

public interface Sensor {
    
    SensorValueType getValueType();
    Observable<Integer> getDataStream();
}
