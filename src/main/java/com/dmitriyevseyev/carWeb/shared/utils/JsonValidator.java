package com.dmitriyevseyev.carWeb.shared.utils;

import com.dmitriyevseyev.carWeb.servlet.JSONValidatorExeption;
import com.dmitriyevseyev.carWeb.servlet.ServletConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class JsonValidator {
    public static JsonValidator instance;

    public static JsonValidator getInstance() {
        if (instance == null) {
            instance = new JsonValidator();
        }
        return instance;
    }

    private JsonValidator() {
    }

    public String isValidImport(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);


        File schemaFile;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(ServletConstants.PATH_SCHEMA_JSON);
        if (resource == null) {
            throw new IllegalArgumentException("schema.json is not found!");
        } else {
            schemaFile = new File(resource.getFile());
        }


        String schema = FileUtils.readFileToString(schemaFile, StandardCharsets.UTF_8);

        JsonNode jsonNode = objectMapper.readTree(json);
        JsonSchema jsonSchema = schemaFactory.getSchema(schema);
        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);

        StringBuilder error = new StringBuilder();
        validationResult.forEach(vm -> error.append(vm.getMessage()));


        System.out.println("JJJJJJJKsonValidator" + error);


        return error.toString();
    }
}
