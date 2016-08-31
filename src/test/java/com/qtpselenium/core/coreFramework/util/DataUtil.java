package com.qtpselenium.core.coreFramework.util;

import java.util.Hashtable;

public class DataUtil {

	public static Object[][] getTestData(Xls_Reader xls, String testCaseName) {
		
		String sheetName = "Data";
		
		int testStartRowNo = 1;
		
		while(!xls.getCellData(sheetName, 0, testStartRowNo).equals(testCaseName))
		{
			testStartRowNo++;
		}
		
		System.out.println("test start row number is -> "+testStartRowNo);
		
		int colStartRowNo = testStartRowNo+1;
		int dataStartRowNo = testStartRowNo+2;
		
		System.out.println("data starts from "+dataStartRowNo);
		
		//calculate rows of data
		
		int totalRowsOfData = 0;
		
		while(!xls.getCellData(sheetName, 0, dataStartRowNo+totalRowsOfData).equals("")){
			totalRowsOfData++;
		}
				
		System.out.println("total rows of data -> "+totalRowsOfData);
		
		int totalColOfData = 0;
		
		while(!xls.getCellData(sheetName, totalColOfData, colStartRowNo).equals("")){
			totalColOfData++;
		}
		System.out.println("total cols of data -> "+totalColOfData);
		
		//reading data
		Object[][] data= new Object[totalRowsOfData][1];
		int dataRows =0; 
		
		Hashtable<String, String> table = null;
		
		for (int rNum = dataStartRowNo; rNum < dataStartRowNo+totalRowsOfData; rNum++) {
//			System.out.println("inside rows");
			table= new Hashtable<String, String>();
			for (int cNum = 0; cNum < totalColOfData; cNum++) {
//				System.out.println("inside col");
				System.out.print(xls.getCellData(sheetName, cNum, rNum)+ "    ");
				String key = xls.getCellData(sheetName, cNum, colStartRowNo);
				String value = xls.getCellData(sheetName, cNum, rNum); 
				table.put(key, value);
			}
			
			data[dataRows][0]= table;
			dataRows++;
			System.out.println();
		}
		return data;
	}
	
}
