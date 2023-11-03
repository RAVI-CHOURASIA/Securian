import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/*
 * RetirementCalculatorSubmitForm class to encapsulate pop up form functionality
 */
public class RetirementCalculatorSubmitForm {
	private WebDriver driver;
	private By adjustDefaultValuesLink = By.linkText("Adjust default values");
	private By saveChangesButton = By.xpath("//button[text()='Save changes']");

	public RetirementCalculatorSubmitForm(WebDriver driver) {
		this.driver = driver;
	}
	
	//Clicks on adjust default value link to open pop up form
	public void clickAdjustDefaultValue() {
		try {
			WebElement element = driver.findElement(adjustDefaultValuesLink);
			Assert.assertNotNull(element, "Cannot find adjust default values button");
			element.click(); 
		} catch (Exception e) {
			Assert.fail("Failure in clicking button adjust default values");
			e.printStackTrace();
		}
	}
	
	//Set the form data for a web element
	public void sendKeysToElement(WebElement element, String text) {
		try {
			element.sendKeys(text);
		} catch (Exception e) {
			Assert.fail("Failure in sending keys to element in RetirementCalculatorSubmitForm");
			e.printStackTrace();
		}
	}
	
	//Click on save changes button to close the pop up form and submit the form
	public void clickSaveChangesButton() {
		try {
			WebElement element = driver.findElement(saveChangesButton);
			Assert.assertNotNull(element, "Cannot find save changes button");
			element.click();
		} catch (Exception e) {
			Assert.fail("Failure in clicking button save changes");
			e.printStackTrace();
		}
	}
}
