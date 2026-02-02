package SampleTestingMayur;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.search.SubjectTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutoStartExotalent {

    // STEP 1: Fetch OTP from Zoho Mail
    public static String fetchOtpFromZoho(String zohoEmail, String appPassword) {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.zoho.com", zohoEmail, appPassword);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new SubjectTerm("Exotalent - Login OTP"));
            if (messages.length == 0) {
                System.out.println("OTP email not found.");
                return null;
            }

            Message latest = messages[messages.length - 1];
            String content = latest.getContent().toString();

            // Extract 6-digit OTP using regex
            Pattern p = Pattern.compile("\\b\\d{6}\\b");
            Matcher m = p.matcher(content);
            if (m.find()) {
                return m.group();
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // STEP 2: Selenium automation
    public static void main(String[] args) throws InterruptedException {
        // Don't run on weekends
        java.time.DayOfWeek today = java.time.LocalDate.now().getDayOfWeek();
        if (today == java.time.DayOfWeek.SATURDAY || today == java.time.DayOfWeek.SUNDAY) {
            System.out.println("Weekend. Exiting.");
            return;
        }

        // Setup ChromeDriver path
//        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");

        // --- Replace below ---
        String exotalentEmail = "mayur@linkcxo.com";
        String zohoEmail = "mayur@linkcxo.com";
        String zohoAppPassword = "Pinap@1427"; // App password from Zoho
        String exotalentURL = "http://exotalent.in/company";

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Step 1: Open Exotalent portal
            driver.get(exotalentURL);
            Thread.sleep(2000);

            // Step 2: Enter email and click login
            driver.findElement(By.id("emailId")).sendKeys(exotalentEmail);
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Step 3: Wait for OTP to arrive
            System.out.println("Waiting 25 seconds for OTP to be received...");
            Thread.sleep(25000);

            // Step 4: Fetch OTP from Zoho
            String otp = fetchOtpFromZoho(zohoEmail, zohoAppPassword);
            if (otp == null || otp.length() != 6) {
                System.out.println("OTP not received or invalid. Exiting.");
                driver.quit();
                return;
            }

            System.out.println("OTP received: " + otp);

            // Step 5: Enter OTP digit by digit into fields named otp-input-0 to otp-input-5
//            for (int i = 0; i < otp.length(); i++) {
//                String digit = String.valueOf(otp.charAt(i));
//                String fieldName = "otp-input-" + i;
//                driver.findElement(By.name(fieldName)).sendKeys(digit);
//                Thread.sleep(300); // optional short delay
//            }
            
         // Step 5: Enter OTP using XPath loop
         // Wait until OTP inputs are visible
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//            WebDriverWait wait = new WebDriverWait(driver, 15); // timeout in seconds
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[2]")));
//
//            // Click the first input to focus it
//            driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
//
//            // Loop through each digit and enter it
//            for (int i = 1; i <= otp.length(); i++) {
//                String digit = String.valueOf(otp.charAt(i - 1));
//                String xpath = "(//input[@type='text'])[" + i + "]";
//                driver.findElement(By.xpath(xpath)).sendKeys(digit);
//                Thread.sleep(200); // short delay helps with field transitions
//            }

         // Wait until OTP inputs are visible
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='text'])[2]")));

            // Loop through OTP and enter each digit
            for (int i = 0; i < otp.length(); i++) {
                String digit = String.valueOf(otp.charAt(i));
                String xpath = "(//input[@type='text'])[" + (i + 2) + "]";  // Start from index 2
                driver.findElement(By.xpath(xpath)).sendKeys(digit);
                Thread.sleep(200); // Optional short pause
            }


            // Step 6: Click Login As button
//            driver.findElement(By.xpath("//button[contains(text(),'Login As')]")).click();
            Thread.sleep(4000);

            // Step 7: Click Start button
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
            
//            driver.findElement(By.xpath("//a[.='My Profile']"));
//            Thread.sleep(3000);
//            driver.findElement(By.xpath("//a[.='Dashboard']"));
//            Thread.sleep(10000);
//            driver.navigate().refresh();
            
            
//            driver.findElement(By.xpath("//button[contains(text(),'Start')]")).click();
//            System.out.println("✅ Logged in and clicked Start.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(5000); // Optional delay to observe result
//            driver.quit();
        }
    }
}
