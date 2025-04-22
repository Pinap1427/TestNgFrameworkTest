package SampleTestingMayur;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MailSendDaily {

    public static void main(String[] args) {
        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        
        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        try {
            // Open the website
            driver.get("https://accounts.zoho.com/signin?servicename=VirtualOffice&signupurl=https%3A%2F%2Fwww.zoho.com%2Fmail%2Fzohomail-pricing.html&serviceurl=https%3A%2F%2Fmail.zoho.com%2Fzm%2F#stream/group/852181306/views/unread");
            driver.manage().window().maximize();
            
            WebDriverWait wait = new WebDriverWait(driver, 10);
            
            // Enter email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email address or mobile number']")));
            emailField.sendKeys("mayur@linkcxo.com");
            
            // Click 'Next' button
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Next']")));
            nextButton.click();
            
            // Enter password
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter password']")));
            passwordField.sendKeys("Pinap@7781");
            
            // Click 'Sign in' button
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Sign in'])[2]")));
            signInButton.click();
            
            // Click 'New Mail'
            WebElement newMailButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='New Mail']")));
            newMailButton.click();
            newMailButton.click();
            
            // Enter recipient email
            WebElement recipientField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[4]/div/div/div/div[4]/div[1]/div")));
            recipientField.sendKeys("GK");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Quit the driver to close the browser
            driver.quit();
        }
    }
}
