package SampleTestingMayur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCompanyUsers {
    public static void main(String[] args) {
        String host = "https://api.exotalent.in";
        String endpoint = "/v1/exo/companies/7277816881553059840/users";
        String fullUrl = host + endpoint;

        try {
            URL url = new URL(fullUrl);

            // ⏱️ Start time before making the connection
            long startTime = System.currentTimeMillis();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // If authentication is needed:
            // conn.setRequestProperty("Authorization", "Bearer YOUR_TOKEN");

            int responseCode = conn.getResponseCode();

            // ⏱️ End time after getting the response
            long endTime = System.currentTimeMillis();

            // ⏱️ Calculate response time in milliseconds
            long responseTime = endTime - startTime;
            System.out.println("Response Time: " + responseTime + " ms");

            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse as JSON Object
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Print full JSON response (optional)
                System.out.println("Full Response: " + jsonResponse.toString(2));

                // Get array of users
                JSONArray users = jsonResponse.getJSONArray("users");

                System.out.println("\nUsers List:");
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    System.out.println(user.toString(2));
                    System.out.println();
                }

            } else {
                System.out.println("GET request failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
