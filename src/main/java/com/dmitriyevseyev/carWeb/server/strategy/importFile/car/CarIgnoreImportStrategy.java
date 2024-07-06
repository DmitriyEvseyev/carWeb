package com.dmitriyevseyev.carWeb.server.strategy.importFile.car;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.model.dto.CarDTO;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.AddCarExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ConverterDTO;

public class CarIgnoreImportStrategy implements ImportStrategy<CarDTO> {
    @Override
    public void store(CarDTO carDTO) throws ImportExeption {
        ConverterDTO converterDTO = ConverterDTO.getInstance();
        Car car = converterDTO.converterCarDTOToCar(carDTO);
        CarDealership dealer = null;

        try {
            DealerController dealerController = DealerController.getInstance();
            CarController carController = CarController.getInstance();
            dealer = dealerController.getDealer(carDTO.getIdDealer());
            Car oldCar = null;
            car.setDealer(dealer);
            oldCar = carController.getCar(car.getId());
            if (oldCar == null) {
                try {
                    carController.addCar(car);
                } catch (AddCarExeption ex) {
                    throw new ImportExeption(ex.getMessage());
                }
            }
        } catch (DAOFactoryActionException | NotFoundException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}
