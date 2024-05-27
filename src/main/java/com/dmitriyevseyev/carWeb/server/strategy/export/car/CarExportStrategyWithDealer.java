package com.dmitriyevseyev.carWeb.server.strategy.export.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;

import java.util.ArrayList;
import java.util.List;

public class CarExportStrategyWithDealer implements ExportStrategy {
    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
        CarController carController = CarController.getInstance();
        List<Car> carList = new ArrayList<>();
        try {
            carList = carController.getCars(ids);
        } catch (NotFoundException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        exportList.addCars(carList);

        DealerController controllerDealer = DealerController.getInstance();
        List<CarDealership> dealerList = new ArrayList<>();
        try {
            dealerList.add(controllerDealer.getDealer(carList.get(0).getIdDealer()));
        } catch (NotFoundException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        exportList.addDelers(dealerList);



        System.out.println("CarExportStrategyWithDealer - " + exportList);


    }
}
