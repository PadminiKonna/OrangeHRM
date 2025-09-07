package com.orangehrm.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PIM_PageFactory {
    WebDriver driver;
    WebDriverWait wait;

    // ===== PIM Navigation =====
    @FindBy(xpath = "//span[text()='PIM']")
    WebElement pimMenu;

    @FindBy(xpath = "//a[text()='Add Employee']")
    WebElement addEmployeeOption;

    // ===== Add Employee Form =====
    @FindBy(name = "firstName")
    WebElement firstNameField;

    @FindBy(name = "middleName")
    WebElement middleNameField;

    @FindBy(name = "lastName")
    WebElement lastNameField;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement saveEmployeeBtn;

    // ===== Redirect → Personal Details =====
    @FindBy(xpath = "//h6[contains(normalize-space(), 'Personal Details')]")
    WebElement personalDetailsHeader;

    @FindBy(xpath = "//div[@class='orangehrm-edit-employee-name']/h6")
    WebElement employeeProfileName;

    // ===== Personal Details Edit Button =====
    @FindBy(xpath = "//button[contains(@class,'oxd-button') and text()='Edit']")
    WebElement editButton;

    // ===== Personal Details Fields =====
    @FindBy(name = "nickName")
    WebElement nickNameField;

    @FindBy(name = "otherId")
    WebElement otherIdField;

    @FindBy(name = "licenseNo")
    WebElement licenseNoField;

    @FindBy(xpath = "//label[text()='Date of Birth']/../following-sibling::div//input")
    WebElement dobField;

    @FindBy(xpath = "//label[text()='Male']/../input")
    WebElement genderMaleRadio;

    @FindBy(xpath = "//label[text()='Female']/../input")
    WebElement genderFemaleRadio;

    @FindBy(xpath = "//label[text()='Marital Status']/../following-sibling::div//div[@class='oxd-select-text-input']")
    WebElement maritalStatusDropdown;

    @FindBy(xpath = "//label[text()='Nationality']/../following-sibling::div//div[@class='oxd-select-text-input']")
    WebElement nationalityDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement savePersonalDetailsBtn;

    // ===== Constructor =====
    public PIM_PageFactory(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // increased wait
        PageFactory.initElements(driver, this);
    }

    // ===== Actions =====
    public void goToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeOption)).click();
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).sendKeys(firstName);
        middleNameField.sendKeys(middleName);
        lastNameField.sendKeys(lastName);
        saveEmployeeBtn.click();

        // Optional small pause for dynamic page
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
    }

    public boolean verifyRedirectToPersonalDetails(String firstName, String lastName) {
        try {
            // Wait for Personal Details header
            wait.until(ExpectedConditions.visibilityOf(personalDetailsHeader));

            // Wait until employee name is non-empty
            wait.until(driver -> !employeeProfileName.getText().trim().isEmpty());

            String profileName = employeeProfileName.getText().trim();
            System.out.println("🔎 Employee Profile Name: " + profileName);

            return profileName.contains(firstName) && profileName.contains(lastName);
        } catch (Exception e) {
            System.out.println("❌ Personal Details page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void savePersonalDetails() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(savePersonalDetailsBtn)).click();
            System.out.println("✅ Personal details saved successfully");
        } catch (Exception e) {
            System.out.println("❌ Could not click Save: " + e.getMessage());
        }
    }
}
