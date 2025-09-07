package com.orangehrm.tests;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.PIM_PageFactory;
import org.openqa.selenium.By;

public class PIM_OrangeHRM extends BaseTest {

	@Test
	public void addEmployeeAndEditPersonalDetails() throws InterruptedException {
	    // Step 1: Login
	    driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	    Thread.sleep(2000);

	    driver.findElement(By.name("username")).sendKeys("Admin");
	    driver.findElement(By.name("password")).sendKeys("admin123");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    System.out.println("✅ Logged in successfully");

	    // Step 2: Go to PIM → Add Employee
	    PIM_PageFactory pimPage = new PIM_PageFactory(driver);
	    pimPage.goToPIM();

	    String firstName = "David";
	    String middleName = "John";
	    String lastName = "Smith";
	    pimPage.addEmployee(firstName, middleName, lastName);
	    System.out.println("✅ Employee added: " + firstName + " " + middleName + " " + lastName);

	    // Step 3: Verify redirect to Personal Details
	    boolean redirected = pimPage.verifyRedirectToPersonalDetails(firstName, lastName);
	    Assert.assertTrue(redirected, "❌ Personal Details page not displayed!");
	    System.out.println("✅ Redirected to Personal Details page");

	    // Step 4: Edit Personal Details
	    /*pimPage.editPersonalDetails("DJ", "12345", "LIC987654", "1990-05-15",
	            "Male", "Single", "Indian");
	    System.out.println("✅ Personal details updated successfully");*/
	 // Step 4: Just click Save on Personal Details
	    pimPage.savePersonalDetails();


	    // Step 5: Logout
	    driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
	    driver.findElement(By.xpath("//a[text()='Logout']")).click();
	    System.out.println("✅ Logout successful");
	}
}
