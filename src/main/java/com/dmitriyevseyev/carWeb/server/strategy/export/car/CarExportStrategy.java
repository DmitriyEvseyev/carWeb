package com.dmitriyevseyev.carWeb.server.strategy.export.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ExportList;

import java.util.ArrayList;
import java.util.List;

public class CarExportStrategy implements ExportStrategy {
    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) {
        CarController carController = CarController.getInstance();
        List<Car> carList = new ArrayList<>();
        try {
            carList = carController.getCars(ids);

            System.out.println(carList);

        } catch (NotFoundException e) {
            System.out.println("DealerExportStrategyWithCar, NotFoundException. " + e.getMessage());
        }
        exportList.addCars(carList);
    }
}
