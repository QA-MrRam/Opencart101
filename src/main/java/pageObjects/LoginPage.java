package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//input[@id='input-email']")
	private WebElement txt_Email;

	@FindBy(xpath = "//input[@id='input-password']")
	private WebElement txt_Password;

//	@FindBy(cssSelector = "div[class='form-group'] a")
	@FindBy(css = "div.form-group a")
	private WebElement link_ForgottonPassword;

	@FindBy(xpath = "//input[@value='Login']")
	private WebElement btn_Login;

	public void setEmail(String email) {
		txt_Email.sendKeys(email);
	}

	public void setPassword(String password) {
		txt_Password.sendKeys(password);
	}

	public void clickLogin() {
		btn_Login.click();
	}
}
