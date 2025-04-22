package SampleTestingMayur;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
// Additional imports for handling file reading/writing, data processing, etc.

public class JobAggregator {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public JobAggregator() {
//        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(option);
        
        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, 10);  // 10 is the timeout in seconds

        driver.manage().window().maximize();
    }

    // Method to initialize API keys and track usage
    private List<String> apiKeys = Arrays.asList("API_KEY_1", "API_KEY_2", "API_KEY_3", "API_KEY_4");
    private int currentKeyIndex = 0;
    private int[] apiKeyUsage = new int[apiKeys.size()];
    private final int maxRequestsPerKey = 6;

    private void trackApiRequests() {
        apiKeyUsage[currentKeyIndex]++;
        if (apiKeyUsage[currentKeyIndex] >= maxRequestsPerKey) {
            System.out.println("Switching to next API key...");
            currentKeyIndex = (currentKeyIndex + 1) % apiKeys.size();
            apiKeyUsage[currentKeyIndex] = 0;
        }
    }

    // Method to perform login
    public void login(String phoneNumber, String otp) {
        try {
            driver.get("https://dev.linkcxo.com/job/create/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input']")))
                .sendKeys(phoneNumber);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            // Enter OTP
            for (int i = 1; i <= otp.length(); i++) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Digit " + i + "']")))
                    .sendKeys(String.valueOf(otp.charAt(i - 1)));
            }
            driver.findElement(By.xpath("//button[@type='submit']")).click();
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    // Method to scrape job details
    public void scrapeJobDetails(String searchQuery, int numJobs) {
        try {
            driver.get("https://www.naukri.com/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter skills / designations / companies']")))
                .sendKeys(searchQuery);
            // Additional job search interactions go here

            List<String> jobLinks = new ArrayList<>();
            while (jobLinks.size() < numJobs) {
                List<WebElement> jobs = driver.findElements(By.xpath("//a[@class='title ']"));
                for (WebElement job : jobs) {
                    String jobUrl = job.getAttribute("href");
                    if (!jobLinks.contains(jobUrl)) {
                        jobLinks.add(jobUrl);
                        if (jobLinks.size() >= numJobs) break;
                    }
                }
                // Navigate to next page if needed
                WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@class='styles_btn-secondary__2AsIP'])[1]")));
                nextPageButton.click();
            }

            System.out.println("Job URLs collected: " + jobLinks);

            for (String url : jobLinks) {
                driver.get(url);
                // Extract details and process job data
                // Perform NLP and match qualifications, experience, etc.
            }

        } catch (Exception e) {
            System.out.println("Error scraping job details: " + e.getMessage());
        }
    }

    // Main method to run the job aggregator
    public static void main(String[] args) {
        JobAggregator aggregator = new JobAggregator();
        aggregator.login("9162849798", "198989");
        aggregator.scrapeJobDetails("Developer", 10);
    }
}
