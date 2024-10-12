package com.wladischlau.app.pract3.task1;

import com.wladischlau.app.pract3.task1.sensors.Sensor;
import com.wladischlau.app.pract3.task1.sensors.SensorValueType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmSystem {
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void subscribeToSensors(List<Sensor> sensors) {
        Map<SensorValueType, Integer> sensorsData = new HashMap<>();
        
        sensors.forEach(sensor ->
            sensor.getDataStream().subscribe(value -> {
                sensorsData.put(sensor.getValueType(), value);
                checkAlarmConditions(sensorsData);
            })
        );
    }
    
    private void checkAlarmConditions(Map<SensorValueType, Integer> sensorsData) {
        int limitsCounter = 0;
        
        for (Map.Entry<SensorValueType, Integer> entry : sensorsData.entrySet()) {
            SensorValueType type = entry.getKey();
            int value = entry.getValue();
            if (value > type.getThreshold()) {
                limitsCounter++;
                System.out.println("Warning: " + type +
                        " indicator value: " + value +
                        " exceeds the threshold: " + type.getThreshold());
            }
        }
        
        if (limitsCounter == sensorsData.size()) {
            System.out.println("ALARM! All indicator values exceeds the threshold");
        }
    }
}
