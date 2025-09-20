package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	// Initialize driver
	public HomePage(WebDriver driver) {
		super(driver);

	}

	// Locators

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	private WebElement myAccountDropdown;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	private WebElement linkRegister;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	private WebElement linkLogin;
	// Action Method

	public void clickMyAccount() {
		myAccountDropdown.click();
	}

	public void clickRegister() {
		linkRegister.click();
	}
	
	public void clickLogin() {
		linkLogin.click();
	}

}
