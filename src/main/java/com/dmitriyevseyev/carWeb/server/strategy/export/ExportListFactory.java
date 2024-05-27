package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.server.strategy.*;
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

    public ExportDTO create(List<Integer> dealersIds, List<Integer> carsIds) throws StrategyNotFoundException, ExportExeption {
        ExportDTO exportList = new ExportDTO();
        this.fillExportDealer(exportList, dealersIds);
        this.fillExportCar(exportList, carsIds);
        return exportList;
    }


    private void fillExportDealer(ExportDTO exportList, List<Integer> dealersIds) throws StrategyNotFoundException, ExportExeption {
        ExportConfigStrategy config = null;
        int dealerExpIdStrategy;
        config = ExportConfigStrategy.getInstance();
        try {
            dealerExpIdStrategy = config.getExportConfig().get(StrategyConstants.DEALER_TYPE);
        } catch (PropertyFileException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (ExportStrategyHelper.getInstance().resolveDealerStrategy(dealerExpIdStrategy).equals(null)) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            ExportStrategy dealerExportStrategy = ExportStrategyHelper.getInstance().resolveDealerStrategy(dealerExpIdStrategy);
            dealerExportStrategy.collectExportIds(exportList, dealersIds);
        }
    }

    private void fillExportCar(ExportDTO exportList, List<Integer> carIds) throws StrategyNotFoundException, ExportExeption {

        ExportConfigStrategy config = null;
        int carExpIdStrategy;
        config = ExportConfigStrategy.getInstance();

        try {
            if (exportList.getDealers().size() > 0) {
                carExpIdStrategy = StrategyConstants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY;
            } else {
                carExpIdStrategy = config.getExportConfig().get(StrategyConstants.CAR_TYPE);
            }
        } catch (PropertyFileException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (ExportStrategyHelper.getInstance().resolveCarStrategy(carExpIdStrategy).equals(null)) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            ExportStrategy carExportStrategy = ExportStrategyHelper.getInstance().resolveCarStrategy(carExpIdStrategy);
            carExportStrategy.collectExportIds(exportList, carIds);
        }
    }
}