package com.orangehrm.base;

//import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.orangehrm.utilities.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

public class BaseTest {
	 public static WebDriver driver;
	    protected ExtentReports extent;
	    protected ExtentTest test;

	    
	    
	    
	    @BeforeSuite
	    public void setupReport() {
	        extent = ExtentManager.getinstance();
	    }

	    @BeforeMethod
	    public void setup() {
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	    }

	    @AfterMethod
	    public void teardown() {
	        driver.quit();
	    }

	    @AfterSuite
	    public void flushReport() {
	        extent.flush();
	    }

}
