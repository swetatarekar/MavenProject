package Module20.com.qtpselenium.framework.datadriven.util;

import java.util.Hashtable;

public class Utility {

	public static boolean isSuiteRunnable(String SuiteName,Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.SUITE_SHEET);
		for (int rNum = 2; rNum <= rows; rNum++) {
			String data = xls.getCellData(Constants.SUITE_SHEET, Constants.SUITENAME_COL, rNum);
			if (data.equals(SuiteName)) {
				String runmode = xls.getCellData(Constants.SUITE_SHEET, Constants.RUNMODE_COL, rNum);
				if (runmode.equals(Constants.RUNMODE_YES)) {
					return true;
				} else {
					return false;
				}
			}
			
		}
		System.out.println("suite not found");
		return false;
	}
	
	public static boolean isTestCaseRunnable(String testCase,Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		for (int rNum = 2; rNum <= rows; rNum++) {
			String testNameXls = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TESTCASES_COL, rNum);
			
			if (testNameXls.equalsIgnoreCase(testCase)) { 
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if (runmode.equals(Constants.RUNMODE_YES)) {
					return true;
				} else {
					return false;
				}
			}
			
		}
		System.out.println("test case not found");
		return false;
		
	}
	
	public static Object[][] getData(String testName, Xls_Reader xls){
		
		int rows = xls.getRowCount(Constants.DATA_SHEET);
		
		int rNum = 0;
		for (rNum = 0; rNum < rows; rNum++) {
			String cellData = xls.getCellData(Constants.DATA_SHEET, 0, rNum);
			if(cellData.equalsIgnoreCase(testName))
			{
				System.out.println("found");
				break;
			}
			
		}
		System.out.println("test starts from -> "+rNum);
		int dataStartsRowNum = rNum+2;
		int colNameStartsRowNum = rNum+1;
		
		//total number of rows of data for a particular test
		int testRows = 1;
		while (!xls.getCellData(Constants.DATA_SHEET, 0, dataStartsRowNum+testRows).equals("")) {
			testRows++;
		}
		System.out.println("total number of rows of data for a test -> "+testRows);
		
		int testCol=0;
		while (!xls.getCellData(Constants.DATA_SHEET, testCol, colNameStartsRowNum).equals("")){
			testCol++;
		}
		System.out.println("total number of cols -> "+testCol);
		
		Object[][] data = new Object[testRows][1];
		//the 'data' array shud start form zero(0), so we are using the foll variable 'r' for that purpose 
		int r=0;
		for (int rNum1 = dataStartsRowNum; rNum1 < dataStartsRowNum+testRows; rNum1++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < testCol; cNum++) {
				//System.out.println(xls.getCellData(Constants.DATA_SHEET, cNum, rNum1));
				//data[r][cNum] = xls.getCellData(Constants.DATA_SHEET, cNum, rNum1);
				table.put(xls.getCellData(Constants.DATA_SHEET, cNum, colNameStartsRowNum),xls.getCellData(Constants.DATA_SHEET, cNum, rNum1));
			}
			data[r][0] = table;
			r++;
		}
		
		return data;
		
	}
	
}
