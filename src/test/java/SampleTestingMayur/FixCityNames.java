package SampleTestingMayur;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FixCityNames {

    public static void main(String[] args) {
        String inputPath = "C:\\Users\\Mayur More\\Downloads\\merged_cities.csv";   // input CSV
        String outputPath = "C:\\Users\\Mayur More\\Downloads\\cleaned_cities.csv"; // output CSV

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.ISO_8859_1));
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8))
        ) {
            String line;
            long count = 0;

            while ((line = reader.readLine()) != null) {
                // Clean each line by re-encoding corrupted text
                String cleaned = cleanText(line);
                writer.write(cleaned);
                writer.newLine();

                if (++count % 10000 == 0) {
                    System.out.println("Processed " + count + " lines...");
                }
            }

            System.out.println("✅ Cleaned file saved successfully at:");
            System.out.println(outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Re-encode text from Latin1 (ISO-8859-1) → UTF-8
    private static String cleanText(String text) {
        if (text == null || text.isEmpty()) return text;
        try {
            byte[] bytes = text.getBytes("ISO-8859-1");
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return text;
        }
    }
}
