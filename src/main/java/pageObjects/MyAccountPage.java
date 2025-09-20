package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	private WebElement msgHeading;

	@FindBy(xpath = "(//a[contains(text(),'Logout')])[2]")
	private WebElement linkLogout;

	public boolean isMyAccountPageExists() {
		try {
			return (msgHeading.isDisplayed());

		} catch (Exception e) {
			return false;
		}
	}

	public void clickLogout() {
		linkLogout.click();
	}

}
