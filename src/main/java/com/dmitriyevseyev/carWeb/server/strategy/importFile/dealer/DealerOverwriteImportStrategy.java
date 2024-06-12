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
        try {
            DealerController dealerController = DealerController.getInstance();

            if (dealerController.getDealerByName(dealer.getName()) != null ||
                    dealerController.getDealer(dealer.getId()) != null) {
                dealerController.updateDealer(dealer.getId(), dealer.getName(), dealer.getAddress());

            } else if (dealerController.getDealerByName(dealer.getName()) == null &&
                    dealerController.getDealer(dealer.getId()) == null) {
                dealerController.addDealer(dealer);
            }
        } catch (NotFoundException | UpdateDealerException | AddDealerExeption | DAOFactoryActionException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}