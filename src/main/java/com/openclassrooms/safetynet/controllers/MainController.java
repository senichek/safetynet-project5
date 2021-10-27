package com.openclassrooms.safetynet.controllers;

import java.io.IOException;

import com.openclassrooms.safetynet.models.DataSource;
import com.openclassrooms.safetynet.utils.JsonReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class MainController {

    @GetMapping(value = "/")
    public String greetings() throws IOException {    
        log.info("MainController.greetings() was called."); 
        return "Just checking if it's running. Looks OK!";
    }

    @GetMapping(value="/data")
    public DataSource getAllData() throws IOException {
        log.info("MainController.getAllData() was called."); 
        return JsonReader.getData("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
    }
    
}
