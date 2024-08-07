package com.dmitriyevseyev.carWeb.server.strategy.importFile.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;

public class CarIgnoreImportStrategy implements ImportStrategy<Car> {
    @Override
    public void store(Car car) throws ImportExeption {
        DealerController dealerController;
        CarController carController = null;
        try {
            dealerController = DealerController.getInstance();
            carController = CarController.getInstance();
        } catch (DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
        CarDealership dealer = null;
        Car oldCar = null;
        try {
            if (dealerController.getDealer(car.getIdDealer()) == null) {
                throw new NotFoundException("The dealer was not found with id = " + car.getIdDealer() + "! "
                        + StrategyConstants.IMPORT_EXCEPTION_MESSAGE);
            } else {
                oldCar = carController.getCar(car.getId());
                if (oldCar == null) {
                    carController.addCar(car);
                }
            }
        } catch (NotFoundException | AddCarExeption e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}
