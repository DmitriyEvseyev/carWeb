package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.shared.utils.ExportList;

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

    public ExportList create(List<Integer> dealersIds, List<Integer> carsIds) throws StrategyNotFoundException  {
        ExportList exportList = new ExportList();
        this.fillExportByType(exportList, StrategyConstants.DEALER_TYPE, dealersIds);
        this.fillExportByType(exportList, StrategyConstants.CAR_TYPE, carsIds);
        return exportList;
    }
    private void fillExportByType(ExportList exportList, String type, List<Integer> ids) throws StrategyNotFoundException {
        ExportConfigItem configItem = ExportConfig.getInstance().get(type);

        System.out.println("CCconfigItem --- " + configItem);

        ExportStrategy exportStrategy = ExportStrategyHelper.getInstance().resolveStrategy(configItem);
            if (exportStrategy == null) {
                throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
            }
        exportStrategy.collectExportIds(exportList, ids);
    }
}
