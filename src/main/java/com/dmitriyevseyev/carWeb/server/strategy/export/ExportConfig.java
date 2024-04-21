package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExportConfig {
    private static ExportConfig instance;
    private final Map<String, ExportConfigItem> configItems;
    private final Properties property;

    private ExportConfig() {
        this.configItems = new HashMap<>();
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
        int dealerStrategy = Integer.parseInt(property.getProperty(StrategyConstants.DEALER_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.DEALER_TYPE,
                new ExportConfigItem(StrategyConstants.DEALER_TYPE, dealerStrategy));
        int carStrategy = Integer.parseInt(property.getProperty(StrategyConstants.CAR_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.CAR_TYPE,
                new ExportConfigItem(StrategyConstants.CAR_TYPE, carStrategy));

    }

    public static ExportConfig getInstance() {
        if (instance == null) {
            instance = new ExportConfig();
        }
        return instance;
    }
    public ExportConfigItem get(String type) {
        return this.configItems.get(type);
    }
}
