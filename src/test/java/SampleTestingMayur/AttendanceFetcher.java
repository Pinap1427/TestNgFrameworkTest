package SampleTestingMayur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class AttendanceFetcher {

    public static void main(String[] args) {
        try {
            // API URL
            String urlString = "https://api.exotalent.in/v1/exo/attendance?&dateFrom=2025-04-08&dateTo=2025-04-08&companyId=7277816881553059840";
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Add Authorization token
            conn.setRequestProperty("Authorization", "Bearer 3jbOsSSevHsIpyx6iCOf4CGlXU7urRmeA9LMH2lPhlIIn4d1Jrj0EkzfwD6OsssrcIGgJmnR2UhMLAsVaotlUeCX83up95ssfpknAvuhMBFqcnEPNloBF6S2snPK7mOP");

            // Get response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read and pretty-print the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse and pretty-print JSON
                JSONObject json = new JSONObject(response.toString());
                String prettyJson = json.toString(4); // 4 is indentation level
                System.out.println("Pretty JSON Response:\n" + prettyJson);
            } else {
                System.out.println("GET request failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
