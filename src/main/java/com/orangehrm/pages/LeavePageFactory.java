package com.orangehrm.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeavePageFactory {
    WebDriver driver;
    WebDriverWait wait;

 // Leave menu
    @FindBy(xpath = "//span[text()='Leave']")
    WebElement leaveMenu;

    // Assign Leave option inside menu
    @FindBy(xpath = "//a[contains(@href,'assignLeave')] | //a[text()='Assign Leave']")
    WebElement assignLeaveOption;

    // ===== Assign Leave Form =====
    @FindBy(xpath = "//input[contains(@placeholder,'Type for hints...')]")
    WebElement employeeNameField;

    @FindBy(xpath = "//label[text()='Leave Type']/../following-sibling::div//div[contains(@class,'oxd-select-text-input')]")
    WebElement leaveTypeDropdown;

    @FindBy(xpath = "//label[text()='From Date']/../following-sibling::div//input")
    WebElement fromDateField;

    @FindBy(xpath = "//label[text()='To Date']/../following-sibling::div//input")
    WebElement toDateField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement assignBtn;

    public LeavePageFactory(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigate to Assign Leave page
    public void goToAssignLeave() {
        // Maximize browser (optional but recommended)
        driver.manage().window().maximize();

        // Click Leave menu
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu)).click();

        // Wait until Assign Leave option is visible & clickable
        wait.until(ExpectedConditions.elementToBeClickable(assignLeaveOption)).click();
    }


    // Assign leave to employee
    public void assignLeave(String employeeName, String leaveType, String fromDate, String toDate) {
        // Wait for Employee Name field and type
        wait.until(ExpectedConditions.visibilityOf(employeeNameField)).sendKeys(employeeName);

        // Select Leave Type
        leaveTypeDropdown.click();
        driver.findElement(By.xpath("//div[@role='listbox']//span[text()='" + leaveType + "']")).click();

        // Set dates
        fromDateField.clear();
        fromDateField.sendKeys(fromDate);

        toDateField.clear();
        toDateField.sendKeys(toDate);

        // Click Assign
        assignBtn.click();
    }
}


