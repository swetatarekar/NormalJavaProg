package com.qtpselenium.core.coreFramework.util;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {
		if(extent ==  null){
			
			String fileName = new Date().toString().replace(":", "_").replace(" ", "_")+".html";
			extent = new ExtentReports("d:\\report\\"+fileName,true, DisplayOrder.NEWEST_FIRST);
			
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\ReportsConfig.xml"));
			extent.addSystemInfo("Selenium Version", "2.53.0").addSystemInfo("Environment", "QA");
		}
		
		return extent;
	}
	
	
}
