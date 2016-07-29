package com.selenium.thoughtworks.marsair.validation;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.selenium.thoughtworks.marsair.datadriven.TestBase;
import com.selenium.thoughtworks.marsair.util.Constants;
import com.selenium.thoughtworks.marsair.util.TestDataProvider;

public class validationTest extends TestBase{

	
	//tovalidate ediatble fields
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="FieldValidationDataProvider")
	public void fieldValidationTest(Hashtable<String, String> table){
		
		validateRunmode("fieldValidationTest", Constants.FIELDVALIDATION_SUITE, table.get(Constants.RUNMODE_COL));
		
		goToMarsAir();
		
		Assert.assertTrue(isElementPresent(table.get(Constants.FIELDVALIDATIONXPATH_COL)), table.get(Constants.FIELDVALIDATIONXPATH_COL)+"Field not found");
		quit();
		
	}
	
	//to validate the data in the drop down
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="FieldValidationDataProvider")
	public void dropdownValidationTest(Hashtable<String, String> table){
		
		validateRunmode("dropdownValidationTest", Constants.FIELDVALIDATION_SUITE, table.get(Constants.RUNMODE_COL));
		
		goToMarsAir();
		
		CheckValuesInDropdown(table.get(Constants.DROPDOWNDVALIDATIONXPATH_COL));
		quit();
	}
	
	//to validate the lables
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="FieldValidationDataProvider")
	public void labelValidationTest(Hashtable<String, String> table){
		validateRunmode("labelValidationTest", Constants.FIELDVALIDATION_SUITE, table.get(Constants.RUNMODE_COL));
		
		goToMarsAir();
		
		Assert.assertTrue(getText(table.get(Constants.LABELVALIDATIONXPATH_COL)).equals(table.get(Constants.EXPECTEDLABEL_COL)), table.get(Constants.EXPECTEDLABEL_COL)+" - Label not displayed correctly");
		
	}
	
	
	//to validate whether the links navigate to the home page
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="FieldValidationDataProvider")
	public void linkNavigateToHomePageTest(Hashtable<String, String> table) {
		validateRunmode("linkNavigateToHomePageTest", Constants.FIELDVALIDATION_SUITE, table.get(Constants.RUNMODE_COL));
		
		goToMarsAir();
		
		Assert.assertTrue(isElementPresent(table.get(Constants.LINKVALIDATIONXPATH_COL)), table.get(Constants.LINKS_COL)+" is not present on home page");
		click("LoginPgSearchBtn_xpath");
		Assert.assertTrue(isElementPresent(table.get(Constants.LINKVALIDATIONXPATH_COL)), table.get(Constants.LINKS_COL)+" is not present on search results page");
		
		Assert.assertTrue(isElementEnabled(table.get(Constants.LINKVALIDATIONXPATH_COL)), table.get(Constants.LINKS_COL)+" is not clickable link");
		
		
		if ((isElementPresent("welcmeToMarsTxt_xpath"))||(isElementPresent("searchResultTxt_xpath"))) {
			click(table.get(Constants.LINKVALIDATIONXPATH_COL));
			Assert.assertTrue(isElementPresent("welcmeToMarsTxt_xpath"), table.get(Constants.LINKS_COL)+" does not navigate to Home Page");
		}
		
	}
	
	@AfterMethod
	public void quitBrowser(){
		quit();
	}
	
}
