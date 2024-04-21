package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.export.car.CarExportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.export.dealer.DealerExportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.export.dealer.DealerExportStrategyWithCar;

import java.util.HashMap;
import java.util.Map;

public class ExportStrategyHelper {
    private static ExportStrategyHelper instance;
    private final Map<String, Map<Integer, ExportStrategy>> strategiesByType;

    public static ExportStrategyHelper getInstance() {
        if (instance == null) {
            instance = new ExportStrategyHelper();
        }
        return instance;
    }
    private ExportStrategyHelper() {
        this.strategiesByType = new HashMap<>();

        Map<Integer, ExportStrategy> dealerStrategies = new HashMap<>();
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY, new DealerExportStrategy());
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY, new DealerExportStrategyWithCar());

        this.strategiesByType.put(StrategyConstants.DEALER_TYPE, dealerStrategies);

        Map<Integer, ExportStrategy> carStrategies = new HashMap<>();
        carStrategies.put(StrategyConstants.CAR_EXPORT_NUMBER_STRATEGY, new CarExportStrategy());

        this.strategiesByType.put(StrategyConstants.CAR_TYPE, carStrategies);
    }
    public ExportStrategy resolveStrategy(ExportConfigItem item) {
        return this.strategiesByType.get(item.getType()).get(item.getStrategyID());
    }
}
