package com.qtpselenium.core.coreFramework.testcases;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.core.coreFramework.base.BaseTest;
import com.qtpselenium.core.coreFramework.util.DataUtil;
import com.qtpselenium.core.coreFramework.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class DummyTestCase1 extends BaseTest{

	SoftAssert softAssert;
	
	@BeforeMethod
	public void initializations(){
		softAssert= new SoftAssert();	
	}
	
	
	@Test(priority=1)
	public void testA1(){

//		test = rep.startTest("starting test A1");
//		softAssert = new SoftAssert();
//		
//		openBrowser("Mozilla");
//		navigate("appURL");
//		click("signInBtn_xpath");
//		
//		softAssert.assertTrue(false, "error 1");
//		softAssert.assertTrue(false, "errror 2");
//		softAssert.assertTrue(false, "errror 3");
//		softAssert.assertTrue(false, "errror 4");
//		
//		if (!isElementPresent("signInBtn_xpath")) {
//			reportFailure("cridentials not present");	
//		}

	}
	
	@Test(priority=2,dependsOnMethods={"testA1"},dataProvider="getData")
	public void testA2(Hashtable<String, String> table){
		System.out.println(table.get("Runmode"));
	}
	
	@Test(priority=3, dependsOnMethods={"testA1","testA2"})
	public void testA3(){
		
	}
	
	@AfterMethod
	public void quitTest(){
		
		try {
			softAssert.assertAll();
		} catch (Error e) {
			test.log(LogStatus.FAIL, e.getMessage());
		}
		
	     rep.endTest(test);
	     rep.flush();
	}
	
	@DataProvider
	public Object[][] getData(){
		init();
		Xls_Reader xls = new Xls_Reader(prop.getProperty("xlsPath"));
		return DataUtil.getTestData(xls, "testA2");
		
	}
	
}
