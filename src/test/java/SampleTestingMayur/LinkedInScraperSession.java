package SampleTestingMayur;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkedInScraperSession {

    public static String extractSafe(WebDriver driver, By selector, String label) {
        try {
            WebElement element = driver.findElement(selector);
            System.out.println(label + ": " + element.getText());
            return element.getText();
        } catch (NoSuchElementException e) {
            System.out.println(label + ": Not found.");
            return null;
        }
    }

    public static void scrapeSingleProfile(WebDriver driver, String targetUrl) {
        for (int attempt = 1; attempt <= 20; attempt++) {
            System.out.println("Attempt " + attempt + ": Opening tab for " + targetUrl);
            ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
            List<String> tabs = List.copyOf(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            driver.get(targetUrl);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (driver.getCurrentUrl().trim().equalsIgnoreCase(targetUrl.trim())) {
                System.out.println("✅ Target profile loaded successfully.");
                break;
            }

            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        // Example usage
        String profileUrl = "https://ae.linkedin.com/in/shabbirfakhruddin";
        scrapeSingleProfile(driver, profileUrl);

        // Don't forget to close the driver
        driver.quit();
    }
}
