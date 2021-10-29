package com.openclassrooms.safetynet.utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.openclassrooms.safetynet.models.DataSource;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonReader {
    public static DataSource getData(String pathToJson) throws IOException {
		JavaTimeModule module = new JavaTimeModule();

        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        module.addDeserializer(LocalDate.class, localDateDeserializer);
    
        ObjectMapper objectMapperObj = Jackson2ObjectMapperBuilder.json().modules(module)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

        URL url = new URL(pathToJson);
        DataSource dataSource = objectMapperObj.readValue(url, DataSource.class);

        return dataSource;
    }
}
