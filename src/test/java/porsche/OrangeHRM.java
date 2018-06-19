package porsche;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OrangeHRM {
	public static void main(String[] args) throws InterruptedException {
		
		//System.setProperty("webdriver.chrome.driver", "/mvn-batch8-automation/src/test/resources/drivers/chromedriver");
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://opensource.demo.orangehrmlive.com/");
		
		String expectedTitle = "OrangeHRM";
		String actualTitle = driver.getTitle();
		
		if(actualTitle.equals(expectedTitle)) {
			System.out.println("Step PASS");
		}else {
			System.out.println("Step FAIL");
		}
		
		
		List<WebElement> inputs = driver.findElements(By.xpath("//input"));
		System.out.println(inputs.size());
		
	
		List<WebElement> inputsLink = driver.findElements(By.xpath("//a"));
		
		for(WebElement link : inputsLink) {
			link.click();
		}
		
		
		WebElement username = driver.findElement(By.id("txtUsername"));
		username.sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin");
		driver.findElement(By.id("btnLogin")).click();
		
		String expextedURL = "http://opensource.demo.orangehrmlive.com/index.php/dashboard";
		
		if (driver.getCurrentUrl().equals(expextedURL)) {
			System.out.println("PASS");
		}else {
			System.out.println("FAIL");
		}


		
		
		
		
		
		
		
		
		
		
		
		
		Thread.sleep(15000);
		driver.quit();

	}

}