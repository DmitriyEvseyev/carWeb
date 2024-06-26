package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.server.strategy.export.ExportExeption;
import com.dmitriyevseyev.carWeb.server.strategy.importFile.ImportExeption;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    String exportObjects(List<Integer> dealersIds, List<Integer> carsIds) throws ExportExeption;

    void importObjects(String json) throws ImportExeption;
}
