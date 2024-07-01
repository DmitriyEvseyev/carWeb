package com.dmitriyevseyev.carWeb.server.strategy.export.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ConverterDTO;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;

import java.util.ArrayList;
import java.util.List;

public class CarExportStrategyWithDealer implements ExportStrategy {
    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
        ConverterDTO converterDTO = ConverterDTO.getInstance();
        List<Car> carList = new ArrayList<>();
        try {
            CarController carController = CarController.getInstance();
            carList = carController.getCars(ids);
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ExportExeption(e.getMessage());
        }

        exportList.addCars(converterDTO.convertCarToCarDTO(carList));

        List<CarDealership> dealerList = new ArrayList<>();
        try {
            DealerController controllerDealer = DealerController.getInstance();
            dealerList.add(controllerDealer.getDealer(carList.get(0).getDealer().getId()));
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ExportExeption(e.getMessage());
        }
        exportList.addDelers(converterDTO.convertDealerToDealerDTO(dealerList));


        System.out.println("CarExportStrategyWithDealer - " + exportList);

    }
}
