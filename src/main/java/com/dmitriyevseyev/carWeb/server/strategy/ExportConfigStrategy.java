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

    private ExportConfigStrategy() {
        this.exportConfig = new HashMap<>();
        this.importConfig = new HashMap<>();
        this.property = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(StrategyConstants.PATH_TO_PROPERTIES);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException, ExportConfig. " + e.getMessage());
        }
        try {
            property.load(stream);
        } catch (IOException e) {
            System.out.println("IOException, ExportConfig. " + e.getMessage());
        }
        int dealerExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_EXPORT_STRATEGY));
        this.exportConfig.put(StrategyConstants.DEALER_TYPE, dealerExpStrategy);
        int carExpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY));
        this.exportConfig.put(StrategyConstants.CAR_TYPE, carExpStrategy);

        int dealerImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_IMPORT_STRATEGY));
        this.importConfig.put(StrategyConstants.DEALER_TYPE, dealerImpStrategy);
        int carImpStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_IMPORT_STRATEGY));
        this.importConfig.put(StrategyConstants.CAR_TYPE, carImpStrategy);
    }

    public static ExportConfigStrategy getInstance() {
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
