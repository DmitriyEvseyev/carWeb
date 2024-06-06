package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;

public class DealerIgnoreImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer) throws ImportExeption {
        DealerController dealerController = DealerController.getInstance();
        try {
            if (dealerController.getDealerByName(dealer.getName()) == null &&
                    dealerController.getDealer(dealer.getId()) == null) {
                try {
                    dealerController.addDealer(dealer);
                } catch (AddDealerExeption e) {
                    throw new ImportExeption(e.getMessage());
                }
            }
        } catch (NotFoundException e) {
            throw new ImportExeption(e.getMessage());
        }
    }
}