package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.export.car.CarExportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.export.car.CarExportStrategyWithDealer;
import com.dmitriyevseyev.carWeb.server.strategy.export.dealer.DealerExportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.export.dealer.DealerExportStrategyWithCar;

import java.util.HashMap;
import java.util.Map;

public class ExportStrategyHelper {
    private static ExportStrategyHelper instance;
    private final Map<Integer, ExportStrategy> dealerStrategies;
    private final Map<Integer, ExportStrategy> carStrategies;

    public static ExportStrategyHelper getInstance() {
        if (instance == null) {
            instance = new ExportStrategyHelper();
        }
        return instance;
    }

    private ExportStrategyHelper() {
        this.dealerStrategies = new HashMap<>();
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY, new DealerExportStrategy());
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY, new DealerExportStrategyWithCar());

        this.carStrategies = new HashMap<>();
        carStrategies.put(StrategyConstants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY, new CarExportStrategy());
        carStrategies.put(StrategyConstants.CAR_EXPORT_WITH_DEALER_NUMBER_STRATEGY, new CarExportStrategyWithDealer());
    }

    public ExportStrategy resolveDealerStrategy(int strategyID) {
        return this.dealerStrategies.get(strategyID);
    }

    public ExportStrategy resolveCarStrategy(int strategyId) {
        return this.carStrategies.get(strategyId);
    }
}
