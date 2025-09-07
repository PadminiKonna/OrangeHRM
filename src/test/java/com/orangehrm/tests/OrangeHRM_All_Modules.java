package com.orangehrm.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPageFactory;
import com.orangehrm.pages.AdminPageFactory;
import com.orangehrm.pages.LeavePageFactory;
import com.orangehrm.pages.PIM_PageFactory;
import com.orangehrm.pages.TimePageFactory;
import com.orangehrm.utilities.ExcelUtiles;
import com.orangehrm.utilities.Screenshot;

public class OrangeHRM_All_Modules extends BaseTest {

    String loginExcel = System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\Data.xlsx";
    String adminExcel = System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\AdminData.xlsx";
    String loginSheet = "Sheet1";
    String adminSheet = "Sheet1";

    // ===============================
    // 🔹 Login Test
    // ===============================
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        return ExcelUtiles.getdata(loginExcel, loginSheet);
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        test = extent.createTest("Login Test with Username: " + username);
        LoginPageFactory loginPage = new LoginPageFactory(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            wait.until(ExpectedConditions.visibilityOf(loginPage.usernameField));
            loginPage.enterUsername(username);
            test.info("Entered Username: " + username);

            loginPage.enterPassword(password);
            test.info("Entered Password");

            WebElement loginBtn = loginPage.loginButton;
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
            test.info("Clicked Login button");

            wait.until(ExpectedConditions.titleContains("OrangeHRM"));
            Assert.assertTrue(driver.getTitle().contains("OrangeHRM"), "Login failed!");
            test.pass("Login successful for Username: " + username);

            loginPage.logout();
            wait.until(ExpectedConditions.urlContains("login"));
            test.pass("Logout successful for Username: " + username);

        } catch (Exception e) {
            captureFailure("Login_" + username, e);
            throw e;
        }
    }

    // ===============================
    // 🔹 Admin Test
    // ===============================
    @DataProvider(name = "adminData")
    public Object[][] getAdminData() throws IOException {
        return ExcelUtiles.getdata(adminExcel, adminSheet);
    }

    @Test(dataProvider = "adminData")
    public void addUserTest(String role, String empName, String status,
                            String username, String password, String confirmPassword) {
        test = extent.createTest("Admin - Add User Test with Username: " + username);

        try {
            AdminPageFactory adminPage = new AdminPageFactory(driver);

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            adminPage.login("Admin", "admin123");
            test.pass("Logged in as Admin");

            adminPage.addUser(role, empName, status, username, password, confirmPassword);
            test.pass("User added successfully with Username: " + username);

            adminPage.logout();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("login"));
            test.pass("Logout successful");

        } catch (Exception e) {
            captureFailure("Admin_AddUser_" + username, e);
            throw e;
        }
    }

    // ===============================
    // 🔹 PIM Test
    // ===============================
    @Test
    public void addEmployeeAndEditPersonalDetails() {
        test = extent.createTest("PIM - Add Employee and Edit Personal Details");

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
            driver.findElement(By.name("password")).sendKeys("admin123");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            test.pass("Logged in successfully");

            PIM_PageFactory pimPage = new PIM_PageFactory(driver);
            pimPage.goToPIM();
            test.info("Navigated to PIM module");

            String firstName = "David";
            String middleName = "John";
            String lastName = "Smith";
            pimPage.addEmployee(firstName, middleName, lastName);
            test.pass("Employee added: " + firstName + " " + middleName + " " + lastName);

            boolean redirected = pimPage.verifyRedirectToPersonalDetails(firstName, lastName);
            Assert.assertTrue(redirected, "Personal Details page not displayed!");
            test.pass("Redirected to Personal Details page");

            pimPage.savePersonalDetails();
            test.pass("Personal details saved successfully");

            driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Logout']"))).click();
            test.pass("Logout successful");

        } catch (Exception e) {
            captureFailure("PIM_AddEmployee", e);
            throw e;
        }
    }
    
    @Test
    public void employeeTimesheetFlowTest() {
        test = extent.createTest("Employee Timesheet Flow - Create and Approve");

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            TimePageFactory timePage = new TimePageFactory(driver);

            // Login
            timePage.login("Admin", "admin123");
            test.pass("Logged in successfully");

            // Navigate to Time
            timePage.navigateToTimeModule();
            test.info("Navigated to Time module");

            // Search Employee and View Timesheet
            timePage.searchEmployeeAndView("Peter Mac Anderson");
            test.pass("Employee timesheet opened successfully");

            //*/ Create Timesheet
           /* timePage.createTimesheet();
            test.pass("Timesheet created successfully");*/

            /*// Approve Timesheet
            timePage.submitTimesheet();
            test.pass("Timesheet submitted successfully");
            
            timePage.approveTimesheet();
            test.pass("Timesheet approved successfully");*/

            // Logout
            timePage.logout();
            test.pass("Logout successful");

        } catch (Exception e) {
            captureFailure("EmployeeTimesheetFlow", e);
            throw e;
        }
    }
    @Test
    public void assignLeaveFlowTest() {
        test = extent.createTest("Assign Leave Flow Test");

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            LeavePageFactory leavePage = new LeavePageFactory(driver);

            // Login
            leavePage.login("Admin", "admin123");
            test.pass("Logged in successfully");

            // Navigate to Assign Leave
            leavePage.navigateToLeaveModule();
            test.info("Navigated to Leave module");
            leavePage.clickAssignLeave();
            test.info("Navigated to Assign leave");

            // Fill Leave Form
            leavePage.fillAssignLeaveForm("David John Smith", "CAN - Vacation", "2025-09-10", "2025-09-11");
            test.pass("Leave form filled successfully");

            // Click Assign
            leavePage.clickAssign();
            test.pass("Leave assigned successfully");

            // Logout
            leavePage.logout();
            test.pass("Logout successful");

        } catch (Exception e) {
            captureFailure("AssignLeaveFlow", e);
            throw e;
        }
    }
    
    
    
    
    

    // ===============================
    private void captureFailure(String testName, Throwable e) {
        try {
            String screenshotPath = Screenshot.Capture(driver, testName);
            test.fail("Test failed → " + e.getMessage())
                .addScreenCaptureFromPath(screenshotPath);
        } catch (IOException io) {
            test.fail("Failed to capture screenshot: " + io.getMessage());
        }
    }
}
