package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

	// Initialize driver
	public RegisterPage(WebDriver driver) {
		super(driver);
	}

	// Locators

	@FindBy(xpath = "//input[@id='input-firstname']")
	private WebElement txt_Firstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	private WebElement txt_Lastname;

	@FindBy(xpath = "//input[@id='input-email']")
	private WebElement txt_Email;

	@FindBy(xpath = "//input[@id='input-telephone']")
	private WebElement txt_Telephone;

	@FindBy(xpath = "//input[@id='input-password']")
	private WebElement txt_Password;

	@FindBy(xpath = "//input[@id='input-confirm']")
	private WebElement txt_ConfirmPassword;

	@FindBy(xpath = "//label[normalize-space()='Yes']")
	private WebElement radio_Subscribe;

	@FindBy(xpath = "//input[@name='agree']")
	private WebElement check_Privacypolicy;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement btn_Continue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	private WebElement confirmationMsg;

	public void setFirstName(String fname) {
		txt_Firstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
		txt_Lastname.sendKeys(lname);
	}

	public void setEmail(String email) {
		txt_Email.sendKeys(email);
	}

	public void setTelephone(String telephone) {
		txt_Telephone.sendKeys(telephone);
	}

	public void setPassword(String password) {
		txt_Password.sendKeys(password);
	}

	public void setConfirmPassword(String password) {
		txt_ConfirmPassword.sendKeys(password);
	}

	public void clickSubscribe() {
		radio_Subscribe.click();
	}

	public void clickPrivacyPolicy() {
		check_Privacypolicy.click();
	}

	public void clickContinue() {
		btn_Continue.click();
	}

	public String getConfirmationMsg() {
		try {
			return (confirmationMsg.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}

	}
}
