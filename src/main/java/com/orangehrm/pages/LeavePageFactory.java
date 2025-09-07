package com.orangehrm.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class LeavePageFactory {

    WebDriver driver;
    WebDriverWait wait;

    public LeavePageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Login
    public void login(String username, String password) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        userField.sendKeys(username);

        WebElement passField = driver.findElement(By.name("password"));
        passField.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        loginBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Leave']")));
    }

 // Navigate to Leave Module
    public void navigateToLeaveModule() {
        WebElement leaveMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Leave']")));
        leaveMenu.click();
    }

    // Click Assign Leave
    public void clickAssignLeave() {
        WebElement assignLeaveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Assign Leave']")));
        assignLeaveBtn.click();
    }

    // Fill Assign Leave Form
    public void fillAssignLeaveForm(String empName, String leaveType, String fromDate, String toDate) {
        // Employee Name
        WebElement empField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Type for hints...']")));
        empField.clear();
        empField.sendKeys(empName);

        WebElement empOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='listbox']//span[contains(text(),'" + empName + "')]")));
        empOption.click();

        // Wait for Leave Type container to be visible
        WebElement leaveTypeContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//div[contains(@class,'oxd-select-wrapper')])[1]")));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", leaveTypeContainer);

        // Click dropdown to expand
        leaveTypeContainer.click();

        // Wait for dropdown option to appear and select
        WebElement leaveTypeOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option']//span[text()='" + leaveType + "']")));
        leaveTypeOption.click();

        // 3️⃣ From Date
        WebElement fromDateField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='From Date']/following::input[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", fromDateField);
        fromDateField.clear();
        fromDateField.sendKeys(fromDate);
        fromDateField.sendKeys(Keys.ENTER);

        // 4️⃣ To Date
        WebElement toDateField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='To Date']/following::input[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", toDateField);
        toDateField.clear();
        toDateField.sendKeys(toDate);
        toDateField.sendKeys(Keys.ENTER);

        

        
    
    }

    // Click Assign Button
 // Click Assign Button and handle modal
 // Click Assign Button and handle confirmation modal
    public void clickAssign() {
        // Click Assign button using JS to avoid ElementClickInterceptedException
        WebElement assignBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Assign']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", assignBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", assignBtn);

        // Wait for confirmation modal
        WebElement confirmModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'oxd-dialog-container')]")));

        // Click OK inside modal using JS
        WebElement okBtn = confirmModal.findElement(By.xpath(".//button[normalize-space()='Ok']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);

        System.out.println("Leave assigned confirmation clicked successfully.");
    }



    // Logout
    public void logout() {
        WebElement profileMenu = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[@class='oxd-userdropdown-name']"))
        );
        profileMenu.click();

        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[text()='Logout']"))
        );
        logoutBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }
}
