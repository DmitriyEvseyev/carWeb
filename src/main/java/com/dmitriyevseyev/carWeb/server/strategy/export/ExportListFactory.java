package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.server.strategy.ExportConfigStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.PrintableExportException;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;

import java.util.List;

public class ExportListFactory {
    private static ExportListFactory instance;

    public static ExportListFactory getInstance() {
        if (instance == null) {
            instance = new ExportListFactory();
        }
        return instance;
    }
    private ExportListFactory() {
    }

    public ExportDTO create(List<Integer> dealersIds, List<Integer> carsIds) throws StrategyNotFoundException, ExportExeption, PrintableExportException {
        ExportDTO exportList = new ExportDTO();
        this.fillExportDealer(exportList, dealersIds);
        this.fillExportCar(exportList, carsIds);
        return exportList;
    }


    private void fillExportDealer(ExportDTO exportList, List<Integer> dealersIds) throws StrategyNotFoundException, ExportExeption, PrintableExportException {
        ExportConfigStrategy config = ExportConfigStrategy.getInstance();
        int dealerExpIdStrategy = config.getExportConfig().get(StrategyConstants.DEALER_TYPE);

        ExportStrategy dealerExportStrategy = ExportStrategyHelper.getInstance().resolveDealerStrategy(dealerExpIdStrategy);
        if (dealerExportStrategy == null) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        dealerExportStrategy.collectExportIds(exportList, dealersIds);
    }



    private void fillExportCar(ExportDTO exportList, List<Integer> carIds) throws StrategyNotFoundException, ExportExeption, PrintableExportException {
        ExportConfigStrategy config = ExportConfigStrategy.getInstance();
        int carExpIdStrategy = config.getExportConfig().get(StrategyConstants.CAR_TYPE);

        ExportStrategy carExportStrategy = ExportStrategyHelper.getInstance().resolveCarStrategy(carExpIdStrategy);
        if (carExportStrategy == null) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        carExportStrategy.collectExportIds(exportList, carIds);
    }
}