package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIService {

    public List<Scholarship> fetchScholarships() {
        String token = loadApiToken();
        String json = sendRequest(token);
        return parseScholarships(json);
    }

    //Method for retrieving the API token from local files
    private String loadApiToken(){
        Properties properties = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if(input==null){
                throw new RuntimeException("config.properties is not found.");
            }
            properties.load(input);
            return properties.getProperty("apify.token");
        } catch(IOException e){
            throw new RuntimeException("Unable to read API token.", e);
        }
    }

    private static final String API_URL = "https://api.apify.com/v2/acts/commanding_hotdog~scholarship-finder-scraper/run-sync-get-dataset-items";
    private String sendRequest(String token){
    }
}