package com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.AddDealerExeption;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.UpdateDealerException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerIdAlreadyExException;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.exeption.DealerNAmeAddressAlreadyExistException;

public class DealerOverwriteImportStrategy implements ImportStrategy<CarDealership> {
    @Override
    public void store(CarDealership dealer) {
        try {
            DealerController dealerController = DealerController.getInstance();
            CarDealership oldDealer = dealerController.getDealerByNameAddress(dealer.getName(), dealer.getAddress());

            //           CarDealership oldDealer = dealerController.getDealer(dealer.getId());

            System.out.println("dealer - " + dealer);
            System.out.println("oldDealer -  " + oldDealer);
            System.out.println(oldDealer == null);


            if (oldDealer != null) {
                dealerController.updateDealer(dealer.getId(), dealer.getName(), dealer.getAddress());
            } else {
                dealerController.addDealerWithId(dealer);
            }
        } catch (DealerNAmeAddressAlreadyExistException | DealerIdAlreadyExException e) {
            System.out.println(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (NotFoundException | UpdateDealerException | AddDealerExeption e) {
            System.out.println("DealerOverwriteImportStrategy.  " + e.getMessage());
        }
    }
}