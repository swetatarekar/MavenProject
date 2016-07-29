package Module20.com.qtpselenium.framework.datadriven.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class RoughWork {
	
	static WebDriver driver= null;
	
	public static void main(String[] args) {
		
			
			System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			driver.navigate().to("http://portfolio.rediff.com/portfolio?src=top_nav");
			driver.findElement(By.xpath(".//*[@id='useremail']")).sendKeys("sweta.tarekar0506");
			driver.findElement(By.xpath(".//*[@id='useremail']")).sendKeys(Keys.ENTER);
			
			driver.findElement(By.xpath(".//*[@id='userpass']")).sendKeys("Sweta12");
			driver.findElement(By.xpath(".//*[@id='userpass']")).sendKeys(Keys.ENTER);
			
			driver.findElement(By.xpath(".//*[@id='addStock']")).click();
			
			driver.findElement(By.xpath(".//*[@id='addstockname']")).sendKeys("Arnold Holdings Ltd.");
			driver.findElement(By.xpath(".//*[@id='addstockname']")).sendKeys(Keys.ENTER);
			driver.findElement(By.xpath(".//*[@id='stockPurchaseDate']")).click();
			
			selectDate("09/02/2016");
			
			driver.findElement(By.xpath(".//*[@id='addstockqty']")).sendKeys("3000");
			driver.findElement(By.xpath(".//*[@id='addstockprice']")).sendKeys("235.25");
			
//			driver.findElement(By.xpath(".//*[@id='exchange_tab']/label[2]")).click();
//			
//			driver.findElement(By.xpath(".//*[@id='popupAddStockForm']/div[2]/a")).click();
//			driver.quit();
		
	}	
	
	public static void selectDate(String inputDate)  {
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date currentDate = new Date();
		System.out.println("current date -> "+currentDate);
		Date dateToBeSelected = null;
		try {
			dateToBeSelected = df.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String day = new SimpleDateFormat("d").format(dateToBeSelected);
		String month = new SimpleDateFormat("MMMM").format(dateToBeSelected);
		String year = new SimpleDateFormat("yyyy").format(dateToBeSelected);
		
		String reqMonthYear = month+" "+year;
		System.out.println("required month year  "+reqMonthYear);
		
		
		String monthYearDisplayed = driver.findElement(By.xpath(".//*[@id='datepicker']/table/tbody/tr[1]/td[3]/div")).getText();
		System.out.println("month year"+monthYearDisplayed);
		
		while (true) {

			if (monthYearDisplayed.equals(reqMonthYear)) {
				System.out.println("month year found");
				driver.findElement(By.xpath(".//*[@id='datepicker']/table/tbody/tr/td[text()='"+day+"']")).click();
				break;
			}else if(dateToBeSelected.after(currentDate)){
				driver.findElement(By.xpath("")).click();
			}else {
				driver.findElement(By.xpath(".//*[@id='datepicker']/table/tbody/tr[1]/td[2]/button")).click();
			}
			
			monthYearDisplayed = driver.findElement(By.xpath(".//*[@id='datepicker']/table/tbody/tr[1]/td[3]/div")).getText();
		}
	}

}

