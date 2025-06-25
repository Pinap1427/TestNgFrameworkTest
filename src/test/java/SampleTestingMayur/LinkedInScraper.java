package SampleTestingMayur;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LinkedInScraper {

    public static void main(String[] args) {
        String url = "https://www.linkedin.com/in/mayur-more-9b9a46229/";
        Map<String, String> profileData = scrapeLinkedInProfile(url);

        System.out.println("\n===== Final Extracted Data =====");
        for (Map.Entry<String, String> entry : profileData.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue() != null ? entry.getValue() : "N/A"));
        }
    }

    public static Map<String, String> scrapeLinkedInProfile(String targetUrl) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
         options.addArguments("--headless"); // Enable for headless mode

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        boolean loaded = false;

        for (int attempt = 1; attempt <= 30; attempt++) {
            System.out.println("Attempt " + attempt + ": Navigating to profile...");
            driver.get(targetUrl);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String currentUrl = driver.getCurrentUrl().trim().toLowerCase();
            if (currentUrl.equals(targetUrl.trim().toLowerCase())) {
                System.out.println("✅ Target profile loaded successfully.");
                loaded = true;
                break;
            } else {
                System.out.println("🔄 URL(AuthWall) mismatch. Retrying...");
            }
        }

        if (!loaded) {
            System.out.println("❌ Failed to load the correct profile URL after 10 attempts.");
            driver.quit();
            throw new RuntimeException("Failed to load LinkedIn profile.");
        }

        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebDriverWait wait = new WebDriverWait(driver, 2);

            WebElement dismissButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[data-tracking-control-name='public_profile_contextual-sign-in-modal_modal_dismiss']")
            ));
            dismissButton.click();
            System.out.println("Dismissed profile popup.");
        } catch (Exception e) {
            System.out.println("No popup found or already dismissed.");
        }

        System.out.println("\n===== Extracted Profile Data =====");
        Map<String, String> data = new HashMap<>();

        data.put("Name", extractSafe(driver, By.xpath("//h1[normalize-space()]"), "Name"));
        data.put("Title", extractSafe(driver, By.xpath("//h2[contains(@class, 'top-card-layout__headline')]"), "Title"));
        data.put("Location", extractSafe(driver, By.xpath("//*[contains(text(),'Contact Info')]/preceding::*[not(self::button) and not(contains(@class,'visually-hidden')) and string-length(normalize-space(text())) > 2][1]"), "Location"));
        data.put("About", extractSafe(driver, By.xpath("//section[@data-section='summary']"), "About"));
        data.put("Experience", extractSafe(driver, By.xpath("//section[@data-section='experience']"), "Experience"));
        data.put("Education", extractSafe(driver, By.xpath("//section[@data-section='educationsDetails']"), "Education"));
        data.put("Certifications", extractSafe(driver, By.xpath("//section[@data-section='certifications']"), "Certifications"));
        data.put("Courses", extractSafe(driver, By.xpath("//section[@data-section='courses']"), "Courses"));
        data.put("Honors and Awards", extractSafe(driver, By.xpath("//section[@data-section='honors-and-awards']"), "Honors and Awards"));
        data.put("Languages", extractSafe(driver, By.xpath("//section[@data-section='languages']"), "Languages"));

        driver.quit();
        return data;
    }

    private static String extractSafe(WebDriver driver, By by, String label) {
        try {
            WebElement element = driver.findElement(by);
            String text = element.getText();
            System.out.println(label + ": " + (text.length() > 3000 ? text.substring(0, 3000) + "..." : text) + "\n");
            return text;
        } catch (Exception e) {
            System.out.println(label + ": Not found.\n");
            return null;
        }
    }
}
