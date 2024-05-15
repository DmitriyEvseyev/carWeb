package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerIdAlreadyExistException;

public class DealerIgnoreImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer) {
        try {
            DealerController dealerController = DealerController.getInstance();
            CarDealership oldDealer = null;

            oldDealer = dealerController.getDealer(dealer.getId());

            if (oldDealer == null) {
                dealerController.addDealerWithId(dealer);
            }
        } catch (DealerIdAlreadyExistException e) {
            System.out.println(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (GetDealerException | NotFoundException | AddDealerExeption e) {
            System.out.println("DealerIgnoreImportStrategy.  " + e.getMessage());
        }
    }
}