package SampleTestingMayur;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LxAllJobsDataGet {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://linkcxo.com/login-signup");
        driver.navigate().refresh();
        Thread.sleep(1000);

        // Login
        driver.findElement(By.id("emailId")).sendKeys("kprabhat956@gmail.com");
        driver.findElement(By.xpath("//button[.='Continue']")).click();

        // OTP manually entered
        for (int i = 0; i < 6; i++) {
            driver.findElement(By.xpath("//input[@name='otp-input-" + i + "']")).sendKeys("4");
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[.='Jobs']")).click();
        Thread.sleep(3000);

        // Scroll till end
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int prevCount = 0, scrollTries = 0;

        while (true) {
            List<WebElement> jobs = driver.findElements(By.xpath("//div[@class='text-lg text-white line-clamp-1 font-semibold mb-2 ']"));
            int currentCount = jobs.size();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(3000);

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
        FileOutputStream fileOut = new FileOutputStream("JobData.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        

        System.out.println("Excel saved as JobData.xlsx");

//        driver.quit();
    }
}
