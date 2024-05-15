package com.dmitriyevseyev.carWeb.server.strategy.importFile;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.car.CarConflictImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.car.CarIgnoreImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.car.CarOverwriteImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer.DealerConflictImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer.DealerIgnoreImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.dealer.DealerOverwriteImportStrategy;

import java.util.HashMap;
import java.util.Map;

public class ImportStrategyHelper {
    private static ImportStrategyHelper instance;
    private final Map<Integer, ImportStrategy<CarDealership>> dealerStrategies;
    private final Map<Integer, ImportStrategy<Car>> carStrategies;

    public static ImportStrategyHelper getInstance() {
        if (instance == null) {
            instance = new ImportStrategyHelper();
        }
        return instance;
    }

    private ImportStrategyHelper() {
        this.carStrategies = new HashMap<>();
        this.carStrategies.put(StrategyConstants.CAR_OVERWRITE_IMPORT_ID, new CarOverwriteImportStrategy());
        this.carStrategies.put(StrategyConstants.CAR_IGNORE_IMPORT_ID, new CarIgnoreImportStrategy());
        this.carStrategies.put(StrategyConstants.CAR_CONFLICT_IMPORT_ID, new CarConflictImportStrategy());

        this.dealerStrategies = new HashMap<>();
        this.dealerStrategies.put(StrategyConstants.DEALER_OVERWRITE_IMPORT_ID, new DealerOverwriteImportStrategy());
        this.dealerStrategies.put(StrategyConstants.DEALER_IGNORE_IMPORT_ID, new DealerIgnoreImportStrategy());
        this.dealerStrategies.put(StrategyConstants.DEALER_CONFLICT_IMPORT_ID, new DealerConflictImportStrategy());
    }

    public ImportStrategy<CarDealership> resolveDealerStrategy(int strategyID) {
        return this.dealerStrategies.get(strategyID);
    }

    public ImportStrategy<Car> resolveCarStrategy(int strategyId) {
        return this.carStrategies.get(strategyId);
    }
}
