package com.dmitriyevseyev.carWeb.server.strategy.importFile.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.UpdateCarException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;

public class CarOverwriteImportStrategy implements ImportStrategy<Car> {
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
                try {
                    oldCar = carController.getCar(car.getId());
                    if (oldCar != null) {
                        carController.updateCar(car);
                    } else {
                        carController.addCar(car);
                    }
                } catch (AddCarExeption | UpdateCarException ex) {
                    throw new ImportExeption(ex.getMessage());
                }
            }
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}
