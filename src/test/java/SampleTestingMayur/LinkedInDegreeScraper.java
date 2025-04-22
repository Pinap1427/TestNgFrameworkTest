package SampleTestingMayur;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class LinkedInDegreeScraper {
    public static void main(String[] args) throws InterruptedException {
        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to LinkedIn and log in
            driver.get("https://www.linkedin.com/");
            // Perform login
            driver.findElement(By.xpath("/html/body/nav/div/a[2]")).click();
            driver.findElement(By.id("username")).sendKeys("mayurmore2706@gmail.com");
            driver.findElement(By.id("password")).sendKeys("Mayur@123");
            driver.findElement(By.xpath("//*[@id=\"organic-div\"]/form/div[3]/button")).click();

            // Navigate to the 'Add education' section
            WebDriverWait wait = new WebDriverWait(driver, 20);
//            wait.until(ExpectedConditions.urlToBe("https://www.linkedin.com/feed/"));
            driver.get("https://www.linkedin.com/in/mayur-more-a68a18314/edit/forms/education/new/?profileFormEntryPoint=PROFILE_COMPLETION_HUB");

            // Wait for the degree dropdown to be clickable and click it
            WebElement degreeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("single-typeahead-entity-form-component-profileEditFormElement-EDUCATION-profileEducation-ACoAAE-ihPABoYDutEpg1rpZTz1Ub8mNiXIz-fA-1-degree")));
            degreeDropdown.click();

            // Wait for the options to be visible
            WebElement degreeOptionsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-basic-typeahead")));

            // Get all degree options
            List<WebElement> degreeOptions = degreeOptionsContainer.findElements(By.xpath(".//div[@role='option']"));
            for (WebElement option : degreeOptions) {
                System.out.println(option.getText());
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
