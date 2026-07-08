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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class APIService {

    private static final String API_URL = "https://api.apify.com/v2/acts/commanding_hotdog~scholarship-finder-scraper/run-sync-get-dataset-items?token=";
    private static final Path CACHE_DIRECTORY = Path.of("cache");
    private static final Path CACHE_FILE = CACHE_DIRECTORY.resolve("scholarships.json");

    public List<Scholarship> fetchScholarships(){
        String json;
        if(hasCache()){
            System.out.println("Loading scholarships from cache...");
            json = readCache();
        }else{
            System.out.println("Fetching scholarships from API...");
            String token = loadApiToken();
            json = sendRequest(token);
            saveCache(json);
        }

        //This is temporary until Scholarship.java is ready to use
        System.out.println(json);
        return List.of();
    }

    //This method is for retrieving the API token from local files
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

    //This method is for creating the JSON request body to be sent to the Apify API
    private String buildRequestBody(){
        return """
                {
                    "keyword": "",
                    "maxResults": "50",
                    "maxPages": "5",
                    "fetchDetails": true
                }
                """;
    }

    //This method is for creating the HTTP request that will be sent to the Apify API
    private HttpRequest buildRequest(String token, String requestBody){
        return HttpRequest.newBuilder()   //Creates a new HTTP request
                .uri(URI.create(API_URL+token))   //Destination of the request
                .header("Content-Type", "application/json")   //Letting Apify know it is a JSON file
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))   //Body of the request
                .build();   //Finalizes the request
    }

    //This method is used to check the status code and to prevent response when failed
    private void validateResponse(HttpResponse<String> response){
        int statusCode = response.statusCode();
        if(statusCode!=201){
            throw new RuntimeException("Apify request failed. HTTP Status: " + response.statusCode() + "\nResponse: " + response.body());
        }
    }

    //This method is used to check if the local scholarship cache exists
    private boolean hasCache(){
        return Files.exists(CACHE_FILE);
    }

    //This method is used to create the cache if it does not exist and saves the response from the API
    private void saveCache(String json){
        try{
            if(!Files.exists(CACHE_DIRECTORY)){
                Files.createDirectories(CACHE_DIRECTORY);
            }
            Files.writeString(
                    CACHE_FILE,
                    json,
                    StandardCharsets.UTF_8
            );
        }catch(IOException e){
            throw new RuntimeException("Failed to save scholarship cache", e);
        }
    }

    //This method is used to read the cached scholarship JSON
    private String readCache(){
        try{
            return Files.readString(CACHE_FILE, StandardCharsets.UTF_8);
        }catch(IOException e){
            throw new RuntimeException("Failed to read scholarship cache", e);
        }
    }

    //This method is used to send the HTTP request to the Apify API
    private String sendRequest(String token){
        String requestBody = buildRequestBody();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = buildRequest(token, requestBody);
        try{
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());   //Sends the request
            validateResponse(response);
            return response.body();
        } catch(IOException e){
            throw new RuntimeException("Unable to connect to the Apify API. Please check your Internet connection.", e);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("The API request was interrupted.", e);
        }
    }

    private List<Scholarship> parseScholarships(String json){
        return List.of();
    }
}