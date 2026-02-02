package SampleTestingMayur;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CountryNameUpdater {

    public static void main(String[] args) {
        // ✅ Corrected file paths
        String inputFile = "C:/Users/Mayur More/Downloads/all cities list without country.csv";
        String outputFile = "C:/Users/Mayur More/Downloads/With all cities list without country.csv";

        List<String[]> updatedData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)))) {
            String header = br.readLine();
            if (header != null) {
                updatedData.add(header.split(","));
            }

            String line;
            int count = 1;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",", -1); // keep empty cells
                if (row.length < 4) continue;

                String name = row[0].trim();
                String title = row[1].trim();
                String state = row[2].trim();
                String country = row[3].trim();

                if (country.isEmpty() || country.equalsIgnoreCase("null")) {
                    String detectedCountry = getCountryFromNominatim(name, state);
                    if (detectedCountry != null) {
                        country = detectedCountry;
                        System.out.println("✅ [" + count + "] Found country for " + name + ", " + state + ": " + country);
                    } else {
                        System.out.println("❌ [" + count + "] Country not found for " + name + ", " + state);
                    }
                }

                row[3] = country;
                updatedData.add(row);
                count++;

                Thread.sleep(1000); // Respect Nominatim usage policy
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)))) {
                for (String[] row : updatedData) {
                    bw.write(String.join(",", row));
                    bw.newLine();
                }
            }

            System.out.println("\n✅ Country update completed! File saved as: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Free-text search using "q=" for better results
    private static String getCountryFromNominatim(String city, String state) {
        // Try city + state + India first, then city + India
        String[] queries = new String[] {
            city + ", " + state + ", India",
            city + ", India"
        };

        for (String q : queries) {
            try {
                String urlStr = "https://nominatim.openstreetmap.org/search?q=" 
                                + URLEncoder.encode(q, StandardCharsets.UTF_8)
                                + "&format=json";

                System.out.println("Querying URL: " + urlStr);

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "MayurCountryUpdater/1.0 (your_email@example.com)");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                    if (jsonArray.size() > 0) {
                        JsonObject obj = jsonArray.get(0).getAsJsonObject();
                        JsonObject address = obj.getAsJsonObject("address");
                        if (address != null && address.has("country")) {
                            return address.get("country").getAsString();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error fetching country for " + city + ", " + state + ": " + e.getMessage());
            }

            // Small delay between retries
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }

        return null;
    }
}
