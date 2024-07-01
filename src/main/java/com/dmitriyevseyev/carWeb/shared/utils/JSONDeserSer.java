package com.dmitriyevseyev.carWeb.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONDeserSer {
    private static JSONDeserSer instance;
    private ObjectMapper objectMapper;
    public static JSONDeserSer getInstance() {
        if (instance == null) {
            instance = new JSONDeserSer();
        }
        return instance;
    }

    private JSONDeserSer() {
        this.objectMapper = new ObjectMapper();
    }

    public ExportDTO deserialization(String json) {
        ExportDTO exportDTO = null;
        try {
            exportDTO = objectMapper.readValue(json, ExportDTO.class);
        } catch (IOException e) {
            System.out.println("JSONDeserialization. " + e.getMessage());
        }
        return exportDTO;
    }

    public String serialization (ExportDTO exportDTO) throws JsonProcessingException {

        System.out.println("JSONDeserSer, serialization - " + exportDTO);

        return objectMapper.writeValueAsString(exportDTO);
    }
}

