package SampleTestingMayur;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LaxmiHoliday2 {

	public static void main(String[] args) throws Exception {

//		System.setProperty("webdriver.chrome.driver",
//				"C:/Users/vj847/Downloads/chromedriver-win64 (1)/chromedriver-win64/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(option);

		driver.manage().window().maximize();

//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

		WebDriverWait wait = new WebDriverWait(driver, 20);

		driver.get("https://www.laxmiholidays.com/index.html");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollBy(0,200)");

		WebElement origin = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("(//div[@class='ant-select-selection__placeholder'])[1]")));
		origin.click();
		Thread.sleep(3000);

		Actions act = new Actions(driver);
		act.pause(Duration.ofSeconds(3)).sendKeys("manali");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='Manali']"))).click();

		Thread.sleep(2000);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//li[text()='Delhi'])[2]"))).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@title=\"March 13, 2024\"]"))).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("gt-search"))).click();

		js.executeScript("window.scrollBy(0,100)");

		String text = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class=\"ant-col-3 text-center\"])[2]")))
				.getText();

		text = text.split("\n")[0];
		System.out.println("Actual Available seat are == " + text);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
				"#root > div > div > div.search-result-parent > div:nth-child(2) > div > div > div.ant-col-2.btn-block1.text-right > div > button")))
				.click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[contains(@class,'available_seat') or contains(@class,'gents_qta_seat available_seat') or contains(@class,'ladies_qta_seat available_ seat')]")));

		List<WebElement> seats = driver.findElements(By.xpath(
				"//div[contains(@class,'available_seat') or contains(@class,'gents_qta_seat available_seat') or contains(@class,'ladies_qta_seat available_ seat')]"));

		int expectedSeats = seats.size();

		System.out.println("Expected Available seat are == " + expectedSeats);

		if (expectedSeats == Integer.parseInt(text)) {
			System.out.println("Seats Available In The list Is Matching With The Seats Available On Seat Layout...");
		} else {
			System.out
					.println("Seats Available In The list Is Matching With The Seats Not Available On Seat Layout...");
		}

		Thread.sleep(3000);

		driver.quit();

	}

}
