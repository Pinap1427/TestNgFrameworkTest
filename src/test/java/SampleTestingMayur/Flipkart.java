package SampleTestingMayur;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flipkart {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com");
        driver.navigate().refresh();
        Thread.sleep(1000);

        try {
            WebElement closeLoginPopup = driver.findElement(By.xpath("//span[contains(text(),'✕')]"));
            closeLoginPopup.click();
        } catch (NoSuchElementException e) {
            System.out.println("No login popup on Flipkart.");
        }

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("iPhone 16 Pro" + Keys.ENTER);

        WebDriverWait wait = new WebDriverWait(driver, 10);

        By firstProductLocator = By.xpath("(//div[contains(text(), 'Apple iPhone 16 Pro')])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(firstProductLocator)).click();

        switchToNewTab(driver);

        By specificationsLocator = By.xpath("//div[contains(text(),'Specifications')]");
        moveToElementWithRetry(driver, specificationsLocator);

        Thread.sleep(2000);
        takeScreenshot(driver, "product_details.png");

        By buyNowLocator = By.xpath("//button[@type='button']");
        wait.until(ExpectedConditions.elementToBeClickable(buyNowLocator)).click();

        takeScreenshot(driver, "login_prompt.png");
        driver.quit();
    }

    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File screenshotDir = new File("./screenshots/");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File(screenshotDir + "/" + fileName));
    }

    public static void switchToNewTab(WebDriver driver) {
        String currentWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(currentWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public static void moveToElementWithRetry(WebDriver driver, By locator) {
    	WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);
        int attempts = 0;
        while (attempts < 3) {  
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element = driver.findElement(locator);
                actions.moveToElement(element).perform();
                break; 
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying due to stale element");
            }
            attempts++;
        }
    }
}
