package com.orangehrm.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class TimePageFactory {

    WebDriver driver;
    WebDriverWait wait;

    public TimePageFactory(WebDriver driver) {
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Time']")));
    }
    
 // --- Logout locators ---
    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    public WebElement userDropdown;

    @FindBy(xpath = "//a[text()='Logout']")
    public WebElement logoutLink;


    // Navigate to Time Module
    public void navigateToTimeModule() {
        WebElement timeMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Time']")));
        timeMenu.click();
    }

    // Search Employee and Click View
 // Search Employee and Click View
    public void searchEmployeeAndView(String empName) {
        WebElement empField = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@placeholder='Type for hints...']")
        ));
        empField.sendKeys(empName);

        // Select from dropdown
        WebElement empOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='listbox']//span[contains(text(),'" + empName + "')]")
        ));
        empOption.click();

        // Click View (correct locator)
        WebElement viewBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/form/div[2]/button")
        ));
        viewBtn.click();

        // Wait for Timesheet page
       /* wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[contains(text(),'Timesheet')]")
        ));*/
    }

    // Create Timesheet
    public void createTimesheet() {
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/form/div[3]/div[2]/button")
        ));
        createBtn.click();

        // Wait for page update
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[contains(text(),'Timesheet for')]")
        ));
    }

    // Approve Timesheet
    public void submitTimesheet() {
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/form/div[3]/div[2]/button[2]")
        ));
        submitBtn.click();

        // Wait for success message
        /*wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Successfully')]")
        ));*/
    }
 // Approve Timesheet
    public void approveTimesheet() {
        // Locate the approve button
        WebElement approveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[normalize-space()='Approve']")));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveBtn);

        // Wait until clickable and click
        wait.until(ExpectedConditions.elementToBeClickable(approveBtn)).click();

        // Wait for success message
        /*wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Successfully Approved')]")));*/
    }


    // Logout
 // Logout
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for dropdown, then click
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        // Wait for logout link, then click
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

}
