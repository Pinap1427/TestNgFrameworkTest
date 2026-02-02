package SampleTestingMayur;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestingExample {
public static void main(String [] args) {
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();
    	driver.get("https://linkcxo.com/");
	driver.manage().window().maximize();
	WebElement login = driver.findElement(By.xpath("//button[text()=\"Login\"]"));
	login.click();
	WebElement email = driver.findElement(By.xpath("//input[@id=\"emailId\"]"));
	email.sendKeys("prabhat@linkcxo.com");
	WebElement conn = driver.findElement(By.xpath("//button[@type = \"submit\"]"));
	conn.click();
	WebElement email1 = driver.findElement(By.xpath("//input[@name=\"otp-input-0\"]"));
	WebElement email2 = driver.findElement(By.xpath("//input[@name=\"otp-input-1\"]"));
	WebElement email3 = driver.findElement(By.xpath("//input[@name=\"otp-input-2\"]"));
	WebElement email4 = driver.findElement(By.xpath("//input[@name=\"otp-input-3\"]"));
	WebElement email5 = driver.findElement(By.xpath("//input[@name=\"otp-input-4\"]"));
	WebElement email6 = driver.findElement(By.xpath("//input[@name=\"otp-input-5\"]"));

	email1.sendKeys("4");
	email2.sendKeys("4");
	email3.sendKeys("4");
	email4.sendKeys("4");
	email5.sendKeys("4");
	email6.sendKeys("4");
	
	
	


	
}
}
