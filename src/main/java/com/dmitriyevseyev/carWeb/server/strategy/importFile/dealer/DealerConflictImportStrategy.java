package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerIdAlreadyExException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerNAmeAddressAlreadyExistException;

public class DealerConflictImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer)  {
        try {
            DealerController dealerController  = DealerController.getInstance();
            dealerController.addDealerWithId(dealer);
        } catch (DealerNAmeAddressAlreadyExistException | DealerIdAlreadyExException e) {
            System.out.println(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (NotFoundException | AddDealerExeption e) {
            System.out.println("DealerConflictImportStrategy. " + e.getMessage());
        }
    }
}