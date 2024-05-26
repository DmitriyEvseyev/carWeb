package com.dmitriyevseyev.carWeb.server.strategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExportConfigStrategy {
    private static ExportConfigStrategy instance;
    private Map<String, Integer> exportConfig;
    private Map<String, Integer> importConfig;
    private final Properties property;

    private ExportConfigStrategy() throws StrategyNotFoundException, PropertyFileException {
        this.exportConfig = new HashMap<>();
        this.importConfig = new HashMap<>();
        this.property = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(StrategyConstants.PATH_TO_PROPERTIES);
            property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(StrategyConstants.PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (StrategyConstants.DEALER_EXPORT_STRATEGY.isEmpty() ||
                property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_EXPORT_STRATEGY));
            this.exportConfig.put(StrategyConstants.DEALER_TYPE, dealerExpStrategy);
            int carExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY));
            this.exportConfig.put(StrategyConstants.CAR_TYPE, carExpStrategy);
        }

        if (property.getProperty(StrategyConstants.DEALER_IMPORT_STRATEGY).isEmpty() ||
                property.getProperty(StrategyConstants.CAR_IMPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_IMPORT_STRATEGY));
            this.importConfig.put(StrategyConstants.DEALER_TYPE, dealerImpStrategy);
            int carImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_IMPORT_STRATEGY));
            this.importConfig.put(StrategyConstants.CAR_TYPE, carImpStrategy);
        }
    }

    public static ExportConfigStrategy getInstance() throws StrategyNotFoundException, PropertyFileException {
        if (instance == null) {
            instance = new ExportConfigStrategy();
        }
        return instance;
    }

    public Map<String, Integer> getExportConfig() {
        return exportConfig;
    }

    public Map<String, Integer> getImportConfig() {
        return importConfig;
    }
}
