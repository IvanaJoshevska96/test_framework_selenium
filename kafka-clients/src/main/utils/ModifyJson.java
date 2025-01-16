package com.maersk.procurement.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;

import java.io.File;
import java.io.IOException;

public class ModifyJson {

    public static String modifyJSONInput(String filePath, String... locatorsAndValues) {
        if (locatorsAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Arguments should be in pairs: locator and value.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            String jsonString = objectMapper.writeValueAsString(rootNode);

            for (int i = 0; i < locatorsAndValues.length; i += 2) {
                String locator = locatorsAndValues[i];
                String newValue = locatorsAndValues[i + 1];

                try {

                    jsonString = JsonPath.parse(jsonString).set(locator, newValue).jsonString();
                } catch (JsonPathException e) {
                    System.out.println("Invalid JsonPath or field not found: " + locator);
                    return null;
                }
            }

            JsonNode updatedRootNode = objectMapper.readTree(jsonString);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), updatedRootNode);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedRootNode);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

