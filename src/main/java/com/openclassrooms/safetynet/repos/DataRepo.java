package com.openclassrooms.safetynet.repos;

import java.io.IOException;
import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.utils.JsonReader;

import org.springframework.stereotype.Repository;

@Repository
public class DataRepo {

    private static DataSource dataSource;

    public DataRepo() throws IOException {
        dataSource = JsonReader
                .getData("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
    }

    public DataSource getData() {
        return dataSource;
    }
}
