package com.dmitriyevseyev.carWeb.server.strategy.importFile.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.CarIdAlreadyExistException;

public class CarConflictImportStrategy implements ImportStrategy<Car> {
    @Override
    public void store(Car car) {
        try {
            CarController carController = CarController.getInstance();
            carController.addCarWithId(car);
        } catch (CarIdAlreadyExistException e) {
            System.out.println(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (AddCarExeption | NotFoundException e) {
            System.out.println("CarConflictImportStrategy. " + e.getMessage());
        }
    }
}