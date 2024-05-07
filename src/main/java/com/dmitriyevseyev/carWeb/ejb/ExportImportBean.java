package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carWeb.server.strategy.export.ExportListFactory;
import com.dmitriyevseyev.carWeb.shared.utils.ExportList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ExportImportBean implements IExportImport {

    @Override
    public String exportObjects(List<Integer> dealersIds, List<Integer> carsIds) {
        ExportList exportList = null;
        try {
            exportList = ExportListFactory.getInstance().create(dealersIds, carsIds);
        } catch (StrategyNotFoundException e) {
            System.out.println("ExportImportBean. " + e.getMessage());
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(exportList);
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException, ExportImportBean. " + e.getMessage());
        }


        System.out.println("ExportImportBean, exportObjects json :::  " + json);


        return json;
    }
    @Override
    public void importObjects(String xml, int userId) {

    }
            //throws PrintableImportException, ImportException {
//            XMLMarshaller xmlMarshaller = new XMLMarshaller();
//
//            List<Journal> journals = new ArrayList<>();
//            List<Task> tasks = new ArrayList<>();
//
//            try {
//                xmlMarshaller.unmarshal(journals, tasks, xml, userId);
//            } catch (UnmarshalException e) {
//                throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
//            }
//
//            ImportStrategyHelper importStrategyHelper = ImportStrategyHelper.getInstance();
//            PropertyParser propertyParser;
//            try {
//                propertyParser = PropertyParser.getInstance();
//            } catch (PropertyFileException e) {
//                throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
//            }
//
//            int strategyId = Integer.parseInt(propertyParser.getProperty(StrategyConstants.JOURNAL_IMPORT_STRATEGY));
//            ImportStrategy<Journal> journalImportStrategy =
//                    importStrategyHelper.resolveJournalStrategy(strategyId);
//            try {
//                if (journalImportStrategy == null) throw
//                        new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
//            } catch (StrategyNotFoundException e) {
//                throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
//            }
//
//            for (Journal journal : journals) journalImportStrategy.store(journal);
//
//            strategyId = Integer.parseInt(propertyParser.getProperty(StrategyConstants.TASK_IMPORT_STRATEGY));
//            ImportStrategy<Task> taskImportStrategy = importStrategyHelper.resolveTaskStrategy(strategyId);
//
//            try {
//                if (taskImportStrategy == null) throw
//                        new StrategyNotFoundException(StrategyConstants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
//            } catch (StrategyNotFoundException e) {
//                throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
//            }
//            for (Task task : tasks) taskImportStrategy.store(task);
//        }
    }
