package com.openclassrooms.safetynet.utils;

import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.models.DataSource;

public class JsonReader {
    public static DataSource getData(String pathToJson) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL(pathToJson);
		DataSource dataSource = objectMapper.readValue(url, DataSource.class);
        return dataSource;
    }
}
