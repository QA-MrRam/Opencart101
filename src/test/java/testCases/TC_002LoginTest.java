package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import baseTest.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC_002LoginTest extends BaseClass {

	@Test(groups = { "Regression", "Master" })
	public void verify_Login() {

		logger.info("********* Starting TC_002LoginTest ***********");
		try {

			HomePage hp = new HomePage(driver);
			logger.info("** Click on My Account link **");
			hp.clickMyAccount();

			logger.info("** Click on Login link **");
			hp.clickLogin();

			LoginPage logPage = new LoginPage(driver);

			logger.info("** Entering Email: " + p.getProperty("username") + " **");
			logPage.setEmail(p.getProperty("username"));

			logger.info("** Entering Password **");
			logPage.setPassword(p.getProperty("password"));

			logger.info("** Clicking Login button **");
			logPage.clickLogin();

			MyAccountPage myAcc = new MyAccountPage(driver);
			logger.info("** Verifying My Account Page existence **");

			boolean targetPage = myAcc.isMyAccountPageExists();
			Assert.assertEquals(targetPage, true, "Login Failed");

			logger.info("** Login Test Passed **");
		} catch (Exception e) {
			logger.error("Exception occurred during Login Test: " + e.getMessage());
			Assert.fail();
		}

		logger.info("******** finished TC_002LoginTest");
	}

}
