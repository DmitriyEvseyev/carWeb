package com.dmitriyevseyev.carWeb.shared.utils;

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
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class JsonValidator {
    public static JsonValidator instance;

    public static  JsonValidator getInstance() {
        if (instance == null) {
            instance = new JsonValidator();
        }
        return instance;
    }

    private JsonValidator() {}

    public boolean isValidImport (String json) throws IOException {
        boolean isValid = false;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

        File schemaFile = new File(ServletConstants.PATH_SCHEMA_JSON);
        String schema = FileUtils.readFileToString(schemaFile, StandardCharsets.UTF_8);

        JsonNode jsonNode = objectMapper.readTree(json);
        JsonSchema jsonSchema = schemaFactory.getSchema(schema);
        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);

        // print validation errors
        if (validationResult.isEmpty()) {
            isValid = true;
        } else {
            validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            isValid = false;
        }
        return isValid;
    }
}
