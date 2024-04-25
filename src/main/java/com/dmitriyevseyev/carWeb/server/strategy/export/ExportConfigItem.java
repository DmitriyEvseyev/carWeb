package com.dmitriyevseyev.carWeb.server.strategy.export;

public class ExportConfigItem {

    private final String type;

    public final Integer strategyID;

    public ExportConfigItem(String type, Integer strategyID) {
        this.type = type;
        this.strategyID = strategyID;
    }

    public String getType() {
        return type;
    }

    public Integer getStrategyID() {
        return strategyID;
    }

    @Override
    public String toString() {
        return "ExportConfigItem{" +
                "type='" + type + '\'' +
                ", strategyID=" + strategyID +
                '}';
    }
}