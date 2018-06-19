package porsche;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Porsche {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		//2
		driver.get("https://www.porsche.com/usa/modelstart/");

		//3
		driver.findElement(By.linkText("718")).click(); 

		//4
		String price = driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[1]/div[2]/div[2]")).getText();
		price = getNumbers(price);
		price = price.substring(0, 5);

		//5
		String winHandleBefore = driver.getWindowHandle();
		driver.findElement(By.linkText("Build & Price")).click(); 
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}

		//6
		String priceBase = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		priceBase = getNumbers(priceBase);
		verify(price, priceBase);

		//7
		String priceForEquipment = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		priceForEquipment = getNumbers(priceForEquipment);
		verify(priceForEquipment, "0");

		//8
		String priceDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		String priceTotal = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		priceDelivery = "" + Integer.parseInt(getNumbers(priceDelivery));
		priceTotal = "" + Integer.parseInt(getNumbers(priceTotal));
		verify(sumStrings(priceDelivery, priceBase), priceTotal);

		//9-color
		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_FJ5\"]/span")).click(); 

		//10
		String priceMiami = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IAF\"]/div[2]/div[1]/div/div[2]")).getText();
		priceForEquipment = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		priceMiami = getNumbers(priceMiami);
		String previousEq = priceForEquipment = getNumbers(priceForEquipment);
		verify(priceMiami, priceForEquipment);

		//11
		verifyTotal(priceBase, priceForEquipment, priceDelivery, priceTotal, driver);

		//12-wheels
		//driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_M433\"]/span/span")).click();
		List<WebElement> wheels = driver.findElements(By.xpath("//div[@id='ARA']//li"));
		wheels.get(3).click();

		//13
		String priceWheels = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText();
		previousEq = verifyEquipment(previousEq, priceWheels, driver);

		//14
		verifyTotal(priceBase, priceForEquipment, priceDelivery, priceTotal, driver);

		//15-seat
		WebElement scrollArea = driver.findElement(By.cssSelector("#IIN_subHdl"));
		scroll_Page(scrollArea ,100, driver);
		driver.findElement(By.cssSelector("#s_interieur_x_PP06")).click();

		//16
		String priceSeats = driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div")).getText();
		previousEq = verifyEquipment(previousEq, priceSeats, driver);

		//17
		verifyTotal(priceBase, priceForEquipment, priceDelivery, priceTotal, driver);

		//18-interior carbon
		scrollArea = driver.findElement(By.cssSelector("#s_navigation_config_end_x_s_navigation_config_end_x_showSelection > a"));
		scroll_Page(scrollArea ,100, driver);
		driver.findElement(By.xpath("//*[@id=\"IIC_subHdl\"]")).click();

		//19-interior carbon trim
		driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH_x_c01_PEKH\"]")).click();

		//20
		String priceTrim = driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH\"]/div[1]/div[2]/div")).getText();
		previousEq = verifyEquipment(previousEq, priceTrim, driver);

		//21
		verifyTotal(priceBase, priceForEquipment, priceDelivery, priceTotal, driver);

		//22-performance
		driver.findElement(By.linkText("3. Options")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"IMG_subHdl\"]")).click();

		//23
		driver.findElement(By.cssSelector("#vs_table_IMG_x_M250_x_c11_M250")).click();
		String price7Speed = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250\"]/div[1]/div[2]/div")).getText();
		Thread.sleep(500);

		//24
		scrollArea = driver.findElement(By.cssSelector("#IIN_subHdl"));
		scroll_Page(scrollArea ,100, driver);
		driver.findElement(By.cssSelector("#vs_table_IMG_x_M450_x_c91_M450")).click();
		String priceCeramic = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450\"]/div[1]/div[2]/div")).getText();

		//25
		price7Speed = getNumbers(price7Speed);
		priceCeramic = getNumbers(priceCeramic);
		previousEq = verifyEquipment(previousEq, sumStrings(price7Speed, priceCeramic), driver);

		//26
		verifyTotal(priceBase, priceForEquipment, priceDelivery, priceTotal, driver);
	}

	public static boolean verify(String first, String second){
		int firstNum = Integer.parseInt(first);
		int secondNum = Integer.parseInt(second);
		if(firstNum == secondNum) {
			System.out.println("Verified!");
			return true;
		}
		System.out.println("Not Verified!");
		return false;
	}
	
	public static String verifyEquipment(String previousEq, String item, WebDriver driver) {
		String equipment = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		item = getNumbers(item);
		equipment = getNumbers(equipment);
		verify(sumStrings(previousEq, item), equipment);
		return sumStrings(previousEq, item);
	}

	public static void verifyTotal(String priceBase, String priceForEquipment, String priceDelivery, String priceTotal, WebDriver driver) {
		priceForEquipment = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		priceDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		priceTotal = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		priceForEquipment = getNumbers(priceForEquipment);
		priceDelivery = "" + Integer.parseInt(getNumbers(priceDelivery));
		priceTotal = "" + Integer.parseInt(getNumbers(priceTotal));
		verify(sumStrings(sumStrings(priceBase, priceForEquipment), priceDelivery), priceTotal);
	}

	public static String getNumbers(String str) {
		return str.replaceAll("\\D+","");
	}

	public static String sumStrings(String first, String second){
		int firstNum = Integer.parseInt(first);
		int secondNum = Integer.parseInt(second);
		return String.valueOf(firstNum + secondNum);
	}

	public static boolean scroll_Page(WebElement webelement, int scrollPoints, WebDriver driver){
		try
		{               
			Actions dragger = new Actions(driver);
			// drag downwards
			int numberOfPixelsToDragTheScrollbarDown = 10;
			for (int i = 10; i < scrollPoints; i = i + numberOfPixelsToDragTheScrollbarDown){
				dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release(webelement).build().perform();
			}
			Thread.sleep(500);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}