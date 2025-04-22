package SampleTestingMayur;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadDocxFile {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\Mayur More Resume .pdf mayur (1).docx"; // Update the file path
        String empEmailid = "";
        String empMobileNo = "";

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            XWPFDocument document = new XWPFDocument(fis);
            String text = document.getParagraphs().stream()
                    .map(p -> p.getText())
                    .reduce("", (acc, p) -> acc + p + "\n");

            System.out.println("DOCX Content:\n" + text);

            // Extract email
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            Matcher emailMatcher = emailPattern.matcher(text);
            if (emailMatcher.find()) {
                empEmailid = emailMatcher.group();
            }

            // Extract global mobile numbers (with or without country code)
            Pattern mobilePattern = Pattern.compile("\\+?\\d{1,4}[-\\s]?\\(?\\d{1,4}\\)?[-\\s]?\\d{1,4}[-\\s]?\\d{1,4}");
            Matcher mobileMatcher = mobilePattern.matcher(text);
            if (mobileMatcher.find()) {
                empMobileNo = mobileMatcher.group();
            }

            System.out.println("Extracted Email ID: " + empEmailid);
            System.out.println("Extracted Mobile No: " + empMobileNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
