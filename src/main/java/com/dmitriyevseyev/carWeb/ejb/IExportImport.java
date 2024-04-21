package com.dmitriyevseyev.carWeb.ejb;

import java.util.List;

public interface IExportImport {
    String exportObjects(List<Integer> dealersIds, List<Integer> carsIds);
           // throws ExportException, PrintableExportException;

    void importObjects(String xml, int userId);
          //  throws PrintableImportException, ImportException;
}
