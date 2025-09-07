package com.orangehrm.tests;




import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LeavePageFactory;

public class LeaveOrangeHRM extends BaseTest {

    @Test
    public void assignLeaveTest() throws InterruptedException {
        // Step 1: Login
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize(); // <-- Maximize browser
        Thread.sleep(2000);

        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        System.out.println("✅ Logged in successfully");

        // Step 2: Go to Leave → Assign Leave
        LeavePageFactory leavePage = new LeavePageFactory(driver);
        leavePage.goToAssignLeave();

        // Step 3: Assign leave
        String employeeName = "David Smith";
        String leaveType = "US Vacation";
        String fromDate = "2025-09-10";
        String toDate = "2025-09-12";

        leavePage.assignLeave(employeeName, leaveType, fromDate, toDate);
        System.out.println("✅ Leave assigned to " + employeeName + " successfully");

        // Step 4: Logout
        driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
        System.out.println("✅ Logout successful");
    }
}


