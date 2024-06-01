package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TASK4 {
    private static final String API_URL = "https://3ospphrepc.execute-api.us-west-2.amazonaws.com/prod/RDSLambda";
    private static final String BUCKET_NAME = "interview-digiage";
    private static final String FILE_PATH = "gender_counts.txt";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            Map<String, Integer> genderCounts = new HashMap<>();

            Iterator<JsonNode> elements = rootNode.elements();
            while (elements.hasNext()) {
                JsonNode record = elements.next();
                String gender = record.get("gender").asText();
                genderCounts.put(gender, genderCounts.getOrDefault(gender, 0) + 1);
            }

            File file = new File(FILE_PATH);
            try (FileWriter writer = new FileWriter(file)) {
                for (Map.Entry<String, Integer> entry : genderCounts.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
            }

            Region region = Region.US_WEST_2;
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(file.getName())
                    .build();

            s3.putObject(putObjectRequest, Paths.get(file.getAbsolutePath()));

            System.out.println("File uploaded to S3 successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
