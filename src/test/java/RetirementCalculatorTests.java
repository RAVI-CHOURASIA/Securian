import java.util.ArrayList;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

/*
 * Test Class to run the tests, Extend Util Class to get the utility data and methods.
 */
public class RetirementCalculatorTests extends Util {
	private static final Logger LOGGER = Logger.getLogger(RetirementCalculatorTests.class.getName());
	private RetirementCalculatorSubmitForm retirementCalculatorSubmitForm;
	//Setup Extent report
	@BeforeSuite
	public void setUpExtentReport() {
		LOGGER.info("setUpExtentReport setting up extent report");
		extent = ExtentManager.getInstance();
	}
	
	//Before Test executed before the tests to setup the environment
	@BeforeTest
    public void setUp() {
		LOGGER.info("setUp set the webdriver system property");
       	System.setProperty(WEB_DRIVER_NAME, WEB_DRIVER_ADDRESS);
    	driver = new FirefoxDriver();
    	executor = (JavascriptExecutor) driver;
    	LOGGER.info("setUp get data from Excel sheet");
    	getDataFromExcel();
    	retirementCalculatorSubmitForm = new RetirementCalculatorSubmitForm(driver);
	}
	
	//Test case to verify the page title
	@Test
	public void verifyPageTitle() {
		LOGGER.info("verifyPageTitle");
		test = extent.createTest("Verify Page title");
		driver.get(baseURL);
		String expectedTitle = "How Much to Save for Retirement | Securian Financial";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle, "Title Mismatch");
	}
	
	//Test case to verify mandatory fields are non null
	@Test
	public void verifyMandatoryFields() {
		LOGGER.info("verifyMandatoryFields");
 	    test = extent.createTest("Verify Mandatory Fields");
 	    getUrlAndElements();
		Assert.assertNotNull(currentAge,"Current Age is null");
		Assert.assertNotNull(retirementAge,"Retirement Age is null");
		Assert.assertNotNull(currentAnnualIncome,"Current Annual Income is null");
		Assert.assertNotNull(currentRetirementSaving,"Current Retirement Saving Balance is null");
		Assert.assertNotNull(currentRetirementContribution,"Currently saving for retirement is null");
		Assert.assertNotNull(annualRetirementContribution,"Rate of increase in saving each year is null");
		Assert.assertNotNull(yesSocialSecurityIncome,"Social security yes button is null");
		Assert.assertNotNull(noSocialSecurityIncome,"Social security no button is null");
		Assert.assertNotNull(relationshipStatus,"Relationship status is null");
	}

	// User should be able to submit form with all required fields filled in
    @Test(priority = 1)
    public void testCase1() {
    	LOGGER.info("testCase1 - Fill the form with all the Mandatory fields in, social security is hidden");
    	try {
    		//Get the web page and find all the elements
        	getUrlAndElements();
	 	    test = extent.createTest("Test Case 1");
	 	    //Get the first row of data for Test Case 1
	 	    ArrayList<String> list = listData.get(0);
	 	    fillForm(list);
		 	clickCalculate();
    	} catch (Exception e) {
			Assert.fail("Failure in test case 1");
			e.printStackTrace();
    	}
    }

	// Additional Social Security fields should display/hide based on Social Security benefits toggle
    @Test(priority = 2)
    public void testCase2() {
    	LOGGER.info("testCase2 - Fill the form with all the Mandatory fields in, social security is expanded/selected.");
    	try {
        	getUrlAndElements();
	 	    test = extent.createTest("Test Case 2");
	 	    //Get the second row of data for Test Case 2
	 	    ArrayList<String> list = listData.get(1);
	 	    fillForm(list);
		 	clickCalculate();
    	} catch (Exception e) {
			Assert.fail("Failure in test case 2");
			e.printStackTrace();
    	}
    }
 
	// User should be able to submit form with all fields filled in, Social Security selected as NO
    @Test(priority = 3)
    public void testCase3() {
    	LOGGER.info("testCase3 - Fill the form with all the fields in, fill the Adjust default values form,"
    			+ " Social security is hidden");
    	try {
        	getUrlAndElements();
	 	    test = extent.createTest("Test Case 3");
	 	    //Get the third row of data for Test Case 3
	 	    ArrayList<String> list = listData.get(2);
	 	    fillForm(list);
		 	interactWithPopupForm(list);
		 	clickCalculate();
    	} catch (Exception e) {
			Assert.fail("Failure in test case 3");
			e.printStackTrace();
    	}
    }
 
	// User should be able to update default calculator values, Social Security selected as YES
    @Test(priority = 4)
    public void testCase4() {
    	LOGGER.info("testCase4 - Fill the form with all the fields in, fill the Adjust default values form,"
    			+ " Social security is expanded/selected");
    	try {
        	getUrlAndElements();
	 	    test = extent.createTest("Test Case 4");
	 	    //Get the fourth row of data for Test Case 4
	 	    ArrayList<String> list = listData.get(3);
	 	    fillForm(list);
		 	interactWithPopupForm(list);
		 	clickCalculate();
    	} catch (Exception e) {
			Assert.fail("Failure in test case 4");
			e.printStackTrace();
    	}
    }

    //Validate boundary condition , miss to fill in one mandatory field, enabled = false
    @Test(priority = 1, enabled = false)
    public void testCase5() {
    	LOGGER.info("testCase5 - Fill the form with mandatory field missing, social security is hidden");
    	try {
        	getUrlAndElements();
	 	    test = extent.createTest("Test Case 5");
	 	    //Get the fifth row of data for Test Case 5
	 	    ArrayList<String> list = listData.get(4);
	 	    fillForm(list);
		 	clickCalculate();
    	} catch (Exception e) {
			Assert.fail("Failure in test case 5");
			e.printStackTrace();
    	}
    }
    
    @AfterTest
    private void tearDown() {
    	LOGGER.info("tearDown");
    	driver.quit();
    }

    // Function to fill the form and assert if all the required fields are put in
	private void fillForm(ArrayList<String> list) {
		LOGGER.info("fillForm");
		String currAge = list.get(0);
		Assert.assertTrue(!currAge.isEmpty(), "Current Age is Mandatory field and is empty");
 	    currentAge.sendKeys(currAge);
 	    
 	    String retAge = list.get(1);
 	    Assert.assertTrue(!retAge.isEmpty(), "Retirement Age is Mandatory field and is empty");
 	    retirementAge.sendKeys(retAge);
 	    
 	    String curAnnIncome = list.get(2);
 	    Assert.assertTrue(!curAnnIncome.isEmpty(), "Current Annual income is Mandatory field and is empty");
	 	currentAnnualIncome.sendKeys(curAnnIncome);
	 	
	 	//NOT mandatory field
	 	spouseAnnualIncome.sendKeys(list.get(3));
	 	
 	    String currRetSav = list.get(4);
 	    Assert.assertTrue(!currRetSav.isEmpty(), "Current retirement saving is Mandatory field and is empty");
	 	currentRetirementSaving.sendKeys(currRetSav);
	 	
 	    String currRetCont = list.get(5);
 	    Assert.assertTrue(!currRetCont.isEmpty(), "Current retirement contribution is Mandatory field and is empty");
	 	currentRetirementContribution.sendKeys(currRetCont);
	 	
 	    String annRetCont = list.get(6);
 	    Assert.assertTrue(!annRetCont.isEmpty(), "Annual Retirement contribution is Mandatory field and is empty");
	 	annualRetirementContribution.sendKeys(annRetCont);
	 	
	 	// Execute javascript executor based on the input field yes or no.
	 	if (list.get(7).equalsIgnoreCase("Yes")) {
			LOGGER.info("fillForm unhiding for SocialSecurityIncome");
	 		jsExecutor(yesSocialSecurityIncome);
	 		if (list.get(8).equalsIgnoreCase("Married")) {
		 		jsExecutor(relationshipStatus);
	 		}
		 	socialSecurityOverride.sendKeys(list.get(9));
	 	} else {
	 		jsExecutor(noSocialSecurityIncome);
	 	}
	}

	// Function to click on calculate button for calculating
    private void clickCalculate() {
    	LOGGER.info("clickCalculate");
    	try {
        	Assert.assertNotNull(calculateButton, "Cannot find calculate button");
        	calculateButton.click();
    	} catch (Exception e) {
			Assert.fail("Failure in clicking button Calculate");
			e.printStackTrace();
    	}
	}

    // Function to interact with pop up form class
	public void interactWithPopupForm(ArrayList<String> list) throws Exception {
		LOGGER.info("interactWithPopupForm click adjust default value link");
 	    retirementCalculatorSubmitForm.clickAdjustDefaultValue();
 	    
 	    LOGGER.info("interactWithPopupForm sending keys for elements in retirementCalculatorSubmitForm");
 	    //Fill all the required fields
 	    retirementCalculatorSubmitForm.sendKeysToElement(additionalIncome, list.get(10));
 	    retirementCalculatorSubmitForm.sendKeysToElement(noOfYearsRetirementLasts, list.get(11));
 	    //Post Retirement income is Yes
 	    if (list.get(12).equalsIgnoreCase("Yes")) {
			LOGGER.info("interactWithPopupForm unhiding for Post retirement income");
 	    	jsExecutor(yesPostRetirementIncome);
 	 	    retirementCalculatorSubmitForm.sendKeysToElement(expectedInflationRate, list.get(13));
 	    } else {
 	    	jsExecutor(noPostRetirementIncome);
 	    }
 	    retirementCalculatorSubmitForm.sendKeysToElement(percentOfFinalAnnualIncome, list.get(14));
 	    retirementCalculatorSubmitForm.sendKeysToElement(preRetirementInvestment, list.get(15));
 	    retirementCalculatorSubmitForm.sendKeysToElement(postRetirementInvestment, list.get(16));
 	    LOGGER.info("interactWithPopupForm clickSaveChangesButton");
 	    retirementCalculatorSubmitForm.clickSaveChangesButton();
    }
}
