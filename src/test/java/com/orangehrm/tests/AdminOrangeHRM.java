package com.orangehrm.tests;




import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AdminPageFactory;
import com.orangehrm.utilities.ExcelUtiles;
import com.orangehrm.utilities.Screenshot;

public class AdminOrangeHRM extends BaseTest {

    AdminPageFactory adminPage;

    // Excel file path and sheet name
    String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\AdminData.xlsx";
    String sheetName = "Sheet1";

    @DataProvider(name = "adminData")
    public Object[][] getData() throws IOException {
        return ExcelUtiles.getdata(excelPath, sheetName);
    }
    @Test(dataProvider = "adminData")
    public void addUserTest(String role, String empName, String status, String username, String password, String confirmPassword) {
        // 🔹 Create test in Extent report
        test = extent.createTest("Add User Test with Username: " + username);

        try {
            // Step 1: Initialize PageFactory
            adminPage = new AdminPageFactory(driver);
            test.info("Initialized Admin Page Factory");

            // Step 2: Navigate to login page
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            test.info("Navigated to Login Page");

            // Step 3: Login as Admin
            adminPage.login("Admin", "admin123");
            test.pass("Logged in as Admin");

            // Step 4: Add User
            adminPage.addUser(role, empName, status, username, password, confirmPassword);
            test.pass("User added successfully with Username: " + username);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.titleContains("OrangeHRM"));

            // Step 5: Logout
            adminPage.logout();
            wait.until(ExpectedConditions.urlContains("login"));
            Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout failed!");
            test.pass("Logout successful");

        }catch (AssertionError | Exception e) {
            try {
                String screenshotPath = Screenshot.Capture(driver, "AddUser_" + username);
                test.fail("Test failed → " + e.getMessage())
                    .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException io) {
                test.fail("Failed to capture screenshot: " + io.getMessage());
            }
            throw e; // rethrow so TestNG marks it failed
        }

    }
}

