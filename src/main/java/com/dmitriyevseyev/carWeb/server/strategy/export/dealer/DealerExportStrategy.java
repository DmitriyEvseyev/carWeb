package com.dmitriyevseyev.carWeb.server.strategy.export.dealer;

import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.DealerController;
import com.dmitriyevseyev.carWeb.server.exceptions.car.NotFoundException;
import com.dmitriyevseyev.carWeb.server.exceptions.dealer.GetDealerException;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carWeb.shared.utils.ExportList;

import java.util.ArrayList;
import java.util.List;

public class DealerExportStrategy implements ExportStrategy {
    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) {
        DealerController controllerDealer = DealerController.getInstance();
        List<CarDealership> dealerList = new ArrayList<>();
        try {
            dealerList = controllerDealer.getDealers(ids);
        } catch (
                GetDealerException e) {
            System.out.println("DealerExportStrategyWithCar, GetDealerException" + e.getMessage());
        } catch (
                NotFoundException e) {
            System.out.println("DealerExportStrategyWithCar, NotFoundException. " + e.getMessage());
        }
        exportList.addDelers(dealerList);
    }
}
