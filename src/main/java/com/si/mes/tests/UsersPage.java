package com.si.mes.tests;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.rules.Timeout;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class UsersPage {

	private WebDriver driver;
	private static Logger logger = Logger.getLogger(UsersPage.class);

	public UsersPage(WebDriver webDriver) {
		driver = webDriver;
	}

	/**
	 * Verify Title bar of AdminPortal is displayed
	 * 
	 * @param
	 * @return booleam
	 *
	 */
	public boolean isPageTitleDisplayed(String page) {

		return driver.findElement(By.xpath("//div[@id='title_bar']//h2[@id='page_title']")).getText().matches(page);

	}

	/**
	 * Click on Users Tab to load UsersPage
	 * 
	 * @param
	 * @return
	 *
	 */

	public void clickOnUsersTab() {
		driver.findElement(By.xpath("//li[@id='users']/a")).click();
		logger.info("waiting for Users Page load");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   	Assert.assertTrue(driver.findElement(By.xpath("//h2[@id='page_title']")).getText().matches("Users"));
		logger.info("Navigated to UsersPage");

	}

	/**
	 * Verifies UserFilterSection is displayed
	 * 
	 * @param
	 * @return
	 *
	 */

	public boolean isUserFilterSectionDisplayed() {
		return driver.findElement(By.xpath("//div[@id='filters_sidebar_section']")).isDisplayed();
	}

	/**
	 * Verifies UserFilterSection is displayed
	 * 
	 * @param
	 * @return
	 *
	 */

	public boolean isSearchStatusSectionDisplayed() {
		String xpathExpr = "//div[@id='search-status-_sidebar_section']";
		return isElementPresent(xpathExpr);
	}

	/**
	 * Verify Username Rule Fields are displayed and have Valid Value as in default.
	 * 
	 * @return boolean
	 * @throws Exception
	 */

	public boolean isUsernameRuleFieldsValid() {

		String baseXpathExpr = "//div[@id='q_username_input']/";

		boolean ruleLabel = driver.findElement(By.xpath(baseXpathExpr + "label")).getText().matches("USERNAME");
		/** @todo Add Exception Handling */
		boolean ruleCond = driver.findElements(By.xpath(baseXpathExpr + "select/option")).size() == 4;
		boolean ruleVal = isElementEnabledAndDisplayed(baseXpathExpr + "/input[@name='q[username_contains]']");

		if (ruleLabel && ruleCond && ruleVal) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Verify EMAIL Rule Fields are displayed and have Valid Value as in default.
	 * 
	 * @return boolean
	 */

	public boolean isEmailRuleFieldsValid() {

		String baseXpathExpr = "//div[@id='q_email_input']/";
		boolean ruleLabel = driver.findElement(By.xpath(baseXpathExpr + "label")).getText().matches("EMAIL");
		/** @todo Add Exception Handling */
		boolean ruleCond = driver.findElements(By.xpath(baseXpathExpr + "select/option")).size() == 4;
		boolean ruleVal = isElementEnabledAndDisplayed(baseXpathExpr + "/input[@name='q[email_contains]']");

		if (ruleLabel && ruleCond && ruleVal) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCreatedAtRuleFieldsValid() {

		String baseXpathExpr = "//div[@id='q_created_at_input']/";
		boolean ruleLabel = driver.findElement(By.xpath(baseXpathExpr + "label")).getText().matches("CREATED AT");
		boolean fromDate = driver.findElement(By.xpath(baseXpathExpr + "/input[@id='q_created_at_gteq_datetime']"))
				.getAttribute("placeholder").matches("From");
		boolean toDate = driver.findElement(By.xpath(baseXpathExpr + "/input[@id='q_created_at_lteq_datetime']"))
				.getAttribute("placeholder").matches("To");

		if (ruleLabel && fromDate && toDate) {
			return true;
		} else {
			return false;
		}

	}

	public void setUnameAndEmailRule(String uNameCond, String uNameVal, String emailCond, String emailVal) {
		String baseXpathExprUName = "//div[@id='q_username_input']";
		String baseXpathExprEmail = "//div[@id='q_email_input']";

		driver.findElement(By.xpath(baseXpathExprUName + "/select/option[contains(text(),'" + uNameCond + "')]"))
				.click();
		driver.findElement(By.xpath(baseXpathExprUName + "//input[@id='q_username']")).clear();
		driver.findElement(By.xpath(baseXpathExprUName + "//input[@id='q_username']")).sendKeys(uNameVal);
		logger.info("Uname Rule set"
				+ driver.findElement(By.xpath(baseXpathExprUName + "//input[@id='q_username']")).getText());

		driver.findElement(By.xpath(baseXpathExprEmail + "/select/option[contains(text(),'" + emailCond + "')]"))
				.click();
		driver.findElement(By.xpath(baseXpathExprEmail + "//input[@id='q_email']")).clear();
		driver.findElement(By.xpath(baseXpathExprEmail + "//input[@id='q_email']")).sendKeys(emailVal);
		logger.info("Email Rule set");
	}

	public void setCreatedAtRule(String fromDt, String toDt) {
		
		if (fromDt != null && !"".equals(fromDt)) {
			String xpathExpr = "//input[@id='q_created_at_gteq_datetime']";
			WebElement elementFromDate = driver.findElement(By.xpath(xpathExpr));
			elementFromDate.clear();
			elementFromDate.sendKeys(fromDt);
			elementFromDate.sendKeys(Keys.TAB);
		}
		if (toDt != null && !"".equals(toDt)) {
			String xpathExpr = "//input[@id='q_created_at_lteq_datetime']";
			//xpathExpr = xpathExpr.substring(0, 2) + "following-sibling::" + xpathExpr.substring(2, xpathExpr.length());
			WebElement elementToDate = driver.findElement(By.xpath(xpathExpr));
			elementToDate.clear();
			elementToDate.sendKeys(toDt);
			elementToDate.sendKeys(Keys.TAB);
		}
		logger.info("Created At Rule set");
	}

	public void clickFilterButton() {
		String xpathExpr = "//form[@id='new_q']/div[@class='buttons']/input[@type='submit']";
		
		if (isElementEnabledAndDisplayed(xpathExpr)) {
			driver.findElement(By.xpath(xpathExpr)).click();

		}

	}

	public void clickClearFiltersButton() {
		String xpathExpr = "//form[@id='new_q']/div[@class='buttons']/a";
		if (isElementEnabledAndDisplayed(xpathExpr)) {
			driver.findElement(By.xpath(xpathExpr)).click();

		}

	}

	public boolean isSearchStatusUnameRuleLabelDisplayedAndValid(String uNameCond, String uNameVal) {
		String xpathExpr = "//div[@id='search-status-_sidebar_section']//li[contains(@class,'current_filter_username')]";
		return isSearchStatusRuleLabel("username",xpathExpr, uNameCond, uNameVal);
	}

	public boolean isSearchStatusEmailRuleLabelDisplayedAndValid(String eMailCond, String eMailVal) {
		String xpathExpr = "//div[@id='search-status-_sidebar_section']//li[contains(@class,'current_filter_email')]";
		return isSearchStatusRuleLabel("email", xpathExpr, eMailCond, eMailVal);
	}

	public boolean isSearchStatusRuleLabel(String field, String xpath, String cond, String val) {
		if (val ==null || "".equals(val) ) {
			boolean nullsHaveLabelflag = isElementPresent(xpath);
			if (nullsHaveLabelflag) {
				return false;
			} else
				return true;
		} else {
			boolean sSlabelflag = isElementPresent(xpath);
			if (sSlabelflag) {
				String sSUnameLabel = driver.findElement(By.xpath(xpath + "/span")).getText();
				sSUnameLabel = sSUnameLabel +" "+driver.findElement(By.xpath(xpath + "/b")).getText();

				if (sSUnameLabel.equalsIgnoreCase(field +" "+ cond + " " + val)) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}

		}
	}
	
	
	public boolean isSearchStatusCreatedAtRuleLabelDisplayedAndValid(String fromDt, String toDt)
	{
		String xpathExpr = "//div[@id='search-status-_sidebar_section']//li[contains(@class,'current_filter_created_at')]";
		String ruleName = "created at";
		String gteqText = " greater or equal to ";
		String lteqText = " lesser or equal to ";
		String gteqXpath = xpathExpr.substring(0, 90) + "_gteq" + xpathExpr.substring(90, xpathExpr.length());
		String lteqXpath =xpathExpr.substring(0, 90) + "_lteq" + xpathExpr.substring(90, xpathExpr.length());
		
		boolean fromDatelabelflag =  isSearchStatusDateLabel(ruleName,gteqText,gteqXpath,fromDt );
		boolean toDatelableflag = isSearchStatusDateLabel(ruleName,lteqText,lteqXpath,toDt );
		if(fromDatelabelflag && toDatelableflag ){
		return true;
		}
		else {
		return false;
		}
	}
	
	public boolean isSearchStatusDateLabel(String field, String condText, String xpath, String date) {
		if (date ==null || "".equals(date) ) {
			boolean nullsHaveLabelflag = isElementPresent(xpath);
			if (nullsHaveLabelflag) {
				return false;
			} else
				return true;
		} else {
			boolean sSDatelabelflag = isElementPresent(xpath);
			if (sSDatelabelflag) {
				String sSUnameLabel = driver.findElement(By.xpath(xpath + "/span")).getText();
				sSUnameLabel = sSUnameLabel +" "+driver.findElement(By.xpath(xpath + "/b")).getText();

				if (sSUnameLabel.equalsIgnoreCase(field +condText+ date)) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}		
	}
	
	public boolean isResultSetValidforGivenRule(String ruleName,String ruleCond,String ruleValue )
	{
		String currentValue = null;
		StringBuilder errBuilder = new StringBuilder("Error in isResultSetValidforGivenRule: "+ruleName +" failed for " +ruleCond+" in row ");
		if(ruleValue == null || "".equals(ruleValue))
			return true;
		String lowerRuleValue = ruleValue.toLowerCase();
		int resultSetSize = driver.findElements(By.xpath("//table[@id = 'index_table_users']/tbody/tr")).size();	
			
		for(int i = 1 ; i <= resultSetSize; i++)
		{
			currentValue = driver.findElement(By.xpath("//table[@id = 'index_table_users']/tbody/tr["+i+"]//td[contains(@class,'"+ruleName+"')]")).getText();
			
			switch (ruleCond){
				case "Contains" : 					
					if(currentValue == null || !currentValue.toLowerCase().contains(lowerRuleValue)) {
						logger.error(errBuilder.append(i).toString() );
					    return false;
					}
					break;
				case "Equals" : 
					if(currentValue == null || !currentValue.toLowerCase().equals(lowerRuleValue)) {
						logger.error(errBuilder.append(i).toString() );
					    return false;
					}
					break;			                                        
				case "Starts with" :
					if(currentValue == null || !currentValue.toLowerCase().startsWith(lowerRuleValue)) {
						logger.error(errBuilder.append(i).toString() );
						return false;
					}
					break;
				case "Ends with" : 
					if(currentValue == null || !currentValue.toLowerCase().endsWith(lowerRuleValue)) {
						logger.error(errBuilder.append(i).toString() );
					    return false;
					}
					break;
				default:
					logger.error("Bad rule condition.");
					return false;
			}	
	       
			
		}		
		return true;		
	
	}
	
	public void clickNewUSerButton()
	{
		String xpathExpr = "//div[@id='titlebar_right']//a[contains(@href,'/admin/users/new')]";
		if (isElementEnabledAndDisplayed(xpathExpr))
		{
			driver.findElement(By.xpath(xpathExpr)).click();
			logger.info("Navigated to New User Page");
		}
	}
	
	public void clickDeleteLinkOnUser(String field) {
		String xpathExpr = "//table[@id = 'index_table_users']/tbody//td[contains(text(),'" + field + "')]";
		// String appStrSelectable = xpathExpr+"/preceding-sibling::td[@class='col
		// col-selectable']";
		String appStrDeletelink = xpathExpr
				+ "/following-sibling::td[@class='col col-actions']//a[contains(text(),'Delete')]";
		
		String windowHandle=driver.getWindowHandle();
		
		if (isElementEnabledAndDisplayed(appStrDeletelink)) {
			// driver.findElement(By.xpath(appStrSelectable)).click();
			driver.findElement(By.xpath(appStrDeletelink)).click();
		}
		
		for(int i=0; i < 3; i++) {
			try {
				Thread.sleep(1000);
				Alert alert = driver.switchTo().alert();
				if (alert != null) {
					logger.info("Accepting alert message to delete user.");
					alert.accept();
					Thread.sleep(1000);
					break;
				}
				driver.switchTo().window(windowHandle);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
	}



	private boolean isElementPresent(String xpath) {
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
