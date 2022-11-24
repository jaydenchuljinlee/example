package com.headfirst.designpattern.observer;

import com.headfirst.designpattern.observer.display.CurrentConditionsDisplay;
import com.headfirst.designpattern.observer.subject.WeatherData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherStationTest {
    @Test
    void 옵저버_메인_테스트() {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
