package com.dmitriyevseyev.carWeb.shared.utils;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExportList {
    private List<CarDealership> dealers;
    private List<Car> cars;

    public ExportList() {
        this.dealers = new ArrayList<>();
        this.cars = new ArrayList<>();
    }

    public void setDealers(List<CarDealership> dealers) {
        this.dealers = dealers;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addDelers(List<CarDealership> dealers) {
        this.dealers.addAll(this.dealers.size(), dealers);
    }

    public void addCars(List<Car> cars) {
        this.cars.addAll(this.cars.size(), cars);
    }

    public List<CarDealership> getDealers() {
        return Collections.unmodifiableList(this.dealers);
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(this.cars);
    }


}