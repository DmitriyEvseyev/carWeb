package com.dmitriyevseyev.carWeb.ejb;

import com.dmitriyevseyev.carWeb.server.strategy.StrategyNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    String exportObjects(List<Integer> dealersIds, List<Integer> carsIds);
           // throws ExportException, PrintableExportException;

    void importObjects(String json) throws StrategyNotFoundException;
          //  throws PrintableImportException, ImportException;
}
