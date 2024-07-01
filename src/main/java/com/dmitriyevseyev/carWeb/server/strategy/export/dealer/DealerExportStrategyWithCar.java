package com.dmitriyevseyev.carWeb.server.strategy.export.dealer;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.GetAllCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ConverterDTO;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;

import java.util.ArrayList;
import java.util.List;

public class DealerExportStrategyWithCar implements ExportStrategy {
    @Override
    public void collectExportIds(ExportDTO exportList, List<Integer> ids) throws ExportExeption {
        ConverterDTO converterDTO = ConverterDTO.getInstance();
        DealerController controllerDealer ;
        List<CarDealership> dealerList = new ArrayList<>();
        try {
            controllerDealer = DealerController.getInstance();
            dealerList = controllerDealer.getDealers(ids);
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        exportList.addDelers(converterDTO.convertDealerToDealerDTO(dealerList));

        CarController carController;
        List<Car> carList = new ArrayList<>();
        try {
            carController = CarController.getInstance();
            carList = carController.getCarsByDealersIds(ids);
        } catch (GetAllCarExeption | DAOFactoryActionException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        exportList.addCars(converterDTO.convertCarToCarDTO(carList));


        System.out.println("DealerExportStrategyWithCar - " + exportList);
    }
}
