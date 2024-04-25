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
}