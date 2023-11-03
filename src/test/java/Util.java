import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/*
 * Utility class containing base initilizations and Utility functions
 */
public class Util {
	//Base URL of website
	protected final String baseURL = "https://www.securian.com/insights-tools/retirement-calculator.html";
	//Webdriver details
	protected final String WEB_DRIVER_NAME = "webdriver.gecko.driver";
	protected final String WEB_DRIVER_ADDRESS = "C:\\Users\\ravi.chourasia\\Downloads\\geckodriver-v0.33.0-win64\\geckodriver.exe";
	//Excel sheet for input data
	protected final String EXCEL_FILE_ADDR = "data/Securian.xlsx";
	protected final String EXCEL_SHEET_NAME = "Sheet1";
	
	protected WebDriver driver;
	protected JavascriptExecutor executor;
	protected ExtentReports extent;
	protected ExtentTest test;
	
	//List of List of String data containing values from each row and column in excel sheet
	protected ArrayList<ArrayList<String>> listData = new ArrayList<ArrayList<String>>();
	
	//WebElement objects
	protected WebElement currentAge;
	protected WebElement retirementAge;
	protected WebElement currentAnnualIncome;
	protected WebElement spouseAnnualIncome;
	protected WebElement currentRetirementSaving;
	protected WebElement currentRetirementContribution;
	protected WebElement annualRetirementContribution;
	protected WebElement yesSocialSecurityIncome;
	protected WebElement noSocialSecurityIncome;
	protected WebElement relationshipStatus;
	protected WebElement socialSecurityOverride;
	protected WebElement additionalIncome;
	protected WebElement noOfYearsRetirementLasts;
	protected WebElement yesPostRetirementIncome;
	protected WebElement noPostRetirementIncome;
	protected WebElement expectedInflationRate;
	protected WebElement percentOfFinalAnnualIncome;
	protected WebElement preRetirementInvestment;
	protected WebElement postRetirementInvestment;
	protected WebElement calculateButton;
	
	/*
	 * Function to input data into ArrayList from excel sheet.
	 * Total rows = 5 (starting from row 1)
	 * Total columns = 17 (starting from column A)
	 */
	public void getDataFromExcel() {
		FileInputStream file;
		Workbook workbook = null;
		try {
			file = new FileInputStream(new File(EXCEL_FILE_ADDR));
			workbook = WorkbookFactory.create(file);
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(EXCEL_SHEET_NAME);
		//DataFormatter to format the data irrespective of field type
		DataFormatter formatter = new DataFormatter();
		
		//Iterate through 5 rows and 17 columns and store the data into ArrayList of Lists
		for (int i = 1; i <=5; i++) {
			Row row = sheet.getRow(i);
			ArrayList<String> list = new ArrayList<>();
			for (int j = 0; j <= 16; j++) {
				String val = formatter.formatCellValue(row.getCell(j));
				list.add(val);
			}
			listData.add(list);
		}
	}
	
	/*
	 * Launch the base URL website and find the specified elements beforehand
	 */
	protected void getUrlAndElements() {
    	//Launch
    	driver.get(baseURL);
		try {
			currentAge = driver.findElement(By.id("current-age"));
			retirementAge = driver.findElement(By.id("retirement-age"));
			currentAnnualIncome = driver.findElement(By.id("current-income"));
			spouseAnnualIncome = driver.findElement(By.id("spouse-income"));
			currentRetirementSaving = driver.findElement(By.id("current-total-savings"));
			currentRetirementContribution = driver.findElement(By.id("current-annual-savings"));
			annualRetirementContribution = driver.findElement(By.id("savings-increase-rate"));
			yesSocialSecurityIncome = driver.findElement(By.id("yes-social-benefits"));
			noSocialSecurityIncome = driver.findElement(By.id("no-social-benefits"));
			relationshipStatus = driver.findElement(By.id("married"));
			socialSecurityOverride = driver.findElement(By.id("social-security-override"));
			additionalIncome = driver.findElement(By.id("additional-income"));
			noOfYearsRetirementLasts = driver.findElement(By.id("retirement-duration"));
			yesPostRetirementIncome = driver.findElement(By.id("include-inflation"));
			noPostRetirementIncome = driver.findElement(By.id("exclude-inflation"));
			expectedInflationRate = driver.findElement(By.id("expected-inflation-rate"));
			percentOfFinalAnnualIncome = driver.findElement(By.id("retirement-annual-income"));
			preRetirementInvestment = driver.findElement(By.id("pre-retirement-roi"));
			postRetirementInvestment = driver.findElement(By.id("post-retirement-roi"));
			calculateButton = driver.findElement(By.xpath("//button[text()='Calculate']"));
		} catch (NoSuchElementException e) {
			System.out.println("No Such Element Exception caught while finding element");
			Assert.fail("Failure in fetching element");
			e.printStackTrace();
		}
	}
	
	/*
	 * Execute the javascript for unhiding/hiding of elements
	 */
	protected void jsExecutor(WebElement element) {
		executor.executeScript("arguments[0].click();", element);
	}
}