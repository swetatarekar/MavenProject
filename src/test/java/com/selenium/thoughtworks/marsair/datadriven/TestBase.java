package com.selenium.thoughtworks.marsair.datadriven;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;

import com.selenium.thoughtworks.marsair.util.Constants;
import com.selenium.thoughtworks.marsair.util.Utility;
import com.selenium.thoughtworks.marsair.util.Xls_Reader;

public class TestBase {

	public static Properties prop;
	public static Logger APPLICATION_LOG = Logger.getLogger("devpinoyLogger");
	
	public WebDriver driver;
	
	public static void init(){
		
		if (prop == null) {
			String path = System.getProperty("user.dir")+"\\src\\test\\resources\\marsAir.properties";
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void validateRunmode(String testCase,String suiteName, String dataRunmode) {
		//initializing the path
		//init();

		boolean suiteRunmode = Utility.isSuiteRunnable(suiteName, new Xls_Reader(System.getProperty("user.dir")+"\\Suite.xls"));
		boolean testRunmode = Utility.isTestCaseRunnable(testCase, new Xls_Reader(System.getProperty("user.dir")+"\\"+suiteName+".xls"));
		boolean dataSetRunmode = false;
		
		if(dataRunmode.equals(Constants.RUNMODE_YES))
			dataSetRunmode = true;
		
		if(!(dataSetRunmode && suiteRunmode && testRunmode))
		{
			throw new SkipException((dataSetRunmode && suiteRunmode && testRunmode)+" dataset -> "+dataSetRunmode+" suite -> "+suiteRunmode+" testcase -> "+testRunmode+"skipping the test "+testCase+" from suite "+suiteName);
		}
	}
	
	public void OpenBrowser(String browserName){

		if(browserName.equalsIgnoreCase("Mozilla")){
			driver = new FirefoxDriver();
		}else if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\IEDriverServer_Win32_2.53.0\\IEDriverServer.exe");
			driver = new ChromeDriver();
		}
//if we want to run the tests on Grid:
		
//		DesiredCapabilities cap = new DesiredCapabilities();
//		
//		if(browserName.equals("firefox")){
//			cap =  DesiredCapabilities.firefox();
//			cap.setBrowserName("firefox");
//			cap.setPlatform(Platform.ANY);
//		}else if (browserName.equals("chrome")) {
//			cap =  DesiredCapabilities.chrome();
//			cap.setBrowserName("chrome");
//			cap.setPlatform(Platform.ANY);
//		}
//		try {
//			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void navigate(String URLkey){
		driver.navigate().to(prop.getProperty(URLkey));
	}
	
	public void click(String identifier){
		if (identifier.endsWith("_xpath")) {
			driver.findElement(By.xpath(prop.getProperty(identifier))).click();
		}
		if (identifier.endsWith("_id")) {
			driver.findElement(By.id(prop.getProperty(identifier))).click();
		}
		
	}
	
	public void input(String identifier, String data){
		if (identifier.endsWith("_xpath")) {
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
		}
		if (identifier.endsWith("_id")) {
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
		}
		if (identifier.endsWith("_name")) {
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
		}
	}
	
	public String getText(String identifier) {
		String text = "";
		
		if(identifier.endsWith("_xpath")){
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		}
		if(identifier.endsWith("_id")){
			text = driver.findElement(By.id(prop.getProperty(identifier))).getText();
		}
		if(identifier.endsWith("_name")){
			text = driver.findElement(By.name(prop.getProperty(identifier))).getText();
		}
		
		return text;
	}
	

	public boolean isElementPresent(String identifier) {
		
		int size = 0;
		if(identifier.endsWith("_xpath")){
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		}else if(identifier.endsWith("_id")){
			size = driver.findElements(By.id(prop.getProperty(identifier))).size();
		}else if(identifier.endsWith("_name")){
			size = driver.findElements(By.name(prop.getProperty(identifier))).size();
		}else{
			size = driver.findElements(By.xpath(identifier)).size();
		}
			
		
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void quit(){
		if(driver!=null){
			driver.quit();
			driver = null;
		}
	}
	
	//************************** APPLICATION SPECIFIC FUNCTIONS ********************************************//
	
	public void goToMarsAir() {
		OpenBrowser(prop.getProperty("Browser"));
		navigate("testSiteURL");
	}
	
	public List<WebElement> getList(String identifier) {
		
		List<WebElement> list = null;
		
		if(identifier.endsWith("_xpath")){
			list = driver.findElements(By.xpath(prop.getProperty(identifier)));
		}else if(identifier.endsWith("_id")){
			list = driver.findElements(By.id(prop.getProperty(identifier)));
		}else if(identifier.endsWith("_name")){
			list = driver.findElements(By.name(prop.getProperty(identifier)));
		}
		
		return list;
	
	}
	
	public void CheckValuesInDropdown(String identifier) {
		
		List<WebElement> actualDropdownValues = getList(identifier);
		
		String values = prop.getProperty("dropDownValues"); 
		
		String[] ExpectedDropDownValues = values.split(",");
		
		boolean token = false;
		String optionsMissing = null;
		
		
		if(actualDropdownValues.size() > ExpectedDropDownValues.length){
			try {
				throw new Exception("Too many options in the actal drop down on the page.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (actualDropdownValues.size() < ExpectedDropDownValues.length) {
			try {
				throw new Exception("Less options in the actal drop down on the page.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (String option : ExpectedDropDownValues) {
			token = false;
			for (int i = 0; i < ExpectedDropDownValues.length; i++) {
				if (actualDropdownValues.get(i).getText().equals(option)) {
					token = true;
				}
			}
				if (token == false) {
					optionsMissing = optionsMissing + "'" + option + "'";
				}
			
		}
		
		if (optionsMissing != null) {
			try {
				throw new Exception("The following options are not present in the dropdown on the screen: "+optionsMissing);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public boolean validationgPromotionalCode(String promoCode) {

		int firstDigit = Character.getNumericValue(promoCode.split("-")[0].toCharArray()[2]);
		int secondDigit = Character.getNumericValue(promoCode.split("-")[2].toCharArray()[0]);
		int thirdDigit = Character.getNumericValue(promoCode.split("-")[2].toCharArray()[1]);
		int finalDigit = Character.getNumericValue(promoCode.split("-")[2].toCharArray()[2]);
		
		int modOutput = (firstDigit+secondDigit+thirdDigit)%10;
			if (modOutput == finalDigit) {
				if (Character.isLetter(promoCode.split("-")[0].toCharArray()[0]) &&
						Character.isLetter(promoCode.split("-")[0].toCharArray()[1]) &&
						Character.isLetter(promoCode.split("-")[1].toCharArray()[0]) &&
						Character.isLetter(promoCode.split("-")[1].toCharArray()[1]) &&
						Character.isLetter(promoCode.split("-")[1].toCharArray()[2])) {
				return true;
			}else {
				return false;
			}

	       }
			return false;
	}
	
	public boolean invalidSearch(String departure, String arrival) {
		
		String values = prop.getProperty("dropDownValues"); 
		
		String[] DropDownValues = values.split(",");

		int indexOfDeparture = Arrays.asList(DropDownValues).indexOf(departure);
		int indexOfArrival = Arrays.asList(DropDownValues).indexOf(arrival);
		
		if((indexOfArrival<indexOfDeparture) || (indexOfArrival == indexOfDeparture) || (indexOfArrival== indexOfDeparture+1))
		{
			return false;
		}else {
			return true;
		}
		
		
	}
	
	public boolean isElementEnabled(String identifier) {
		
		if(identifier.endsWith("_xpath")){
			return driver.findElement(By.xpath(prop.getProperty(identifier))).isEnabled();
		}else if(identifier.endsWith("_id")){
			return driver.findElement(By.id(prop.getProperty(identifier))).isEnabled();
		}else if(identifier.endsWith("_name")){
			return driver.findElement(By.name(prop.getProperty(identifier))).isEnabled();
		}else{
			return driver.findElement(By.xpath(identifier)).isEnabled();
		}
	}
	
}