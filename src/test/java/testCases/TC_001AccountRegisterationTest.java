package testCases;

import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseTest.BaseClass;
import pageObjects.HomePage;
import pageObjects.RegisterPage;

public class TC_001AccountRegisterationTest extends BaseClass {

	@Test(groups = { "Sanity", "Master" })
	public void verify_Account_Registeration() {

		logger.info("********* Starting TC_001AccountRegisterationTest *******");
		try {
			HomePage hp = new HomePage(driver);

			logger.info("** Clicking on My Account link **");
			hp.clickMyAccount();

			logger.info("Clicking on 'Register' link");
			hp.clickRegister();

			RegisterPage regPage = new RegisterPage(driver);

			logger.info("** Providing Customer details**");
			String fName = firstName().toUpperCase();
			String lName = lastName().toUpperCase();
			String email = randomEmail() + "@gmail";

			logger.debug("Generated First Name: " + fName);
			logger.debug("Generated Last Name: " + lName);
			logger.debug("Generated Email: " + email);

			regPage.setFirstName(fName);
			regPage.setLastName(lName);
			regPage.setEmail(email);

			regPage.setTelephone("1234567890");
			regPage.setPassword("nan123");
			regPage.setConfirmPassword("nan123");

			logger.info("Selecting Newsletter subscription option");
			regPage.clickSubscribe();
			logger.info("Accepting Privacy Policy");
			regPage.clickPrivacyPolicy();
			logger.info("Clicking on Continue button");
			regPage.clickContinue();

			logger.info("Validating expected message...");
			String confMsg = regPage.getConfirmationMsg();

			logger.debug("Actual confirmation message: " + confMsg);

			if (confMsg.equals("Your Account Has Been Created!")) {
				logger.info("********* Test Passed: Account Registration successful *********");
				Assert.assertTrue(true);

			} else {
				logger.error("Test Failed..");
				logger.debug("debug logs...");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			logger.error("Test Failed due to exception: " + e.getMessage());
			Assert.fail("Test Failed due to exception: " + e.getMessage());

		}

		logger.info("********* Test Finished: Account Registration *********");
	}

}
