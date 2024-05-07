package com.dmitriyevseyev.carWeb.ejb;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IExportImport {
    String exportObjects(List<Integer> dealersIds, List<Integer> carsIds);
           // throws ExportException, PrintableExportException;

    void importObjects(String json, int userId);
          //  throws PrintableImportException, ImportException;
}
