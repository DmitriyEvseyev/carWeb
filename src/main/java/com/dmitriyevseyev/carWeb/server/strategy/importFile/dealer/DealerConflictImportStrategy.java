package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.DealerIdAlreadyExistException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerNameAlreadyExistException;

public class DealerConflictImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer) throws ImportExeption {
        DealerController dealerController;

        System.out.println("DealerConflictImportStrategy - " + dealer);


        try {
            dealerController = DealerController.getInstance();
            if (dealerController.getDealerByName(dealer.getName()) != null) {
                try {
                    throw new DealerNameAlreadyExistException("Dealer with this name, address already exist: name = " + dealer.getName());
                } catch (DealerNameAlreadyExistException e) {
                    throw new ImportExeption(e.getMessage());
                }
            } else if (dealerController.getDealer(dealer.getId()) != null) {
                try {
                    throw new DealerIdAlreadyExistException("Dealer with this id already exist: id = " + dealer.getId());
                } catch (DealerIdAlreadyExistException e) {
                    throw new ImportExeption(e.getMessage());
                }
            } else {
                try {
                    dealerController.addDealer(dealer);
                } catch (AddDealerExeption e) {
                    throw new ImportExeption(e.getMessage());
                }
            }
        } catch (NotFoundException | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}