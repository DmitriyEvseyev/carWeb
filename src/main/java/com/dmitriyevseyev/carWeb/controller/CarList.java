package com.dmitriyevseyev.carWeb.controller;

import com.dmitriyevseyev.carWeb.model.Car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class CarList {
    private static CarList instance;
    private List<Car> carL;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


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
        try {
            car1 = Car.builder()
                    .id(1)
                    .idDealer(1)
                    .name("BMW")
                    .date(formatter.parse("2023-12-17"))
                    .color("white")
                    .isAfterCrash(false)
                    .build();

            car2 = Car.builder()
                    .id(2)
                    .idDealer(2)
                    .name("Lexus")
                    .date(formatter.parse("2023-10-15"))
                    .color("white")
                    .isAfterCrash(false)
                    .build();
        } catch (ParseException e) {
            System.out.println("CarList, ParseException. " + e.getMessage());
        }

        carL.add(car1);
        carL.add(car2);
        System.out.println("carList from CarList - " + carL);
    }

    public List<Car> getCarL() {
        return carL;
    }

    public void setCarL(List<Car> carL) {
        this.carL = carL;
    }
}
