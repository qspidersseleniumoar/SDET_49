package com.comcastcrm.contacttest;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.comcast.genericutlity.ExcelUtlity;
import com.comcast.genericutlity.FileUtlity;



public class CreateContactSupportDateTest {
	
	@Test
	public void createOrgTest() throws Throwable{		
		/*create Object for utlity */
		FileUtlity fLib = new FileUtlity();
		ExcelUtlity eLib = new ExcelUtlity();
		
		/*get the FILE PATH*/
       String ENV_FILE_PATH =    fLib.getFilePathFromPropertiesFile("projectConfigDataFilePath");
       String TEST_SCRIPT_EXCEL_FILE_PATH =    fLib.getFilePathFromPropertiesFile("testScriptdatafilePath");
       
       /*Read the common data*/
	   String BROWSER = fLib.getDataFromProperties(ENV_FILE_PATH, "browser");
	   String URL = fLib.getDataFromProperties(ENV_FILE_PATH, "url");
	   String USERNAME = fLib.getDataFromProperties(ENV_FILE_PATH, "username");
	   String PASSWORd = fLib.getDataFromProperties(ENV_FILE_PATH, "password");

		/*test script data*/
		int randomNum = new Random().nextInt(3000);
		
		  String lastName = eLib.getDataFromExcelBasedTestId(TEST_SCRIPT_EXCEL_FILE_PATH, "contact", "tc_05", "LastName") +"_"+ randomNum;
	
		WebDriver driver = null;

		/*step 1 : login to app*/
		if(BROWSER.equalsIgnoreCase("chrome")) {		
		    driver = new ChromeDriver();
		}else if(BROWSER.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}else if(BROWSER.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}else {
		    driver = new ChromeDriver();
		}
		
		
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(URL);
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORd);
		driver.findElement(By.id("submitButton")).click();
		
		/*step 2 : navigate to Org page*/
		driver.findElement(By.linkText("Contacts")).click();
		
		/*step 3 :  navigate to create Org page*/
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();
		
		/*step 4 :  create a new org*/
		driver.findElement(By.name("lastname")).sendKeys(lastName);
		
		Date date = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String fdate =   sim.format(date);
		
		driver.findElement(By.id("jscal_field_support_start_date")).clear();
		driver.findElement(By.id("jscal_field_support_start_date")).sendKeys(fdate);
		
		 Calendar cal =  Calendar.getInstance();
		 cal.add(Calendar.DATE,30);		 
	     Date date1 =  cal.getTime();	   
	    String pfdata = sim.format(date1);
	    System.out.println(pfdata);
		
		driver.findElement(By.id("jscal_field_support_end_date")).clear();
		driver.findElement(By.id("jscal_field_support_end_date")).sendKeys(fdate);
		
        driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		 /*verify expected result*/
         String actLastName = driver.findElement(By.className("dvHeaderText")).getText();
         if(actLastName.contains(lastName)) {
        	 System.out.println(lastName+ "is verified PASS");
         }else {
        	 System.out.println(lastName+ "is not verified FAIL");

         }
         
		/*step 4 :  logout*/
		WebElement ele =  driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
        Actions act = new Actions(driver);
        act.moveToElement(ele).perform();
		driver.findElement(By.linkText("Sign Out")).click();
		driver.close();

	}

}





