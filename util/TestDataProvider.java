package Module20.com.qtpselenium.framework.datadriven.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import Module20.com.qtpselenium.framework.datadriven.TestBase;


public class TestDataProvider {

	@DataProvider(name="PortfolioDataProvider")
	public static Object[][] getDataSuiteA(Method m){

		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.PORTFOLIO_SUITE+".xls");
		
		return Utility.getData(m.getName(), xls1);
		
	}
	
	@DataProvider(name="SuiteBDataProvider")
	public static Object[][] getDataSuiteB(Method m){

		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.SECOND_SUITE+".xls");
		
		return Utility.getData(m.getName(), xls1);
		
	}
	
	@DataProvider(name="SuiteCDataProvider")
	public static Object[][] getDataSuiteC(Method m){
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.THIRD_SUITE+".xls");
		return Utility.getData(m.getName(), xls1);
	}
}
