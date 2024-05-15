package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.strategy.ExportConfigStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyConstants;
import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportListFactory;
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
    public String exportObjects(List<Integer> dealersIds, List<Integer> carsIds) {
        ExportDTO exportDTO = null;
        try {
            exportDTO = ExportListFactory.getInstance().create(dealersIds, carsIds);
        } catch (StrategyNotFoundException e) {
            System.out.println("ExportImportBean. " + e.getMessage());
        }
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
    public void importObjects(String json) throws StrategyNotFoundException /*throws PrintableImportException, ImportException*/ {
        List<CarDealership> dealerList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();

        ExportDTO exportDTO = JSONDeserialization.getInstance().deserialization(json);
        dealerList.addAll(exportDTO.getDealers());
        carList.addAll(exportDTO.getCars());

        ExportConfigStrategy config = ExportConfigStrategy.getInstance();
        int dealerImpIdStrategy = config.getImportConfig().get(StrategyConstants.DEALER_TYPE);
        int carImpIdStrategy = config.getImportConfig().get(StrategyConstants.CAR_TYPE);

        ImportStrategyHelper importStrategyHelper = ImportStrategyHelper.getInstance();
        ImportStrategy<CarDealership> dealerImportStrategy = importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy);
        if (dealerImportStrategy == null)
            throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        for (CarDealership dealer : dealerList) {
            dealerImportStrategy.store(dealer);
        }
        ImportStrategy<Car> carImportStrategy = importStrategyHelper.resolveCarStrategy(carImpIdStrategy);
        if (carImportStrategy == null) throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        for (Car car : carList) {
            carImportStrategy.store(car);
        }
    }
}
