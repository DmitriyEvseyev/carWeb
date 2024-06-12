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
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.CarIdAlreadyExistException;

public class CarConflictImportStrategy implements ImportStrategy<Car> {
    @Override
    public void store(Car car) throws ImportExeption {
        CarDealership dealer = null;
        Car oldCar = null;
        try {
            DealerController dealerController = DealerController.getInstance();
            CarController carController = CarController.getInstance();
            if (dealerController.getDealer(car.getIdDealer()) == null) {
                throw new NotFoundException("The dealer was not found with id = " + car.getIdDealer() + "! "
                        + StrategyConstants.IMPORT_EXCEPTION_MESSAGE);
            } else {
                oldCar = carController.getCar(car.getId());
                if (oldCar != null) {
                    try {
                        throw new CarIdAlreadyExistException ("Car with this id already exist: id = " + car.getId());
                    } catch (CarIdAlreadyExistException e) {
                        throw new ImportExeption(e.getMessage());
                    }
                } else {
                    try {
                        carController.addCar(car);
                    } catch (AddCarExeption ex) {
                        throw new ImportExeption(ex.getMessage());
                    }
                }
            }
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}