package com.si.mes.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.si.mes.io.ExcelReader;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MesWebTests {

	private static Logger logger = Logger.getLogger(MesWebTests.class);
	private static WebDriver driver;
	private static String baseUrl = "http://ec2-54-174-213-136.compute-1.amazonaws.com:8080/admin";
	private static String dataSheetPath = "./src/main/resources/FiltersData.xls";
	private static UsersPage usersPage;
	private static NewUserPage newUserPage;
	private static ExcelReader eReader;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		logger.info("Driver initialized!!");
		usersPage = new UsersPage(driver);
		newUserPage = new NewUserPage(driver);
		driver.get(baseUrl);
		logger.info("Loading application web page!!");
		eReader = new ExcelReader();
		eReader.readExcel(dataSheetPath);
		Assert.assertTrue("Dashboard PageTitle not displayed", usersPage.isPageTitleDisplayed("Dashboard"));
		usersPage.clickOnUsersTab();
		logger.info("Navigated to UsersPage");
	}

	@Test
	public void test1ValidateDefaultFilterView() {
		logger.info("Running validateDefaultFilterView test on UserPage....");
		assertTrue("Users PageTitle not displayed", usersPage.isPageTitleDisplayed("Users"));
		assertTrue("FilterSection is NOT displayed", usersPage.isUserFilterSectionDisplayed());
		assertFalse("SearchStatus Section is displayed", usersPage.isSearchStatusSectionDisplayed());
		assertTrue("Default uname rule is NOT valid", usersPage.isUsernameRuleFieldsValid());
		logger.info("USERNAME label, condition and value fiels are validated");
		Assert.assertTrue("Default email rule view is NOT valid", usersPage.isEmailRuleFieldsValid());
		logger.info("EMAIL label, condition and value fiels are validated");
		Assert.assertTrue("Default Created At Rule is NOT valid", usersPage.isCreatedAtRuleFieldsValid());
		logger.info("CREATED AT label, Default From field,To field verified");
	}

	@Test
	public void test2ApplyAndValidateFilters() {
		logger.info("Running validateFilterUsers test on UserPage....");
		for (Iterator<Row> rowIterator = eReader.getSheet(0).rowIterator(); rowIterator.hasNext();) {
			Row row = rowIterator.next();
			if(row.getRowNum() == 0)
			{
				continue;
			}
			else
			{
			    usersPage.clickClearFiltersButton();
			    logger.info("Setting Rules for DataSheet_Row"+row.getRowNum());	
			 	usersPage.setUnameAndEmailRule(ExcelReader.getValue(row.getCell(0)), ExcelReader.getValue(row.getCell(1)), ExcelReader.getValue(row.getCell(2)), ExcelReader.getValue(row.getCell(3)));
				usersPage.setCreatedAtRule(ExcelReader.getValue(row.getCell(4)),ExcelReader.getValue(row.getCell(5)));
				usersPage.clickFilterButton();
				logger.info("Filters Applied");
			    assertTrue("Search Status Username rule label have failed", usersPage.isSearchStatusUnameRuleLabelDisplayedAndValid(ExcelReader.getValue(row.getCell(0)),ExcelReader.getValue(row.getCell(1))));
			    assertTrue("Search Status Email rule label have failed", usersPage.isSearchStatusEmailRuleLabelDisplayedAndValid(ExcelReader.getValue(row.getCell(2)),ExcelReader.getValue(row.getCell(3))));
			    assertTrue("Search Status Created At rule label have failed", usersPage.isSearchStatusCreatedAtRuleLabelDisplayedAndValid(ExcelReader.getValue(row.getCell(4)),ExcelReader.getValue(row.getCell(5))));
			    logger.info("Applied Filter Labels in Search Section Validated");
			    assertTrue(usersPage.isResultSetValidforGivenRule("username", ExcelReader.getValue(row.getCell(0)), ExcelReader.getValue(row.getCell(1))));
			    assertTrue(usersPage.isResultSetValidforGivenRule("email", ExcelReader.getValue(row.getCell(2)), ExcelReader.getValue(row.getCell(3))));
			    logger.info("Validated ResulSet for Username, Email for selected filter");
			    
			}
		}
		usersPage.clickClearFiltersButton();
	}
	
	
	@Test
	public void test3CreateUser()
	{
		String[] savedUNameeMail = new String[2];
		String[] errMsgDuplicateFileds= new String[2];
		Row  row =eReader.getSheet(1).getRow(1);
		
		logger.info("Running test3ValidateNewUserAction test on UserPage....");
		usersPage.clickNewUSerButton();
		assertTrue("NewUser PageTitle not displayed", usersPage.isPageTitleDisplayed("New User"));
		assertTrue("Required Indicator NOT displayed for Uname or Pswd", newUserPage.isReqIndicatorDisplayedForUNameAndPswd());
		
	    //All Valid Inputs
		newUserPage.setInputfields(ExcelReader.getValue(row.getCell(0)), ExcelReader.getValue(row.getCell(1)), ExcelReader.getValue(row.getCell(2)));
	    newUserPage.clickCreateUserButton();	 
	    assertTrue("New User Success Message NOT displayed", newUserPage.isNewUserCreatedMessageDisplayed());
	    logger.info("Navigated to Edit/Delete User Page and User Success Message displayed");
	    savedUNameeMail = newUserPage.getValuesFromCustomerDetails();
	    assertEquals("Entered Username doesn't match with Saved Username", ExcelReader.getValue(row.getCell(0)), savedUNameeMail[0]);
	    assertEquals("Entered eMail doesn't match with Saved eMail", ExcelReader.getValue(row.getCell(2)), savedUNameeMail[1]);
	    
	    logger.info("NewUser creation Validated");
	    
	    usersPage.clickOnUsersTab();
		logger.info("Navigated to UsersPage");
		usersPage.clickNewUSerButton();
		logger.info("Navigated to NewUsersPage");
		
		//Enter Duplicates and verify error
		row =eReader.getSheet(1).getRow(2);
		newUserPage.setInputfields(ExcelReader.getValue(row.getCell(0)), ExcelReader.getValue(row.getCell(1)), ExcelReader.getValue(row.getCell(2)));
		newUserPage.clickCreateUserButton();
		errMsgDuplicateFileds =newUserPage.getErrorMsgForDuplicates();
	    assertEquals("Error Msg for Duplicate Uname Entry not matching", ExcelReader.getValue(row.getCell(3)), errMsgDuplicateFileds[0]);
	    assertEquals("Error Msg for Duplicate Uname Entry not matching", ExcelReader.getValue(row.getCell(5)), errMsgDuplicateFileds[1]);	    
	    logger.info("Error Message for Duplicate entries Validated");
	    
		
	    //Delete User for the test to pass in next run
	    usersPage.clickOnUsersTab();
	    usersPage.clickDeleteLinkOnUser(ExcelReader.getValue(row.getCell(0)));
	    assertTrue("User Destroyed message is not displayed",  newUserPage.isUserDestroyeddMessageDisplayed());
	    logger.info("Deleted Username: " +ExcelReader.getValue(row.getCell(0))+ "Successfully");
	    
	   
	    			
	}
	
	@Test
	public void test4ErrorMsgesNewUSerPage() {
		
		String[] errMsgRequiredInvalidFields= new String[3];
		usersPage.clickNewUSerButton();		
		
		// Empty and Invalid Inputs
		for (Iterator<Row> rowIterator = eReader.getSheet(1).rowIterator(); rowIterator.hasNext();) {
			Row row = rowIterator.next();
			if(row.getRowNum() <=2)
			{
				continue;
			}
			else
			{   
				newUserPage.setInputfields(ExcelReader.getValue(row.getCell(0)), ExcelReader.getValue(row.getCell(1)), ExcelReader.getValue(row.getCell(2)));
				newUserPage.clickCreateUserButton();	
			    errMsgRequiredInvalidFields = newUserPage.getErrorMsgForRequiredInvalid();
			    assertEquals("Error Msg for Invalid Uname Entry not matching", ExcelReader.getValue(row.getCell(3)), errMsgRequiredInvalidFields[0]);
			    assertEquals("Error Msg for Invalid pswd Entry not matching", ExcelReader.getValue(row.getCell(4)), errMsgRequiredInvalidFields[1]);
			     assertEquals("Error Msg for Invalid email Entry not matching", ExcelReader.getValue(row.getCell(5)), errMsgRequiredInvalidFields[2]);
			    if (row.getRowNum() == 3)
			    logger.info( "Error Messages on Invalid  inputs validated");			    
			    else
			    logger.info( "Error Messages on empty inputs validated");			    
			 }
			    	
		}
	}

	

	@AfterClass
	public static void cleanUp() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
