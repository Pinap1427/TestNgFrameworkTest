package SampleTestingMayur;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReadPdfFile {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\AshwiniPatil-CV.pdf"; // Update the file path
        String empEmailid = "";
        String empMobileNo = "";

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String text = pdfStripper.getText(document);

                System.out.println("PDF Content:\n" + text);

                // Extract email
                Pattern emailPattern1 = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
                Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,})+");

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
