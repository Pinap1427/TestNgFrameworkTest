package SampleTestingMayur;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRPdfReader {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\IMG_1060.pdf";
        String empEmailid = "";
        String empMobileNo = "";

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Users\\Mayur More\\AppData\\Local\\Programs\\Tesseract-OCR\\tessdata"); // Update to your tessdata folder path
            tesseract.setLanguage("eng");

            StringBuilder fullText = new StringBuilder();

            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
                String text = tesseract.doOCR(image);
                fullText.append(text);
            }

            String extractedText = fullText.toString();
            System.out.println("OCR Extracted Text:\n" + extractedText);

            // Extract Email
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            Matcher emailMatcher = emailPattern.matcher(extractedText);
            if (emailMatcher.find()) {
                empEmailid = emailMatcher.group();
            }

            // Extract Mobile
            Pattern mobilePattern = Pattern.compile("\\+?\\d{1,4}[\\s-]?\\(?\\d{2,4}\\)?[\\s-]?\\d{3,4}[\\s-]?\\d{3,4}");
            Matcher mobileMatcher = mobilePattern.matcher(extractedText);
            if (mobileMatcher.find()) {
                empMobileNo = mobileMatcher.group();
            }

            System.out.println("Extracted Email ID: " + empEmailid);
            System.out.println("Extracted Mobile No: " + empMobileNo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
