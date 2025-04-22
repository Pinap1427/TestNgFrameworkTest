package SampleTestingMayur;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;

public class PdfReaderExample {
    public static void main(String[] args) throws IOException {
//    	WebDriverManager.chromedriver().setup();
//		ChromeOptions option = new ChromeOptions();
//		option.addArguments("--remote-allow-origins=*");
//
//		WebDriver driver = new ChromeDriver(option);
//
//		driver.manage().window().maximize();

//        System.setProperty("webdriver.chrome.driver", "pqath_to_chromedriver");
//        WebDriver driver = new ChromeDriver();
        
        // Navigate to the PDF link
//        driver.get("https://example.com/sample.pdf");
//        String pdfFilePath = "C:\\Users\\Mayur More\\Downloads\\Manoj_Chakravarthy.pdf";
        String pdfFilePath = "C:\\Users\\Mayur More\\Downloads\\MayurUKKuwait.pdf";
        // Download the file logic here (depends on browser settings)
        
        // Read the PDF file
        File pdfFile = new File(pdfFilePath);
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfContent = pdfStripper.getText(document);
        System.out.println("PDF Content: " + pdfContent);
        document.close();
//        driver.quit();
    }
}