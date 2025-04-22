package SampleTestingMayur;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.time.Duration;
import java.util.List;

import javax.swing.text.TabableView;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.functions.ExpectedCondition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExoTalentTest {

	public static void main(String[] args) throws InterruptedException, AWTException {
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
//		 option.addArguments("--disable-blink-features=AutomationControlled");
		  option.addArguments("--disable-blink-features=AutomationControlled");
	        option.addArguments("--disable-infobars");
	        option.addArguments("--disable-popup-blocking");
	        option.addArguments("--disable-dev-shm-usage");
	        option.addArguments("--no-sandbox");
	        option.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	        option.setExperimentalOption("useAutomationExtension", false);

		WebDriver driver = new ChromeDriver(option);
		 JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	        // Execute script to override the 'navigator.webdriver' property
	        jsExecutor.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
		
		driver.manage().window().maximize();

//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

		WebDriverWait wait = new WebDriverWait(driver, 20);

		driver.get("https://ashy-water-006dcb610.4.azurestaticapps.net/company");
		Thread.sleep(5000);
	driver.findElement(By.xpath("//input[@id='emailId']")).sendKeys("cr1@apmosys.com");
	driver.findElement(By.xpath("//button[@type='submit']")).click();
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[1]")).sendKeys("4");
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[2]")).sendKeys("4");
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[3]")).sendKeys("4");
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[4]")).sendKeys("4");
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[5]")).sendKeys("4");
//	driver.findElement(By.xpath("(//input[@type=\"number\"])[6]")).sendKeys("4");
//	driver.findElement(By.xpath("//button[normalize-space()='Verify']")).click();
	Thread.sleep(20000);
//	driver.findElement(By.xpath("//a[.='Job Board']")).click();
//	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
	
	 WebElement dropdown = driver.findElement(By.xpath("(//div[@class=\"css-1up7coo\"])[3]"));
	 dropdown.click();

     // Wait for options to load (use an explicit wait)
     WebDriverWait wait1 = new WebDriverWait(driver, 10);
//     wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
//             By.xpath("(//div[contains(@class, ' css-1up7coo')])[3]"))); // Adjust the XPath if needed
   
		/*
		 * // Get all options under the dropdown List<WebElement> options =
		 * driver.findElements(By.xpath("(//div[@role='option'])"));
		 */

     // Print each option's text (locations)
//     System.out.println("Locations in the dropdown:");
//     for (WebElement option1 : options) {
//         System.out.println(option1.getText());
//         
//         
//     }//old method
     
//     System.out.println("Locations in the dropdown:");
//     for (WebElement option1 : options) {
//         System.out.println(option1.getText());	
//         // Optionally, click to select a city, for example:
//         if (option1.getText().equals("Desired City Name")) {
//             option1.click();  // This will click on the desired option
//             break;  // Exit the loop after selecting the option
//         }
//     }
//     for (int i = 1; i <= 999; i++) {
//         try {
//             // Dynamically construct the XPath for each option
//             String xpath = "(//div[@class=\"h-5 w-5 rounded-sm border border-exgray-300\"])";
//             
//             // Find the option element
//             WebElement cityOption = driver.findElement(By.xpath(xpath));
//             
//             // Click the option
//             cityOption.click();
//
//             // Optional: Add a small delay for stability
//             Thread.sleep(100);
//         } catch (Exception e) {
//             System.out.println("Option not found for index: " + i);
//         }
//     }
     
     List<WebElement> cityOptions = driver.findElements(By.xpath("//div[@class='h-5 w-5 rounded-sm border border-exgray-300']"));

     // Counter for successfully selected options
     int selectedOptions = 0;

     // Loop through all the city options and click each one
     for (WebElement cityOption : cityOptions) {
         try {
             // Scroll into view if necessary (for lazy loading)
             ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityOption);

             // Click the checkbox
             cityOption.click();
             selectedOptions++;

             // Optional: Add a small delay for stability
             Thread.sleep(100);
         } catch (Exception e) {
             System.out.println("Failed to select a city: " + e.getMessage());
         }
     }

     // Log the total number of successfully selected cities
     System.out.println("Total cities selected: " + selectedOptions);

     
     Thread.sleep(120000);
		
		
//	WebElement JoinUsBTn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.='Join As']")));
//	JoinUsBTn.click();
//	
//	Thread.sleep(1000);
//	
//	WebElement CompanyBtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[.='Company']")));
//	CompanyBtn.click();
//	System.out.println("User is able to click on Company button");
//	
//	
//	WebElement EnterEmail=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"emailId\"]")));
//	EnterEmail.sendKeys("cr1@apmosys.com");
//	
//	WebElement LoginViaOTPBtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.='Login Via OTP']")));
//	LoginViaOTPBtn.click();
//	
//	
//	
//	
//	WebElement Otp1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type=\"number\"])[1]")));
//	Otp1.sendKeys("4");
//	
//	
//	WebElement Otp2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[2]")));
//	Otp2.sendKeys("4");
//	
//	WebElement Otp3=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[3]")));
//	Otp3.sendKeys("4");
//	
//	WebElement Otp4=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[4]")));
//	Otp4.sendKeys("4");
//	
//	WebElement Otp5=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[5]")));
//	Otp5.sendKeys("4");
//	
//	WebElement Otp6=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[6]")));
//	Otp6.sendKeys("4");
//	
//	
//	
//	WebElement VerifyOtpBtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.='Verify']")));
//	VerifyOtpBtn.click();
//	
//	Thread.sleep(1000);
//	
//	driver.findElement(By.xpath("//button[normalize-space()='Jobs']")).click();
//	
//	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
//	
//	driver.findElement(By.id("jobTitle")).sendKeys("New Senior Project Manager ");
//	
//	driver.findElement(By.id("companyName")).sendKeys("ApMoSys Technologies Pvt Ltd");
//	
//
////WebElement tab=	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\" css-y5ynmu\"])[1]")));
////tab.click();
//
//	Robot rb = new Robot();
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[3]")).click();
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[6]")).click();
//	
//	driver.findElement(By.id("skill")).sendKeys("Pyhton");
//	driver.findElement(By.xpath("//button[.='Add']")).click();
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	
//	
//	
////	driver.findElement(By.xpath("//*[contains(text(), 'Full Time')]"));
//	
//	Thread.sleep(5000);
//	
//	driver.findElement(By.id("jobDescription")).sendKeys("We are seeking an experienced and visionary Senior Product Manager to lead the product development lifecycle from ideation to launch. You will collaborate with cross-functional teams, including engineering, marketing, sales, and customer support, to ensure that our products align with business goals and meet customer needs. The ideal candidate should have a strong understanding of market trends, user-centered design principles, and the ability to manage product roadmaps while driving innovation.\r\n"
//			+ "\r\n"
//			+ "Key Responsibilities:\r\n"
//			+ "Product Strategy & Roadmap:\r\n"
//			+ "\r\n"
//			+ "Define the overall product vision and strategy based on business objectives, market research, and customer feedback.\r\n"
//			+ "Develop and manage detailed product roadmaps that align with business goals and priorities.\r\n"
//			+ "Collaborate with leadership to set clear, measurable objectives and key results (OKRs) for the product team.\r\n"
//			+ "Market Research & Competitive Analysis:\r\n"
//			+ "\r\n"
//			+ "Conduct market analysis to understand the competitive landscape, emerging trends, and customer needs.\r\n"
//			+ "Define customer personas and use data insights to prioritize product features.\r\n"
//			+ "Stay updated on market trends and make informed decisions on product features.\r\n"
//			+ "Cross-Functional Collaboration:\r\n"
//			+ "\r\n"
//			+ "Work closely with engineering, design, and marketing teams to develop products that align with customer needs and company goals.\r\n"
//			+ "Drive the execution of product initiatives from concept to launch by ensuring proper alignment across teams.\r\n"
//			+ "Collaborate with stakeholders to gather requirements, set expectations, and provide regular updates on progress.\r\n"
//			+ "Customer Engagement:\r\n"
//			+ "\r\n");
//	
//	
////	driver.findElement(By.xpath("//button[.='Post Job ']"));
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(4000);
//	
//	WebElement elementscr=driver.findElement(By.xpath("//p[.='Navi Mumbai']"));
//	 JavascriptExecutor js = (JavascriptExecutor) driver;
//     js.executeScript("arguments[0].scrollIntoView(true);", elementscr);
//
//     // Optionally, pause for visual confirmation
//     try {
//         Thread.sleep(3000);
//     } catch (InterruptedException e) {
//         e.printStackTrace();
//     }
//	
//	driver.findElement(By.xpath("//button[normalize-space()='Jobs']")).click();
//
//	
//	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
//	
//	
//driver.findElement(By.id("jobTitle")).sendKeys("Team LEad");
//	
//	driver.findElement(By.id("companyName")).sendKeys("ApMoSys Technologies Pvt Ltd");
//	
//
////WebElement tab=	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\" css-y5ynmu\"])[1]")));
////tab.click();
//
////	Robot rb = new Robot();
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[3]")).click();
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[6]")).click();
//	
//	driver.findElement(By.id("skill")).sendKeys("Java");
//	driver.findElement(By.xpath("//button[.='Add']")).click();
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	
//	
//	
////	driver.findElement(By.xpath("//*[contains(text(), 'Full Time')]"));
//	
//	Thread.sleep(5000);
//	
//	driver.findElement(By.id("jobDescription")).sendKeys("We are seeking an experienced and visionary Senior Product Manager to lead the product development lifecycle from ideation to launch. You will collaborate with cross-functional teams, including engineering, marketing, sales, and customer support, to ensure that our products align with business goals and meet customer needs. The ideal candidate should have a strong understanding of market trends, user-centered design principles, and the ability to manage product roadmaps while driving innovation.\r\n"
//			+ "\r\n"
//			+ "Key Responsibilities:\r\n"
//			+ "Product Strategy & Roadmap:\r\n"
//			+ "\r\n"
//			+ "Define the overall product vision and strategy based on business objectives, market research, and customer feedback.\r\n"
//			+ "Develop and manage detailed product roadmaps that align with business goals and priorities.\r\n"
//			+ "Collaborate with leadership to set clear, measurable objectives and key results (OKRs) for the product team.\r\n"
//			+ "Market Research & Competitive Analysis:\r\n"
//			+ "\r\n"
//			+ "Conduct market analysis to understand the competitive landscape, emerging trends, and customer needs.\r\n"
//			+ "Define customer personas and use data insights to prioritize product features.\r\n"
//			+ "Stay updated on market trends and make informed decisions on product features.\r\n"
//			+ "Cross-Functional Collaboration:\r\n"
//			+ "\r\n"
//			+ "Work closely with engineering, design, and marketing teams to develop products that align with customer needs and company goals.\r\n"
//			+ "Drive the execution of product initiatives from concept to launch by ensuring proper alignment across teams.\r\n"
//			+ "Collaborate with stakeholders to gather requirements, set expectations, and provide regular updates on progress.\r\n"
//			+ "Customer Engagement:\r\n"
//			+ "\r\n");
//	
//	
////	driver.findElement(By.xpath("//button[.='Post Job ']"));
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//
//	
//	///////////////////////////#3rd Job///////////////////////////////////////////
//	
//	WebElement elementscr1=driver.findElement(By.xpath("//p[.='Navi Mumbai']"));
////	 JavascriptExecutor js = (JavascriptExecutor) driver;
//    js.executeScript("arguments[0].scrollIntoView(true);", elementscr1);
//
//    // Optionally, pause for visual confirmation
//    try {
//        Thread.sleep(3000);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//	
//	driver.findElement(By.xpath("//button[normalize-space()='Jobs']")).click();
//
//	
//	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
//	
//	
//driver.findElement(By.id("jobTitle")).sendKeys("Senir React js Web Developer");
//	
//	driver.findElement(By.id("companyName")).sendKeys("ApMoSys Technologies Pvt Ltd");
//	
//
////WebElement tab=	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\" css-y5ynmu\"])[1]")));
////tab.click();
//
////	Robot rb = new Robot();
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[3]")).click();
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[6]")).click();
//	
//	driver.findElement(By.id("skill")).sendKeys("Java");
//	driver.findElement(By.xpath("//button[.='Add']")).click();
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	
//	
//	
////	driver.findElement(By.xpath("//*[contains(text(), 'Full Time')]"));
//	
//	Thread.sleep(5000);
//	
//	driver.findElement(By.id("jobDescription")).sendKeys("We are seeking an experienced and visionary Senior Product Manager to lead the product development lifecycle from ideation to launch. You will collaborate with cross-functional teams, including engineering, marketing, sales, and customer support, to ensure that our products align with business goals and meet customer needs. The ideal candidate should have a strong understanding of market trends, user-centered design principles, and the ability to manage product roadmaps while driving innovation.\r\n"
//			+ "\r\n"
//			+ "Key Responsibilities:\r\n"
//			+ "Product Strategy & Roadmap:\r\n"
//			+ "\r\n"
//			+ "Define the overall product vision and strategy based on business objectives, market research, and customer feedback.\r\n"
//			+ "Develop and manage detailed product roadmaps that align with business goals and priorities.\r\n"
//			+ "Collaborate with leadership to set clear, measurable objectives and key results (OKRs) for the product team.\r\n"
//			+ "Market Research & Competitive Analysis:\r\n"
//			+ "\r\n"
//			+ "Conduct market analysis to understand the competitive landscape, emerging trends, and customer needs.\r\n"
//			+ "Define customer personas and use data insights to prioritize product features.\r\n"
//			+ "Stay updated on market trends and make informed decisions on product features.\r\n"
//			+ "Cross-Functional Collaboration:\r\n"
//			+ "\r\n"
//			+ "Work closely with engineering, design, and marketing teams to develop products that align with customer needs and company goals.\r\n"
//			+ "Drive the execution of product initiatives from concept to launch by ensuring proper alignment across teams.\r\n"
//			+ "Collaborate with stakeholders to gather requirements, set expectations, and provide regular updates on progress.\r\n"
//			+ "Customer Engagement:\r\n"
//			+ "\r\n");
//	
//	
////	driver.findElement(By.xpath("//button[.='Post Job ']"));
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	/////////////////////////////////////////#4th JOb ///////////////////
//	
//	
//	
//	WebElement elementscr2=driver.findElement(By.xpath("//p[.='Navi Mumbai']"));
////	 JavascriptExecutor js = (JavascriptExecutor) driver;
//    js.executeScript("arguments[0].scrollIntoView(true);", elementscr2);
//
//    // Optionally, pause for visual confirmation
//    try {
//        Thread.sleep(3000);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//	
//	driver.findElement(By.xpath("//button[normalize-space()='Jobs']")).click();
//
//	
//	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
//	
//	
//driver.findElement(By.id("jobTitle")).sendKeys("Senior Android Mobile Developer - iOS/Android Apps");
//	
//	driver.findElement(By.id("companyName")).sendKeys("ApMoSys Technologies Pvt Ltd");
//	
//
////WebElement tab=	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class=\" css-y5ynmu\"])[1]")));
////tab.click();
//
////	Robot rb = new Robot();
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[3]")).click();
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	
//	driver.findElement(By.xpath("(//div[@class=\" css-y5ynmu\"])[6]")).click();
//	
//	driver.findElement(By.id("skill")).sendKeys("Java");
//	driver.findElement(By.xpath("//button[.='Add']")).click();
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
//	
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_DOWN);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	
//	
//	
////	driver.findElement(By.xpath("//*[contains(text(), 'Full Time')]"));
//	
//	Thread.sleep(5000);
//	
//	driver.findElement(By.id("jobDescription")).sendKeys("We are seeking an experienced and visionary Senior Product Manager to lead the product development lifecycle from ideation to launch. You will collaborate with cross-functional teams, including engineering, marketing, sales, and customer support, to ensure that our products align with business goals and meet customer needs. The ideal candidate should have a strong understanding of market trends, user-centered design principles, and the ability to manage product roadmaps while driving innovation.\r\n"
//			+ "\r\n"
//			+ "Key Responsibilities:\r\n"
//			+ "Product Strategy & Roadmap:\r\n"
//			+ "\r\n"
//			+ "Define the overall product vision and strategy based on business objectives, market research, and customer feedback.\r\n"
//			+ "Develop and manage detailed product roadmaps that align with business goals and priorities.\r\n"
//			+ "Collaborate with leadership to set clear, measurable objectives and key results (OKRs) for the product team.\r\n"
//			+ "Market Research & Competitive Analysis:\r\n"
//			+ "\r\n"
//			+ "Conduct market analysis to understand the competitive landscape, emerging trends, and customer needs.\r\n"
//			+ "Define customer personas and use data insights to prioritize product features.\r\n"
//			+ "Stay updated on market trends and make informed decisions on product features.\r\n"
//			+ "Cross-Functional Collaboration:\r\n"
//			+ "\r\n"
//			+ "Work closely with engineering, design, and marketing teams to develop products that align with customer needs and company goals.\r\n"
//			+ "Drive the execution of product initiatives from concept to launch by ensuring proper alignment across teams.\r\n"
//			+ "Collaborate with stakeholders to gather requirements, set expectations, and provide regular updates on progress.\r\n"
//			+ "Customer Engagement:\r\n"
//			+ "\r\n");
//	
//	
////	driver.findElement(By.xpath("//button[.='Post Job ']"));
//	
//	rb.keyPress(KeyEvent.VK_TAB);
//	rb.keyPress(KeyEvent.VK_ENTER);
//	
//	Thread.sleep(2000);
//	
////	driver.findElement(By.xpath("//a[.=' Create Job']")).click();
	
	driver.quit();
	}
}
