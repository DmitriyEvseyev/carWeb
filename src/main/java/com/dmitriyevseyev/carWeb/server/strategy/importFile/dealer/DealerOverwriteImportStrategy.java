package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;

public class DealerOverwriteImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer) throws ImportExeption {
        CarDealership oldDealer = null;
        try {
            DealerController dealerController = DealerController.getInstance();
            oldDealer = dealerController.getDealer(dealer.getId());
            if (oldDealer != null) {
                dealerController.updateDealer(dealer);
            } else {
                dealerController.addDealer(dealer);
            }
        } catch (NotFoundException | UpdateDealerException | AddDealerExeption | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}