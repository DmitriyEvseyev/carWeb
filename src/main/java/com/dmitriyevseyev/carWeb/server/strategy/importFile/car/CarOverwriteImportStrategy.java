package com.dmitriyevseyev.carWeb.server.strategy.importFile.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.model.dto.CarDTO;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.UpdateCarException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ConverterDTO;

public class CarOverwriteImportStrategy implements ImportStrategy<CarDTO> {
    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
        ConverterDTO converterDTO = ConverterDTO.getInstance();
        Car car = converterDTO.converterCarDTOToCar(carDTO);
        CarDealership dealer = null;
        Car oldCar = null;
        try {
            DealerController dealerController = DealerController.getInstance();
            CarController carController = CarController.getInstance();
            dealer = dealerController.getDealer(carDTO.getIdDealer());
            car.setDealer(dealer);
            oldCar = carController.getCar(car.getId());
            if (oldCar != null) {
                carController.updateCar(car);
            } else {
                carController.addCar(car);
            }
        } catch (AddCarExeption | UpdateCarException | NotFoundException | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}
