package SampleTestingMayur;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MergeCities {

    public static void main(String[] args) {
        try {
            // 🗂️ OPTION 1: Hardcoded paths (edit as needed)
            String mainFile = "C:\\Users\\Mayur More\\Downloads\\countries-states-cities-database-master\\countries-states-cities-database-master\\csv\\merge\\Cities (Default View) (5).csv";
            String secondFile = "C:\\Users\\Mayur More\\Downloads\\countries-states-cities-database-master\\countries-states-cities-database-master\\csv\\merge\\cities.csv";
            String outputFile = "C:\\Users\\Mayur More\\Downloads\\countries-states-cities-database-master\\countries-states-cities-database-master\\csv\\merge\\merged_cities.xlsx";

            // 🗂️ OPTION 2: Use file chooser (uncomment below if you prefer interactive mode)
            /*
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select the main cities CSV");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                mainFile = chooser.getSelectedFile().getAbsolutePath();
            }

            chooser.setDialogTitle("Select the second cities CSV");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                secondFile = chooser.getSelectedFile().getAbsolutePath();
            }

            chooser.setDialogTitle("Select where to save merged Excel");
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                outputFile = chooser.getSelectedFile().getAbsolutePath();
                if (!outputFile.endsWith(".xlsx")) {
                    outputFile += ".xlsx";
                }
            }
            */

            // ✅ Check file existence
            if (!Files.exists(Paths.get(mainFile))) {
                System.err.println("❌ Main file not found: " + mainFile);
                return;
            }
            if (!Files.exists(Paths.get(secondFile))) {
                System.err.println("❌ Second file not found: " + secondFile);
                return;
            }

            System.out.println("📂 Main file: " + mainFile);
            System.out.println("📂 Second file: " + secondFile);

            // Step 1️⃣ Read and clean both city lists
            System.out.println("🔍 Reading and cleaning city lists...");
            List<String> mainCities = cleanCities(readCSV(mainFile));
            List<String> secondCities = cleanCities(readCSV(secondFile));

            // Step 2️⃣ Remove duplicates within each list
            mainCities = removeDuplicates(mainCities);
            secondCities = removeDuplicates(secondCities);

            // Step 3️⃣ Build lookup set for main cities
            Set<String> mainCitySet = mainCities.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            // Step 4️⃣ Keep only new (unique) cities in second list
            List<String> newUniqueCities = secondCities.stream()
                    .filter(c -> !mainCitySet.contains(c.toLowerCase()))
                    .sorted()
                    .collect(Collectors.toList());

            // Step 5️⃣ Sort main cities too
            Collections.sort(mainCities);

            // Step 6️⃣ Write both sheets to Excel
            writeToExcel(mainCities, newUniqueCities, outputFile);

            System.out.println("✅ " + mainCities.size() + " main cities written to 'Cities_Main' sheet");
            System.out.println("✅ " + newUniqueCities.size() + " unique new cities written to 'Cities_New_Unique' sheet");
            System.out.println("🎉 Final file saved as: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Read CSV file into a list of strings
    private static List<String> readCSV(String filePath) throws IOException {
        List<String> cities = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] parts = line.split("[,;]");
                for (String p : parts) {
                    String clean = cleanCityName(p);
                    if (!clean.isEmpty()) {
                        cities.add(clean);
                    }
                }
            }
            System.out.println("📄 Read " + lineCount + " lines from: " + filePath);
        }
        return cities;
    }

    // 🔹 Clean single city name
    private static String cleanCityName(String name) {
        name = name.trim();
        if (name.isEmpty()) return "";
        // Normalize case
        name = Arrays.stream(name.toLowerCase().split("\\s+"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                .collect(Collectors.joining(" "));
        return name;
    }

    // 🔹 Filter invalid entries (non-city words)
    private static List<String> cleanCities(List<String> cities) {
        Pattern invalid = Pattern.compile("(?i)(road|street|lane|company|hospital|mall|school|university|college|society|sector|layout|block|phase|zone|park|center|centre|project|apartment|residency|flat|area|village|district|state|country|address|near|opposite|gate|station|airport|metro|stop|cross)");
        return cities.stream()
                .filter(c -> c.length() > 1 && c.split("\\s+").length <= 3 && !invalid.matcher(c).find())
                .collect(Collectors.toList());
    }

    // 🔹 Remove duplicates (case-insensitive)
    private static List<String> removeDuplicates(List<String> list) {
        Set<String> seen = new HashSet<>();
        List<String> unique = new ArrayList<>();
        for (String s : list) {
            String key = s.toLowerCase();
            if (!seen.contains(key)) {
                seen.add(key);
                unique.add(s);
            }
        }
        return unique;
    }

    // 🔹 Write both city lists to Excel
    private static void writeToExcel(List<String> mainCities, List<String> newUniqueCities, String outputFile) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // Sheet 1
        Sheet sheet1 = workbook.createSheet("Cities_Main");
        Row header1 = sheet1.createRow(0);
        header1.createCell(0).setCellValue("Main City List");
        for (int i = 0; i < mainCities.size(); i++) {
            sheet1.createRow(i + 1).createCell(0).setCellValue(mainCities.get(i));
        }

        // Sheet 2
        Sheet sheet2 = workbook.createSheet("Cities_New_Unique");
        Row header2 = sheet2.createRow(0);
        header2.createCell(0).setCellValue("Unique New Cities");
        for (int i = 0; i < newUniqueCities.size(); i++) {
            sheet2.createRow(i + 1).createCell(0).setCellValue(newUniqueCities.get(i));
        }

        // Autosize columns
        sheet1.autoSizeColumn(0);
        sheet2.autoSizeColumn(0);

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}
