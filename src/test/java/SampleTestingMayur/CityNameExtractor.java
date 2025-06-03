package SampleTestingMayur;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CityNameExtractor {

    public static void main(String[] args) {
        String inputJsonFile = "C:\\Users\\Mayur More\\OneDrive\\Desktop\\atsmantracity.txt"; // your input JSON file
        String outputExcelFile = "city_names_only.xlsx";

        try {
            // Read the entire JSON file content into a string
            String content = new String(Files.readAllBytes(Paths.get(inputJsonFile)));

            // Parse JSON
            JSONObject jsonObject = new JSONObject(content);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            // Create Workbook and Sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("City Names");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("city_name");

            // Fill sheet with city names
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject entry = dataArray.getJSONObject(i);
                String cityName = entry.optString("city_name", "");

                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(cityName);
            }

            // Write to Excel file
            try (FileOutputStream fileOut = new FileOutputStream(outputExcelFile)) {
                workbook.write(fileOut);
            }

            workbook.close();
            System.out.println("Excel file created: " + outputExcelFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
