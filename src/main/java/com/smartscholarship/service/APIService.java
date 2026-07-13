package com.smartscholarship.service;

import com.smartscholarship.model.Scholarship;

import java.util.ArrayList;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.OutputStream;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Retrieves scholarship data from the Apify API, manages
 * local caching, and converts the retrieved JSON data
 * into Scholarship objects.
 */
public class APIService {

    private static final String API_URL = "https://api.apify.com/v2/acts/commanding_hotdog~scholarship-finder-scraper/run-sync-get-dataset-items?token=";
    private static final Path CACHE_DIRECTORY = Path.of("cache");
    private static final Path CACHE_FILE = CACHE_DIRECTORY.resolve("scholarships.json");
    private static final Path CACHE_PROPERTIES = CACHE_DIRECTORY.resolve("cache.properties");
    private static final Duration CACHE_TTL = Duration.ofHours(24);
    private static final Path SEED_FILE = Path.of("src", "main", "resources", "seed", "scholarships.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * Retrieves scholarship data from the cache or API and
     * converts the response into scholarship objects.
     *
     * @return the list of available scholarships
     */
    public List<Scholarship> fetchScholarships() {
        String json;
        if (hasCache() && isCacheValid()) {
            System.out.println("Loading scholarships from cache...");
            json = readCache();
        } else {
            try {
                System.out.println("Fetching scholarships from API server...");
                json = fetchFromAPI();
                saveCache(json);
                try {
                    saveBundledScholarships(json);
                } catch (RuntimeException e) {
                    System.out.println("Running from packaged application. Bundled scholarship data is read-only.");
                }
            } catch (RuntimeException e) {
                System.out.println("API server is unavailable. Loading bundled scholarship data...");
                json = loadBundledScholarships();
            }
        }
        return parseScholarships(json);
    }

    //This method is for retrieving the API tokens from local files
    private List<String> loadApiTokens() {
        Properties properties = new Properties();
        try (InputStream input = Files.exists(Path.of("config.properties"))
                ? Files.newInputStream(Path.of("config.properties"))
                : getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties is not found.");
            }
            properties.load(input);
            String tokenString = properties.getProperty("api.tokens");
            if(tokenString == null || tokenString.isBlank()){
                throw new RuntimeException("No API tokens are configured");
            }
            return Arrays.stream(tokenString.split(", ")).map(String::trim).filter(token->!token.isEmpty()).toList();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read API tokens", e);
        }
    }

    //This method is used to try each of the API tokens to fetch scholarship data
    private String fetchFromAPI(){
        List<String> tokens = loadApiTokens();
        RuntimeException lastException = null;
        int index = 1;
        for(String token : tokens){
            try{
                return sendRequest(token);
            }catch(RuntimeException e){
                System.out.println("API token #" + index + " failed. Trying next token...");
                lastException = e;
                index++;
            }
        }
        throw new RuntimeException("All API tokens failed", lastException);
    }

