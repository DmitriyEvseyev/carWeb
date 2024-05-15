package com.dmitriyevseyev.carWeb.shared.utils;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.server.controller.CarController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONDeserialization {
    private static JSONDeserialization instance;

    public static JSONDeserialization getInstance() {
        if (instance == null) {
            instance = new JSONDeserialization();
        }
        return instance;
    }

    private JSONDeserialization() {
    }

    public ExportDTO deserialization(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExportDTO exportDTO = null;
        try {
            exportDTO = objectMapper.readValue(json, ExportDTO.class);
        } catch (IOException e) {
            System.out.println("JSONDeserialization. " + e.getMessage());
        }
        return exportDTO;
    }
}

