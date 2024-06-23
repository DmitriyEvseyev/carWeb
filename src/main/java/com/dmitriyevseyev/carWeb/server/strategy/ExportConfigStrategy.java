package com.dmitriyevseyev.carWeb.server.strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExportConfigStrategy {
    private static ExportConfigStrategy instance;

    private ExportConfigStrategy()  {
    }

    public static ExportConfigStrategy getInstance() {
        if (instance == null) {
            instance = new ExportConfigStrategy();
        }
        return instance;
    }

    public Map<String, Integer> getExportConfig() throws StrategyNotFoundException, PropertyFileException {
        Map<String, Integer> exportConfig = new HashMap<>();
        Properties property = new Properties();
        FileInputStream stream = null;
        File file = new File(getClass().getClassLoader().getResource(StrategyConstants.PATH_TO_PROPERTIES).getFile());
        try {
            stream = new FileInputStream(file);
            property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(StrategyConstants.PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (property.getProperty(StrategyConstants.DEALER_EXPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_EXPORT_STRATEGY));
            exportConfig.put(StrategyConstants.DEALER_TYPE, dealerExpStrategy);
        }
        if (property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int carExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY));
            exportConfig.put(StrategyConstants.CAR_TYPE, carExpStrategy);
        }

        return exportConfig;
    }

    public Map<String, Integer> getImportConfig() throws StrategyNotFoundException, PropertyFileException {
        Map<String, Integer> importConfig = new HashMap<>();
        Properties property = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(StrategyConstants.PATH_TO_PROPERTIES);
            property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(StrategyConstants.PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (property.getProperty(StrategyConstants.DEALER_IMPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_IMPORT_STRATEGY));
            importConfig.put(StrategyConstants.DEALER_TYPE, dealerImpStrategy);
        }
        if (property.getProperty(StrategyConstants.CAR_IMPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int carImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_IMPORT_STRATEGY));
            importConfig.put(StrategyConstants.CAR_TYPE, carImpStrategy);
        }
        return importConfig;
    }
}
