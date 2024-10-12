package com.wladischlau.app.pract3;

import com.wladischlau.app.pract3.task1.AlarmSystem;
import com.wladischlau.app.pract3.task1.sensors.CO2Sensor;
import com.wladischlau.app.pract3.task1.sensors.Sensor;
import com.wladischlau.app.pract3.task1.sensors.SensorValueType;
import com.wladischlau.app.pract3.task1.sensors.TemperatureSensor;
import com.wladischlau.app.test.TestConsoleOutput;
import com.wladischlau.app.test.TimingExtension;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(TimingExtension.class)
public class AlarmSystemTest {

    private TestConsoleOutput consoleOutput;

    private AlarmSystem alarmSystem;
    private Sensor temperatureSensor;
    private Sensor co2Sensor;

    @BeforeEach
    void setUp() {
        alarmSystem = new AlarmSystem();

        temperatureSensor = mock(TemperatureSensor.class);
        co2Sensor = mock(CO2Sensor.class);

        when(temperatureSensor.getValueType()).thenReturn(SensorValueType.TEMPERATURE);
        when(co2Sensor.getValueType()).thenReturn(SensorValueType.CO2);

        consoleOutput = new TestConsoleOutput();
        consoleOutput.start();
    }

    @AfterEach
    void tearDown() {
        consoleOutput.stop();
    }

    @Test
    void testTemperatureExceedsThreshold() {
        // Given
        when(temperatureSensor.getDataStream()).thenReturn(Observable.just(30));
        when(co2Sensor.getDataStream()).thenReturn(Observable.just(60));

        // When
        alarmSystem.subscribeToSensors(List.of(temperatureSensor, co2Sensor));

        // Then
        consoleOutput.assertOutputContains("Warning: TEMPERATURE");
    }

    @Test
    void testCO2ExceedsThreshold() {
        // Given
        when(temperatureSensor.getDataStream()).thenReturn(Observable.just(20));
        when(co2Sensor.getDataStream()).thenReturn(Observable.just(80));

        // When
        alarmSystem.subscribeToSensors(List.of(temperatureSensor, co2Sensor));

        // Then
        consoleOutput.assertOutputContains("Warning: CO2");
    }

    @Test
    void testBothExceedThreshold() {
        // Given
        when(temperatureSensor.getDataStream()).thenReturn(Observable.just(30));
        when(co2Sensor.getDataStream()).thenReturn(Observable.just(80));

        // When
        alarmSystem.subscribeToSensors(List.of(temperatureSensor, co2Sensor));

        // Then
        consoleOutput.assertOutputContains("ALARM! All indicator values exceeds the threshold");
    }
}
