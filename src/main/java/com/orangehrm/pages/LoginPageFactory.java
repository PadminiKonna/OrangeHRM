package com.orangehrm.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageFactory {
	 WebDriver driver;

	    @FindBy(name = "username")
		public
	    WebElement usernameField;

	    @FindBy(name = "password")
		public
	    WebElement passwordField;

	    @FindBy(xpath = "//button[@type='submit']")
		public
	    WebElement loginButton;
	    
	 // --- Logout locators ---
	    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
	    public WebElement userDropdown;

	    @FindBy(xpath = "//a[text()='Logout']")
	    public WebElement logoutLink;

		

	    public LoginPageFactory(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    public void enterUsername(String username) {
	        usernameField.sendKeys(username);
	    }

	    public void enterPassword(String password) {
	        passwordField.sendKeys(password);
	    }

	    public void clickLogin() {
	        loginButton.click();
	    }
	    public void logout() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        // Wait for dropdown, then click
	        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
	        // Wait for logout link, then click
	        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
	    }
	

}
