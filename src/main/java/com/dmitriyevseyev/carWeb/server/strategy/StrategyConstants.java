package com.dmitriyevseyev.carWeb.server.strategy;

public class StrategyConstants {
    public final static String PATH_TO_PROPERTIES = "C:\\Users\\VivoB\\IdeaProjects\\carWeb\\src\\main\\resources\\config.properties";

    public static final String DEALER_TYPE = "Dealer";
    public final static String DEALER_EXPORT_STRATEGY = "dealerExportStrategy";
    public final static int DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY = 1;
    public final static int DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY = 2;

    public static final String CAR_TYPE = "Car";
    public final static String CAR_EXPORT_STRATEGY = "carExportStrategy";

    public final static int CAR_EXPORT_NUMBER_STRATEGY = 1;


    public final static String EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE = "Export strategy not found. ";
    public final static String IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE =  "Import strategy not found. ";

    public final static String IMPORT_EXCEPTION_MESSAGE = "Import is not possible! ";

    public final static String EXPORT_EXCEPTION_MESSAGE = "Export is not possible! ";

    public final static Integer CAR_OVERWRITE_IMPORT_ID = 1;
    public final static Integer CAR_IGNORE_IMPORT_ID = 2;
    public final static Integer CAR_CONFLICT_IMPORT_ID = 3;

    public final static Integer DEALER_OVERWRITE_IMPORT_ID = 1;
    public final static Integer DEALER_IGNORE_IMPORT_ID = 2;
    public final static Integer DEALER_CONFLICT_IMPORT_ID = 3;

        public final static String DEALER_IMPORT_STRATEGY = "dealerImportStrategy";
    public final static String CAR_IMPORT_STRATEGY = "carImportStrategy";


    public final static String  PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE = "Properties file not found!";



}
