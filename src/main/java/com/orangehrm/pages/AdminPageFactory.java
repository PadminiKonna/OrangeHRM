package com.orangehrm.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPageFactory {

    WebDriver driver;

    // --- Login locators ---
    @FindBy(name = "username")
    public WebElement usernameField;

    @FindBy(name = "password")
    public WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginButton;

    // --- Logout locators ---
    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    public WebElement userDropdown;

    @FindBy(xpath = "//a[text()='Logout']")
    public WebElement logoutLink;

    // --- Admin tab and Add User ---
    @FindBy(xpath = "//span[text()='Admin']")
    public WebElement adminTab;

    @FindBy(xpath = "//button[contains(@class,'oxd-button') and text()=' Add ']")
    public WebElement addButton;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/div/div/div[2]")
    public WebElement userRoleDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[2]/div/div[2]/div/div/input")
    public WebElement employeeNameField;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[3]/div/div[2]/div/div/div[2]/i")
    public WebElement statusDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[4]/div/div[2]/input")
    public WebElement adminUsernameField;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[1]/div/div[2]/input")
    public WebElement adminPasswordField;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/div/div[2]/div/div[2]/input")
    public WebElement confirmPasswordField;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[3]/button[2]")
    public WebElement saveButton;

    // Constructor
    public AdminPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
 // --- Navigate to Nationalities ---
    @FindBy(xpath = "//span[text()='Admin']")
    public WebElement adminTabForNationalities; // you can reuse adminTab, optional

    @FindBy(xpath = "//a[contains(@href,'/admin/nationality')]")
    public WebElement nationalityMenu;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    public WebElement addNationalityButton;

    @FindBy(xpath = "//label[text()='Name']/following::input[1]")
    public WebElement nationalityNameField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveNationalityButton;

    // --- Login ---
    public void login(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    // --- Logout ---
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    // --- Navigate to Admin ---
    public void navigateToAdminTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
    }

    public void clickAddButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    // --- Select User Role ---
    public void selectUserRole(String role) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span"));
        for (WebElement opt : options) {
            if (opt.getText().equals(role)) {
                opt.click();
                break;
            }
        }
    }

 // --- Enter Employee Name with autocomplete ---
    public void enterEmployeeName(String empName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOf(employeeNameField));
        input.clear();

        // Type only partial name (first word)
        String partialName = empName.split(" ")[0];
        input.sendKeys(partialName);

        // Wait for suggestions
        List<WebElement> suggestions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@role='listbox']//span"))
        );

        // Debug: print all suggestions found
        for (WebElement s : suggestions) {
            System.out.println(" Suggestion: " + s.getText());
        }

        // Select the first available suggestion
        if (!suggestions.isEmpty()) {
            suggestions.get(0).click();
        } else {
            throw new RuntimeException(" No employee suggestions appeared for: " + empName);
        }
    }

    // --- Select Status ---
    public void selectStatus(String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']//span"));
        for (WebElement opt : options) {
            if (opt.getText().equals(status)) {
                opt.click();
                break;
            }
        }
    }

    // --- Enter Username / Password / Confirm Password ---
    public void enterAdminUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(adminUsernameField)).sendKeys(username);
    }

    public void enterAdminPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(adminPasswordField)).sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(confirmPasswordField)).sendKeys(password);
    }

    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    // --- Complete Add User workflow ---
    public void addUser(String role, String empName, String status, String username, String password, String confirmPassword) {
        navigateToAdminTab();
        clickAddButton();
        selectUserRole(role);
        enterEmployeeName(empName);
        selectStatus(status);
        enterAdminUsername(username);
        enterAdminPassword(password);
        enterConfirmPassword(confirmPassword);
        clickSave();
    }
    
 // --- Add Nationality method ---
    public void addNationality(String nationalityName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Click Admin tab (in case not already on it)
        wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
        
        // Click Nationalities menu
        wait.until(ExpectedConditions.elementToBeClickable(nationalityMenu)).click();
        
        // Click Add button
        wait.until(ExpectedConditions.elementToBeClickable(addNationalityButton)).click();
        
        // Enter nationality name
        wait.until(ExpectedConditions.visibilityOf(nationalityNameField)).sendKeys(nationalityName);
        
        // Click Save
        wait.until(ExpectedConditions.elementToBeClickable(saveNationalityButton)).click();
    }
}


