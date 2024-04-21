package com.dmitriyevseyev.carWeb.server.strategy;

public class StrategyConstants {
    public final static String PATH_TO_PROPERTIES = "config.properties";

    public static final String DEALER_TYPE = "Dealer";
    public final static String DEALER_EXPORT_STRATEGY = "dealerExportStrategy";
    public final static int DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY = 1;
    public final static int DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY = 2;

    public static final String CAR_TYPE = "Car";
    public final static String CAR_EXPORT_STRATEGY = "dealerExportStrategy";

    public final static int CAR_EXPORT_NUMBER_STRATEGY = 1;


    public final static String EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE = "Export strategy not found. ";
}
