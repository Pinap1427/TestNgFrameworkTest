package SampleTestingMayur;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class GetUsersAPI {

    public static void main(String[] args) {
        String baseUrl = "https://api.dev.exotalent.in";
        String endpoint = "/v1/exo/users/search";
        String bearerToken = "85nXxTTm8RNx2NzMtWBOeFiG4Zp0jlhzTXmbJD5DsjS0kh1BuLSe7Udams8BedtomU7COqTTay9YthzVEbJbxIBns6yrJNHBSwSqqfbGvELTZ3QX7k35fPGPoKU2zAjL";

        int size = 20;
        String authorId = "7243822606233853952";

        String fullUrl = String.format("%s%s?size=%d&authorId=%s",
                baseUrl, endpoint, size, authorId);

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode json = mapper.readTree(response.body());

            // Print formatted JSON response (optional)
            String prettyJson = writer.writeValueAsString(json);
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Formatted Response:\n" + prettyJson);

            // Read and print user info
            JsonNode users = json.get("users");
            if (users != null && users.isArray()) {
                for (JsonNode user : users) {
                    String name = user.path("name").asText();
                    String industry = user.path("industry").asText();
                    System.out.println("Name: " + name + ", Industry: " + industry);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
