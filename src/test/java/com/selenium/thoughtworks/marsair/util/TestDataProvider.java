package com.selenium.thoughtworks.marsair.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.selenium.thoughtworks.marsair.datadriven.TestBase;

public class TestDataProvider {

	@DataProvider(name="TicketBookingDataProvider")
	public static Object[][] getDataTicketBooking(Method m){

		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+"\\"+Constants.TICKETBOOKING_SUITE+".xls");
		
		return Utility.getData(m.getName(), xls1);
		
	}
	
	@DataProvider(name="FieldValidationDataProvider")
	public static Object[][] getDataFieldValidation(Method m){

		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+"\\"+Constants.FIELDVALIDATION_SUITE+".xls");
		
		return Utility.getData(m.getName(), xls1);
		
	}
	
}
