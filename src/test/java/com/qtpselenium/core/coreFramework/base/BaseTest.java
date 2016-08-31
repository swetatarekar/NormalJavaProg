package com.qtpselenium.core.coreFramework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.qtpselenium.core.coreFramework.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import freemarker.core.ReturnInstruction.Return;

public class BaseTest {

	public WebDriver driver;
	public static Properties prop;
	
	public ExtentReports rep = ExtentManager.getInstance();;
	public ExtentTest test ;
	
	public static void init() {
		if(prop == null){
			prop = new Properties();
			
				FileInputStream fs = null;
				try {
					fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\projectConfig.properties");
				} catch (FileNotFoundException e1) {
					System.out.println("problem in getting path of properties file");
					e1.printStackTrace();
				}
				try {
					prop.load(fs);
				} catch (IOException e) {
					System.out.println("problem in loading properties file");
					e.printStackTrace();
				}

			}
		}
	
	
	public void openBrowser(String browser) {
		if(browser.equalsIgnoreCase("Mozilla")){
			driver= new FirefoxDriver();			
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	public void navigate(String URL) {
		driver.navigate().to(prop.getProperty(URL));
	}
	
	public void click(String xpathEle) {
		getElement(xpathEle).click();
	}
	
	public void input(String xpathEle,String text) {
		getElement(xpathEle).sendKeys(text);
	}
	
	//finding element on page and returning
	public WebElement getElement(String locatorKey) {
		
		WebElement element= null;
		
		try{
		if(locatorKey.endsWith("_id")){
			element= driver.findElement(By.id(prop.getProperty(locatorKey)));
		} else if(locatorKey.endsWith("_xpath")){
			element=driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_name")){
			element= driver.findElement(By.name(prop.getProperty(locatorKey)));
		}else{
			reportFailure("locator not correct -> "+locatorKey);
			Assert.fail("locator not correct -> "+locatorKey);
		}
		
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
			Assert.fail("fail test cause -> "+e.getMessage());
		}
		
		return element;
		
	}
	
	//**************VALIDATIONS**************************************//
	
	public boolean verifyTitle(String locatorKey,String expectedTextKey) {
		
		String actualText = getElement(locatorKey).getText().trim();
		String expectText = prop.getProperty(expectedTextKey);
		
		if (actualText.equals(expectText)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public boolean isElementPresent(String locatorKey) {
		
		List<WebElement> elementList = null;
		
		if(locatorKey.endsWith("_id")){
			elementList= driver.findElements(By.id(prop.getProperty(locatorKey)));
		} else if(locatorKey.endsWith("_xpath")){
			elementList=driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_name")){
			elementList= driver.findElements(By.name(prop.getProperty(locatorKey)));
		}else{
			reportFailure("locator not correct -> "+locatorKey);
			Assert.fail("locator not correct -> "+locatorKey);
		}
		
		if (elementList.size() == 0) {
			return false;
		}else{
			return true;
		}
	}
	public boolean verifyText() {
		return false;
	}
	
	//*********************REPORTING ********************************//
	
	public void reportPass(String msg) {
		test.log(LogStatus.PASS, msg);
	}
	
	public void reportFailure(String msg) {
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
	}
	public void takeScreenShot() {
		// file name of screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String fileName = new Date().toString().replace(":", "_").replace(" ", "_")+".jpg";
		
		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"//screenShots//"+fileName));
		} catch (Exception e) {
			e.getStackTrace();
		}
		// put screenshot file in reports
		test.log(LogStatus.INFO, test.addScreenCapture(System.getProperty("user.dir")+"//screenShots//"+fileName));
	}
	
}
