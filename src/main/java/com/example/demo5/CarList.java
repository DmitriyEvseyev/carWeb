package com.example.demo5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarList {
    private static CarList instance;
    private List<Car> carL;

    public static CarList getInstance() {
        if (instance == null) {
            instance = new CarList();
        }
        return instance;
    }
    public CarList() {
        carL = new ArrayList<>();
        Car car1 = null;
        Car car2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);

        try {
            car1 = Car.builder()
                    .id(1)
                    .name("BMW")
                    .date(formatter.parse("2022-01-01"))
                    .color("white")
                    .isAfterCrash(false)
                    .build();
            car2 = Car.builder()
                    .id(2)
                    .name("Lexus")
                    .date(formatter.parse("2021-01-01"))
                    .color("white")
                    .isAfterCrash(false)
                    .build();
        } catch (ParseException e) {
            System.out.println("Car.builder. " + e.getMessage());
        }
        carL.add(car1);
        carL.add(car2);
        System.out.println("carList - " + carL);
    }

    public List<Car> getCarL() {
        return carL;
    }

    public void setCarL(List<Car> carL) {
        this.carL = carL;
    }
}
