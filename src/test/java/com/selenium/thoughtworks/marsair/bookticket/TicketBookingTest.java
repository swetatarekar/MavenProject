package com.selenium.thoughtworks.marsair.bookticket;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.selenium.thoughtworks.marsair.datadriven.TestBase;
import com.selenium.thoughtworks.marsair.util.Constants;
import com.selenium.thoughtworks.marsair.util.TestDataProvider;

public class TicketBookingTest extends TestBase{

	@Test(dataProviderClass=TestDataProvider.class, dataProvider="TicketBookingDataProvider")
	public void ticketBookingTest(Hashtable<String, String> table){
		
		validateRunmode("ticketBookingTest", Constants.TICKETBOOKING_SUITE, table.get(Constants.RUNMODE_COL));

		
		OpenBrowser(table.get(Constants.BROWSER_COL));
		navigate("testSiteURL");
		input("DepartureDropdown_xpath", table.get(Constants.DEPARTUDEDATE_COL));
		input("ReturnDropdown_xpath", table.get(Constants.RETURNDATE_COL));
		input("PromoCodeField_xpath", table.get(Constants.PROMOTIONALCODE_COL));
		click("LoginPgSearchBtn_xpath");
		
		//if user selects 'select...' in any of the drop downs
//		if ((table.get(Constants.DEPARTUDEDATE_COL).equals("Select..."))||(table.get(Constants.RETURNDATE_COL).equals("Select..."))) {
//			Assert.assertTrue(getText("scheduleNotPossibleMsg_xpath").equals(prop.getProperty("scheduleNotPossibleMsg")), prop.getProperty("scheduleNotPossibleMsg")+" - is not displayed");
//		}else
		
		if (!(invalidSearch(table.get(Constants.DEPARTUDEDATE_COL), table.get(Constants.RETURNDATE_COL)))) {
			System.out.println("search not possible");
			Assert.assertTrue(getText("scheduleNotPossibleMsg_xpath").equals(prop.getProperty("scheduleNotPossibleMsg")), prop.getProperty("scheduleNotPossibleMsg")+" - is not displayed");
		
		}else if (!table.get(Constants.PROMOTIONALCODE_COL).isEmpty()) 
					{//if promo code present get in:
					if(isElementPresent("seatsAvailableMsg_xpath")){//seats available
						if (validationgPromotionalCode(table.get(Constants.PROMOTIONALCODE_COL))){//valid promo code
							System.out.println("valid promotional code");
							Assert.assertTrue(getText("promocodeMsg_xpath").split(" ")[0].equals(prop.getProperty("promocodeSuccessfulMsg")), "Proper message is not displayed for VALID promotional code");
						}else{//invalid promo code
							System.out.println("invalid promotional code");
			                   Assert.assertTrue(getText("promocodeMsg_xpath").split(",")[0].equals(prop.getProperty("promocodeUnSuccessfulMsg")), "Proper message is not displayed for INVALID promotional code");
						}
					}else{//promo code present but seats not available
						System.out.println("seats not available");
						System.out.println(getText("seatNotAvailableMsg_xpath"));
						Assert.assertTrue(getText("seatNotAvailableMsg_xpath").equals(prop.getProperty("seatNotAvailableMsg")), prop.getProperty("seatNotAvailableMsg")+" - is not displayed for valida data");
					}
	           }else {//no promo code
						if (isElementPresent("seatsAvailableMsg_xpath"))//seats available 
						{
							System.out.println("seats available");
							Assert.assertTrue(getText("successfulSearchMsg_xpath").equals(prop.getProperty("successfulSearchMsg")), prop.getProperty("successfulSearchMsg")+" - is not displayed for valida data");
						} 
						else //seats not available
						{
							System.out.println("seats not available");
							Assert.assertTrue(getText("seatNotAvailableMsg_xpath").equals(prop.getProperty("seatNotAvailableMsg")), prop.getProperty("seatNotAvailableMsg")+" - is not displayed for valida data");
						}
					}
	}
	
	
	@AfterMethod
	public void quitBrowser(){
		quit();
	}
	
}
