package com.dmitriyevseyev.carWeb.server.strategy.export;

import com.dmitriyevseyev.carWeb.shared.utils.ExportList;

import java.util.List;

public interface ExportStrategy {
    void collectExportIds(ExportList exportList, List<Integer> ids);
}