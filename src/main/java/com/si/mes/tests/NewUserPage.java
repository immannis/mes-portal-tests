package com.si.mes.tests;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class NewUserPage {

	private WebDriver driver;
	private static Logger logger = Logger.getLogger(NewUserPage.class);

	public NewUserPage(WebDriver webDriver) {
		driver = webDriver;
	}




   public boolean isReqIndicatorDisplayedForUNameAndPswd() {
	   
	   boolean unameFlg=driver.findElement(By.xpath("//fieldset[@class='inputs']//label[contains(text(),'Username')]/abbr")).getAttribute("title").matches("required");
	   boolean pswdFlg=driver.findElement(By.xpath("//fieldset[@class='inputs']//label[contains(text(),'Password')]/abbr")).getAttribute("title").matches("required");
	   if( unameFlg && pswdFlg) {
		   return true;
	   }
	   else {
		   return false;
	   }
	   
   }


   public void setInputfields(String uName, String pswd, String eMail)
   {
	   String baseXpath = "//fieldset[@class='inputs']";
	   String uNameXpath = baseXpath+"//input[@id='user_username']";
	   String pswdXpath= baseXpath+"//input[@id='user_password']";
	   String eMailXpath= baseXpath+"//input[@id='user_email']";
	   if(isElementEnabledAndDisplayed(uNameXpath)) {
	   WebElement uNameEle= driver.findElement(By.xpath(uNameXpath));
	   uNameEle.clear();
	   uNameEle.sendKeys(uName);
	   }
	   
	   if(isElementEnabledAndDisplayed(pswdXpath)) {
	   WebElement pswdEle= driver.findElement(By.xpath(pswdXpath));
	   pswdEle.clear();
	   pswdEle.sendKeys(pswd);
	   }
	   
	   if(isElementEnabledAndDisplayed(eMailXpath)) {
	   WebElement eMailEle = driver.findElement(By.xpath(eMailXpath));
	   eMailEle.clear();
	   eMailEle.sendKeys(eMail);	
	   }
	   
   }
   
   public void clickCreateUserButton(){
	  String xpathExpr = "//li[@id='user_submit_action']/input[@type='submit']";   
	   if(isElementEnabledAndDisplayed(xpathExpr)) {
		   driver.findElement(By.xpath(xpathExpr)).click();		   
	   }
   
   }
   
   public void clickCancelButton(){
		  String xpathExpr = "//li[@class='cancel']/a[contains(text(),'Cancel')]";   
		   if(isElementEnabledAndDisplayed(xpathExpr)) {
			   driver.findElement(By.xpath(xpathExpr)).click();		   
		   }
	   
	   }
   
 public String[] getErrorMsgForDuplicates() {
	 
	 String[] errMsgForDups = new String[2];
	   
	   String uNameXpath = "//li[@id ='user_username_input']/p[@class = 'inline-errors']";
	   String eMailXpath="//li[@id ='user_email_input']/p[@class = 'inline-errors']";
	   
	   errMsgForDups[0] = driver.findElement(By.xpath(uNameXpath)).getText();
	   errMsgForDups[1] = driver.findElement(By.xpath(eMailXpath)).getText();
	   
	   return errMsgForDups;
   }
 
 
 public String[] getErrorMsgForRequiredInvalid() {
	 
	 String[] errMsgForRequiredInvalid = new String[3];
	   
	   String uNameXpath = "//li[@id ='user_username_input']/p[@class = 'inline-errors']";
	   String pswdXpath="//li[@id ='user_password_input']/p[@class = 'inline-errors']";
	   String eMailXpath="//li[@id ='user_email_input']/p[@class = 'inline-errors']";
	   
	   errMsgForRequiredInvalid[0] = driver.findElement(By.xpath(uNameXpath)).getText();
	   errMsgForRequiredInvalid[1] = driver.findElement(By.xpath(pswdXpath)).getText();
	   errMsgForRequiredInvalid[2] = driver.findElement(By.xpath(eMailXpath)).getText();
	   
	   return errMsgForRequiredInvalid;
   }
 
 
 public boolean isUserDestroyeddMessageDisplayed() {
	   
	   return driver.findElement(By.xpath("//div[@id='wrapper']//div[contains(text(),'User was successfully destroyed.')]")).isDisplayed();
 }

/**
 * @TODO Move this method to EditUserPage class when building tests for Edit User
 * DeleteUser from EditUser Page
 */
   public void clickDeleteUserButton(){
		  String xpathExpr = "//li[@class='cancel']/a[contains(text(),'Cancel')]";   
		   if(isElementEnabledAndDisplayed(xpathExpr)) {
			   driver.findElement(By.xpath(xpathExpr)).click();				  
			  }
	   
	   }
   
   /**
    * @TODO Move this method to EditUserPage class when building tests for Edit User
    */
   public boolean isNewUserCreatedMessageDisplayed() {
	   
	   return driver.findElement(By.xpath("//div[@id='wrapper']//div[contains(text(),'User was successfully created.')]")).isDisplayed();
   }

   /**
    * @TODO Move this method to EditUserPage class when building tests for Edit User
    * Implement Capture Date Timestamp at the user creation and compare with CreatedDate
    */
   public String[] getValuesFromCustomerDetails() {
	   
	   String[] getCreatedUserDetails = new String[2];
	   String baseXpathExpr = "//div[@class='attributes_table user']//tr[@class='row row";
	   
	   getCreatedUserDetails[0] = driver.findElement(By.xpath(baseXpathExpr +"-username']/td")).getText();
	   getCreatedUserDetails[1] = driver.findElement(By.xpath(baseXpathExpr +"-email']/td")).getText();	   
	   
	   return getCreatedUserDetails;
   }

	public boolean isElementPresent(String xpath) {
		try {
			return driver.findElement(By.xpath(xpath)) != null;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isElementEnabledAndDisplayed(String xpathExpression) {
		boolean isElementEnabledAndPresent = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathExpression));
			if (element.isDisplayed() && element.isEnabled()) {
				isElementEnabledAndPresent = true;
			}
		} catch (Exception e) {
			isElementEnabledAndPresent = false;
		}
		return isElementEnabledAndPresent;
	}

}
