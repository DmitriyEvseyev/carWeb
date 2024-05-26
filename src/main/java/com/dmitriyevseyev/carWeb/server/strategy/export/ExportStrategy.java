package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.shared.utils.ExportDTO;

import java.util.List;

public interface ExportStrategy {
    void collectExportIds(ExportDTO exportList, List<Integer> ids) throws  ExportExeption;
}