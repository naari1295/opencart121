package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

import testBase.BaseClass;

public class Tc001_AccountRegistrationTest
        extends BaseClass {

    @Test(
        groups={"Regression","Master"}
    )

    public void verify_account_registration()
    {
        logger.info(
        "***** Starting Registration Test *****");

        try {

            // Home Page
            HomePage hp =
                    new HomePage(driver);

            hp.clickMyAccount();

            hp.clickRegister();

            // Registration Page
            AccountRegistrationPage regpage =
                    new AccountRegistrationPage(driver);

            regpage.setFirstName(
                    randomeString());

            regpage.setLastName(
                    randomeString());

            regpage.setEmail(
                    randomeString()
                    + "@gmail.com");

            regpage.setTelephone(
                    randomeNumber());

            String password =
                    randomeAlphaNumberic();

            regpage.setPassword(password);

            regpage.setConfirmPassword(password);

            regpage.setPrivacyPolicy();

            regpage.clickContinue();

            String confmsg =
                    regpage.getConfirmationMsg();

            Assert.assertEquals(
                    confmsg,
                    "Your Account Has Been Created!");

            logger.info(
            "Registration Passed");
        }

        catch(Exception e)
        {
            logger.error(
            "Registration Failed");

            logger.error(
            e.getMessage());

            Assert.fail(
            "Test Failed: "
            + e.getMessage());
        }
    }
}