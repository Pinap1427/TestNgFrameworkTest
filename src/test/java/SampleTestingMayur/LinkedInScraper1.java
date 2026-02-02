package SampleTestingMayur;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;      // Selenium's exception
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkedInScraper1 {

    // ---------------- Helper Functions ----------------

    public static boolean linkedinLogin(WebDriver driver, String username, String password) {
        driver.get("https://www.linkedin.com/login");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5); // Selenium 3 style
            WebElement userField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("username"))
            );
            userField.sendKeys(username);

            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("global-nav-search")));
            System.out.println("✅ Login successful");
            return true;

        } catch (TimeoutException e) {
            System.out.println("⚠ Login failed or took too long");
            return false;
        }
    }

    private static void scrollToBottom(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    private static String textForCSV(String s) {
        if (s == null) return "N/A";
        // keep it simple: replace newlines and commas for CSV safety
        return s.trim().replace("\n", " ").replace(",", " ");
    }

    public static String extractSafe(WebDriver driver, By locator, String label) {
        try {
            WebElement element = driver.findElement(locator);
            String text = element.getText();
            return textForCSV(text.isEmpty() ? "N/A" : text);
        } catch (NoSuchElementException e) {
            System.out.println("[INFO] " + label + " not found");
            return "N/A";
        }
    }

    private static void clickIfPresent(WebElement root, By locator) {
        try {
            WebElement el = root.findElement(locator);
            el.click();
        } catch (NoSuchElementException ignored) {}
    }

    private static String collectListItems(WebDriver driver, By itemsLocator) {
        List<WebElement> items = driver.findElements(itemsLocator);
        if (items.isEmpty()) return "N/A";
        StringBuilder sb = new StringBuilder();
        for (WebElement li : items) {
            sb.append(li.getText().replace("\n", " | ")).append(" || ");
        }
        return textForCSV(sb.toString());
    }

    private static String extractAbout(WebDriver driver) {
        try {
            WebElement aboutSection = driver.findElement(By.xpath(
                    "//section[contains(@id,'about') or .//h2[contains(.,'About')]]"
            ));
            // expand if collapsed
            clickIfPresent(aboutSection, By.xpath(".//button[contains(translate(., 'SEE MORE', 'see more'),'see more')]"));
            return textForCSV(aboutSection.getText());
        } catch (NoSuchElementException e) {
            return "N/A";
        }
    }

    // ✅ Updated Experience Extraction
//	private static String extractExperience(WebDriver driver) {
		/*
		 * try { // Locate Experience section WebElement expSection =
		 * driver.findElement(By.xpath(
		 * "//section[contains(@id,'experience') or .//h2[contains(.,'Experience')]]"
		 * ));
		 * 
		 * // If "Show all" button exists, click it List<WebElement> showAllBtns =
		 * expSection.findElements(By.xpath(".//button[.//span[contains(.,'Show all')]]"
		 * )); if (!showAllBtns.isEmpty()) { showAllBtns.get(0).click();
		 * 
		 * // Wait for the modal overlay to load WebDriverWait wait = new
		 * WebDriverWait(driver, 5);
		 * wait.until(ExpectedConditions.presenceOfElementLocated(
		 * By.xpath("//div[@role='dialog']//ul[contains(@class,'pvs-list')]") ));
		 * 
		 * // Collect all items inside modal List<WebElement> items =
		 * driver.findElements(
		 * By.xpath("//div[@role='dialog']//ul[contains(@class,'pvs-list')]/li") );
		 * 
		 * StringBuilder sb = new StringBuilder(); for (WebElement li : items) {
		 * sb.append(li.getText().replace("\n", " | ")).append(" || "); }
		 * 
		 * // Close modal (optional) try { WebElement closeBtn =
		 * driver.findElement(By.xpath(
		 * "//div[@role='dialog']//button[@aria-label='Dismiss']")); closeBtn.click(); }
		 * catch (Exception ignored) {}
		 * 
		 * return sb.length() > 0 ? sb.toString() : "N/A"; } else { // No "Show all" →
		 * scrape directly from section return collectListItems(driver, By.
		 * xpath("//section[contains(@id,'experience') or .//h2[contains(.,'Experience')]]//li"
		 * ) ); }
		 * 
		 * } catch (NoSuchElementException e) { return "N/A"; }
//		 */
//✅ Updated Experience Extraction with scrolling inside modal
//private static String extractExperience(WebDriver driver) {
// try {
//     // Locate Experience section
//     WebElement expSection = driver.findElement(By.xpath(
//             "//section[contains(@id,'experience') or .//h2[contains(.,'Experience')]]"
//     ));
//
//     // If "Show all" button exists, click it
//     List<WebElement> showAllBtns = expSection.findElements(By.xpath(".//button[.//span[contains(.,'Show all')]]"));
//     if (!showAllBtns.isEmpty()) {
//         showAllBtns.get(0).click();
//
//         WebDriverWait wait = new WebDriverWait(driver, 8);
//         wait.until(ExpectedConditions.presenceOfElementLocated(
//                 By.xpath("//div[@role='dialog']//ul[contains(@class,'pvs-list')]")
//         ));
//
//         // Scroll inside modal to load all experiences
//         WebElement modal = driver.findElement(By.xpath("//div[@role='dialog']//div[contains(@class,'scaffold-finite-scroll')]"));
//         JavascriptExecutor js = (JavascriptExecutor) driver;
//         long lastHeight = (long) js.executeScript("return arguments[0].scrollHeight;", modal);
//
//         while (true) {
//             js.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight);", modal);
//             Thread.sleep(1000);
//             long newHeight = (long) js.executeScript("return arguments[0].scrollHeight;", modal);
//             if (newHeight == lastHeight) break;
//             lastHeight = newHeight;
//         }
//
//         // Collect all items inside modal
//         List<WebElement> items = driver.findElements(
//                 By.xpath("//div[@role='dialog']//ul[contains(@class,'pvs-list')]/li")
//         );
//
//         StringBuilder sb = new StringBuilder();
//         for (WebElement li : items) {
//             sb.append(li.getText().replace("\n", " | ")).append(" || ");
//         }
//
//         // Close modal (optional)
//         try {
//             WebElement closeBtn = driver.findElement(By.xpath("//div[@role='dialog']//button[@aria-label='Dismiss']"));
//             closeBtn.click();
//         } catch (Exception ignored) {}
//
//         return sb.length() > 0 ? sb.toString() : "N/A";
//     } else {
//         // No "Show all" → scrape directly from section
//         return collectListItems(driver,
//                 By.xpath("//section[contains(@id,'experience') or .//h2[contains(.,'Experience')]]//li")
//         );
//     }
//
// } catch (NoSuchElementException e) {
//     return "N/A";
// } catch (InterruptedException e) {
//     Thread.currentThread().interrupt();
//     return "N/A";
// }
//}
    
    
    private static String extractExperience(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);

            // Try to find "Show all experiences" button
            List<WebElement> showAllBtns = driver.findElements(
                    By.cssSelector("a[id*='see-all-experiences']")
            );

            StringBuilder sb = new StringBuilder();

            if (!showAllBtns.isEmpty()) {
                // ✅ Case 1: Show all button exists → click it
                WebElement showAll = showAllBtns.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", showAll);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAll);
                System.out.println("✅ Clicked Show all experiences");

                // Wait for new section to load
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//section[@class='artdeco-card pb3'])[1]"))
                );

                List<WebElement> items = driver.findElements(
                        By.xpath("(//section[@class='artdeco-card pb3'])[1]//li")
                );

                for (WebElement li : items) {
                    sb.append(li.getText().replace("\n", " | ")).append(" || ");
                }

            } else {
                // ✅ Case 2: No show all button → use fallback div
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("(//div[@class='lXQCUuaWPXeieEMgcbMNPiPnFfueBPcFowU'])[2]"))
                );

                List<WebElement> items = driver.findElements(
                        By.xpath("(//div[@class='lXQCUuaWPXeieEMgcbMNPiPnFfueBPcFowU'])[2]//li")
                );

                for (WebElement li : items) {
                    sb.append(li.getText().replace("\n", " | ")).append(" || ");
                }
            }

            return sb.length() > 0 ? sb.toString() : "N/A";

        } catch (Exception e) {
            System.out.println("❌ Error extracting experience: " + e.getMessage());
            return "N/A";
        }
    }



    public static Map<String, String> scrapeProfile(WebDriver driver, String url) {
        System.out.println("\nOpening profile: " + url);
        driver.get(url);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ph5.pb5")));
            System.out.println("✅ Profile loaded");
        } catch (TimeoutException e) {
            System.out.println("⚠ Profile load timeout");
            return null;
        }

        // scroll a bit so lazy-loaded sections appear
        scrollToBottom(driver);

        Map<String, String> data = new LinkedHashMap<>();
        data.put("URL", url);
        data.put("Name", extractSafe(driver, By.xpath("//h1[normalize-space()]"), "Name"));
        data.put("Title", extractSafe(driver, By.xpath("//div[@class='text-body-medium break-words']"), "Title"));
        data.put("Location", extractSafe(driver, By.xpath("//span[@class='text-body-small inline t-black--light break-words']"), "Location"));
        data.put("About", extractAbout(driver));
        data.put("Experience", extractExperience(driver));

        // Education
        data.put("Education", collectListItems(driver,
                By.xpath("//section[contains(@id,'education') or .//h2[contains(.,'Education')]]//li")
        ));

        // Certifications
        data.put("Certifications", collectListItems(driver,
                By.xpath("//section[contains(@id,'certification') or .//h2[contains(.,'Licenses') or contains(.,'Certifications')]]//li")
        ));

        // Courses
        data.put("Courses", collectListItems(driver,
                By.xpath("//section[contains(@id,'courses') or .//h2[contains(.,'Courses')]]//li")
        ));

        // Honors & Awards
        data.put("Honors and Awards", collectListItems(driver,
                By.xpath("//section[contains(@id,'honor') or contains(@id,'award') or .//h2[contains(.,'Honors') or contains(.,'Awards')]]//li")
        ));

        // Languages
        data.put("Languages", collectListItems(driver,
                By.xpath("//section[contains(@id,'languages') or .//h2[contains(.,'Languages')]]//li")
        ));

        // Console dump
        for (Map.Entry<String, String> entry : data.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return data;
    }

    public static List<Map<String, String>> openAndScrapeProfiles(WebDriver driver, List<String> profileLinks) {
        List<Map<String, String>> allData = new ArrayList<>();
        for (String link : profileLinks) {
            Map<String, String> profileData = scrapeProfile(driver, link);
            if (profileData != null) {
                allData.add(profileData);
            }
            try {
                Thread.sleep(1500); // polite throttling
            } catch (InterruptedException ignored) {}
        }
        return allData;
    }

    public static void saveToCSV(List<Map<String, String>> results, String fileName) {
        if (results.isEmpty()) return;

        try (FileWriter csvWriter = new FileWriter(fileName)) {
            // header
            Set<String> headers = results.get(0).keySet();
            csvWriter.append(String.join(",", headers)).append("\n");

            // rows
            for (Map<String, String> row : results) {
                List<String> values = new ArrayList<>(row.values());
                csvWriter.append(String.join(",", values)).append("\n");
            }
            System.out.println("\n✅ Data saved to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Main Execution ----------------
    public static void main(String[] args) {
        String username = "mayurmore2706@gmail.com";
        String password = "Mayur@2706";
        List<String> profileLinks = Arrays.asList(
                "https://www.linkedin.com/in/prerakm/"
//                "https://www.linkedin.com/in/mayur-more-9b9a46229/",
//                "https://www.linkedin.com/in/abhishek-k-556704245/"
        );

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        try {
            // Check if already logged in
            driver.get("https://www.linkedin.com/feed/");
            try {
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("global-nav-search")));
                System.out.println("🔑 Already logged in");
            } catch (TimeoutException e) {
                System.out.println("Not logged in, attempting login...");
                if (!linkedinLogin(driver, username, password)) {
                    driver.quit();
                    throw new RuntimeException("❌ Could not login");
                }
            }

            // Scrape profiles
            List<Map<String, String>> results = openAndScrapeProfiles(driver, profileLinks);

            // Save to CSV
            saveToCSV(results, "linkedin_profiles.csv");

        } finally {
//            driver.quit();
        }
    }
}
