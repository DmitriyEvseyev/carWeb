package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.model.dto.CarDTO;
import com.dmitriyevseyev.carWeb.server.strategy.*;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportListFactory;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportStrategyHelper;
import com.dmitriyevseyev.carWeb.shared.utils.ConverterDTO;
import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;
import com.dmitriyevseyev.carWeb.shared.utils.JSONDeserSer;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ExportImportBean implements IExportImport {

    @Override
    public String exportObjects(List<Integer> dealersIds, List<Integer> carsIds) throws ExportExeption {
        ExportDTO exportDTO = null;
        try {
            exportDTO = ExportListFactory.getInstance().create(dealersIds, carsIds);


            System.out.println("ExportImportBean, exportDTO - " + exportDTO);
        } catch (StrategyNotFoundException e) {
            throw new ExportExeption(e.getMessage());
        }

        String json = null;
        try {
            json = JSONDeserSer.getInstance().serialization(exportDTO);
        } catch (JsonProcessingException e) {
            throw new ExportExeption("JsonProcessingException. " + e.getMessage());
        }
        System.out.println("EJB JSON: " + json);
        return json;
    }

    @Override
    public void importObjects(String json) throws ImportExeption {
        ConverterDTO converterDTO = ConverterDTO.getInstance();
        List<CarDealership> dealerList = new ArrayList<>();
        List<CarDTO> carList = new ArrayList<>();

        ExportDTO exportDTO = JSONDeserSer.getInstance().deserialization(json);
        dealerList.addAll(converterDTO.convertetDealerDTOToDealer(exportDTO.getDealers()));
        carList.addAll(exportDTO.getCars());

        ExportConfigStrategy config = ExportConfigStrategy.getInstance();
        int dealerImpIdStrategy;
        int carImpIdStrategy;
        try {
            dealerImpIdStrategy = config.getImportConfig().get(StrategyConstants.DEALER_TYPE);
            carImpIdStrategy = config.getImportConfig().get(StrategyConstants.CAR_TYPE);
        } catch (PropertyFileException | StrategyNotFoundException e) {
            throw new ImportExeption(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

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
            throw new ImportExeption(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        try {
            if (importStrategyHelper.resolveCarStrategy(carImpIdStrategy).equals(null)) {
                throw new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
            } else {
                ImportStrategy<CarDTO> carImportStrategy = importStrategyHelper.resolveCarStrategy(carImpIdStrategy);
                for (CarDTO carDTO : carList) {
                    carImportStrategy.store(carDTO);
                }
            }
        } catch (StrategyNotFoundException e) {
            throw new ImportExeption(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
