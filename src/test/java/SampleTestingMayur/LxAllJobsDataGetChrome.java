package SampleTestingMayur;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LxAllJobsDataGetChrome {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();

        // Launch Chrome with visible UI (Non-headless)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new"); // Headless disabled for debugging
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://linkcxo.com/login-signup");
        driver.navigate().refresh();
        Thread.sleep(1000);

        // Login
        driver.findElement(By.id("emailId")).sendKeys("kprabhat956@gmail.com");
        driver.findElement(By.xpath("//button[.='Continue']")).click();

        // OTP manually entered (static: 444444)
        for (int i = 0; i < 6; i++) {
            driver.findElement(By.xpath("//input[@name='otp-input-" + i + "']")).sendKeys("4");
        }

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement jobsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[.='Jobs']")));
        jobsButton.click();
        Thread.sleep(3000);

        // Scroll till end with 3-second wait after each scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int prevCount = 0, scrollTries = 0;

        while (true) {
            List<WebElement> jobs = driver.findElements(By.xpath("//div[@class='text-lg text-white line-clamp-1 font-semibold mb-2 ']"));
            int currentCount = jobs.size();
            System.out.println("Jobs found so far: " + currentCount);

            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(3000); // Wait 3 seconds after scroll to allow content to load

            if (currentCount == prevCount) {
                scrollTries++;
                if (scrollTries >= 3) break;
            } else {
                scrollTries = 0;
            }
            prevCount = currentCount;
        }

        System.out.println("Finished scrolling. Total jobs loaded: " + prevCount);

        // Extract data
        List<WebElement> titles = driver.findElements(By.xpath("//div[@class='text-lg text-white line-clamp-1 font-semibold mb-2 ']"));
        List<WebElement> industries = driver.findElements(By.xpath("//h4[@class='text-sm text-lxgray-200  line-clamp-1']"));
        List<WebElement> locations = driver.findElements(By.xpath("//p[@class='text-sm text-lxgray-200 line-clamp-1']"));

        // Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Job Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Job Title");
        header.createCell(1).setCellValue("Industry");
        header.createCell(2).setCellValue("Location");

        for (int i = 0; i < titles.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(titles.get(i).getText());
            row.createCell(1).setCellValue(i < industries.size() ? industries.get(i).getText() : "N/A");
            row.createCell(2).setCellValue(i < locations.size() ? locations.get(i).getText() : "N/A");
        }

        // Save Excel
        FileOutputStream fileOut = new FileOutputStream("JobData1.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        System.out.println("Excel saved as JobData1.xlsx");

//        driver.quit();
    }
}
