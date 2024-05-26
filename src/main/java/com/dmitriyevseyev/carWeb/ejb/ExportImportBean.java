package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.strategy.*;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportListFactory;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategyHelper;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;
import com.dmitriyevseyev.carWeb.shared.utils.JSONDeserialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExportImportBean implements IExportImport {

    @Override
    public String exportObjects(List<Integer> dealersIds, List<Integer> carsIds) throws ExportExeption, StrategyNotFoundException, PrintableExportException {
        ExportDTO exportDTO = null;

        exportDTO = ExportListFactory.getInstance().create(dealersIds, carsIds);

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(exportDTO);
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException, ExportImportBean. " + e.getMessage());
        }


        System.out.println("ExportImportBean, exportObjects json :::  " + json);


        return json;
    }

    @Override
    public void importObjects(String json) throws ImportExeption {
        List<CarDealership> dealerList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();

        ExportDTO exportDTO = JSONDeserialization.getInstance().deserialization(json);
        dealerList.addAll(exportDTO.getDealers());
        carList.addAll(exportDTO.getCars());

        ExportConfigStrategy config = null;
        try {
            config = ExportConfigStrategy.getInstance();
        } catch (PropertyFileException | StrategyNotFoundException e) {
            throw new ImportExeption("Can't export! " + e.getMessage());
        }
        int dealerImpIdStrategy = config.getImportConfig().get(StrategyConstants.DEALER_TYPE);
        int carImpIdStrategy = config.getImportConfig().get(StrategyConstants.CAR_TYPE);

        ImportStrategyHelper importStrategyHelper = ImportStrategyHelper.getInstance();
        try {
            if (importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy).equals(null)) {
                throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
            } else {
                ImportStrategy<CarDealership> dealerImportStrategy = importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy);
                for (CarDealership dealer : dealerList) {
                    dealerImportStrategy.store(dealer);
                }
            }
        } catch (StrategyNotFoundException e) {
            throw new ImportExeption("Can't export! " + e.getMessage());
        }
        ImportStrategy<CarDealership> dealerImportStrategy = importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy);


        ImportStrategy<Car> carImportStrategy = importStrategyHelper.resolveCarStrategy(carImpIdStrategy);
        if (carImportStrategy == null) {
            throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        for (Car car : carList) {
            carImportStrategy.store(car);
        }
    }
}