    //This method is for creating the JSON request body with the input parameters to be sent to the Apify API
    private String buildRequestBody() {
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
    private HttpRequest buildRequest(String token, String requestBody) {
        return HttpRequest.newBuilder()   //Creates a new HTTP request
                .uri(URI.create(API_URL + token))   //Destination of the request
                .header("Content-Type", "application/json")   //Letting Apify know it is a JSON file
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))   //Body of the request
                .build();   //Finalizes the request
    }

    //This method is used to check the status code and to prevent response when failed
    private void validateResponse(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        if (statusCode != 201) {
            throw new RuntimeException("API request failed. HTTP Status: " + response.statusCode() + "\nResponse: " + response.body());
        }
    }

    //This method is used to send the HTTP request to the Apify API
    private String sendRequest(String token) {
        String requestBody = buildRequestBody();
        HttpRequest request = buildRequest(token, requestBody);
        try {
            HttpResponse<String> response =
                    HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());   //Sends the request
            validateResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to the API. Please check your Internet connection.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("The API request was interrupted.", e);
        }
    }

    //This method is used to check if the local scholarship cache exists
    private boolean hasCache() {
        return Files.exists(CACHE_FILE);
    }

    //This method is used to create the cache if it does not exist and saves the response from the API
    private void saveCache(String json) {
        try {
            if (!Files.exists(CACHE_DIRECTORY)) {
                Files.createDirectories(CACHE_DIRECTORY);
            }
            Files.writeString(
                    CACHE_FILE,
                    json,
                    StandardCharsets.UTF_8
            );
            updateCacheMetadata();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save scholarship cache", e);
        }
    }

    //This method is used to read the cached scholarship JSON
    private String readCache() {
        try {
            return Files.readString(CACHE_FILE, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read scholarship cache", e);
        }
    }

    //This method is used to write the metadata for the cache
    private void updateCacheMetadata() {
        Properties properties = new Properties();
        properties.setProperty("lastUpdated", LocalDateTime.now().toString());
        properties.setProperty("cacheVersion", "1");
        try (OutputStream output = Files.newOutputStream(CACHE_PROPERTIES)) {
            properties.store(output, "Primary Cache Metadata");
        } catch (IOException e) {
            throw new RuntimeException("Failed to update cache metadata", e);
        }
    }

    //This method is used to read the timestamp of the cache
    private LocalDateTime getCacheTimestamp() {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(CACHE_PROPERTIES)) {
            properties.load(input);
            String timestamp = properties.getProperty("lastUpdated");
            if (timestamp == null) {
                throw new RuntimeException("Primary cache metadata is missing the lastUpdated property");
            }
            return LocalDateTime.parse(timestamp);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read primary cache metadata", e);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid cache timestamp format", e);
        }
    }

    //This method is used to check if the cache is expired
    private boolean isCacheValid() {
        if (!hasCache()) {
            return false;
        }
        LocalDateTime lastUpdated = getCacheTimestamp();
        Duration cacheAge = Duration.between(lastUpdated, LocalDateTime.now());
        return cacheAge.compareTo(CACHE_TTL) < 0;
    }

    //This method is used to load the scholarship data that came bundled with the application
    private String loadBundledScholarships() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("seed/scholarships.json")) {
            if (input == null) {
                throw new RuntimeException("Bundled scholarship data not found");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load bundled scholarship data", e);
        }
    }

    //This method is used to rewrite the bundled scholarship data everytime new scholarship information is retrieved
    private void saveBundledScholarships(String json) {
        try {
            Files.writeString(SEED_FILE, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update bundled scholarship data", e);
        }
    }

    //This method is used to read the scholarship JSON data and convert each JSON object into a Scholarship object to them store them in a list
    private List<Scholarship> parseScholarships(String json) {
        try{
            JsonNode root = OBJECT_MAPPER.readTree(json);
            List<Scholarship> scholarships = new ArrayList<>();
            for (JsonNode node : root){
                Scholarship scholarship = parseScholarship(node);
                scholarships.add(scholarship);
            }
            return scholarships;
        }catch(IOException e){
            throw new RuntimeException("Failed to parse scholarship data", e);
        }
    }

    //This method is used to convert a single scholarship JSON object into a Scholarship object.
    private Scholarship parseScholarship(JsonNode node){
        Scholarship scholarship = new Scholarship();
        scholarship.setTitle(getText(node, "title"));
        scholarship.setDescription(getText(node, "description"));
        scholarship.setFullDescription(getText(node, "full_description"));
        scholarship.setAward(getText(node, "award"));
        scholarship.setDeadline(getText(node, "deadline"));
        scholarship.setSponsorName(getText(node, "sponsor_name"));
        scholarship.setSponsorUrl(getText(node, "sponsor_url"));
        scholarship.setDetailUrl(getText(node, "detail_url"));
        scholarship.setApplyUrl(getText(node, "apply_url"));
        scholarship.setAwardType(getText(node, "award_type"));
        scholarship.setRequirements(getText(node, "requirements"));
        scholarship.setMajors(getText(node, "majors"));
        scholarship.setEnrollmentLevel(getText(node, "enrollment_level"));
        scholarship.setGeographicRestrictions(getText(node, "geographic_restrictions"));
        return scholarship;
    }

    //This method is used to retrieve a field from the JSON file
    private String getText(JsonNode node, String fieldName){
        JsonNode value = node.get(fieldName);
        if(value == null || value.isNull()){
            return "";
        }
        return value.asText();
    }
}