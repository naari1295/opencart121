package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

import testBase.BaseClass;

import utilities.DataProviders;

public class TC003_LoginDDT
        extends BaseClass {

    @Test(
        dataProvider = "LoginData",
        dataProviderClass =
        DataProviders.class,

        groups = {"Datadriven"}
    )

    public void verify_login_DDT(

            String email,
            String pwd,
            String exp)
            throws InterruptedException {

        logger.info(
        "***** Starting TC003_LoginDDT *****");

        try {

            // Home Page
            HomePage hp =
                    new HomePage(driver);

            logger.info(
            "Clicking My Account");

            hp.clickMyAccount();

            logger.info(
            "Clicking Login");

            hp.clickLogin();

            // Login Page
            LoginPage lp =
                    new LoginPage(driver);

            logger.info(
            "Entering Email");

            lp.setEmail(email);

            logger.info(
            "Entering Password");

            lp.setPassword(pwd);

            logger.info(
            "Clicking Login Button");

            lp.clickLogin();

            // My Account Page
            MyAccountPage myacc =
                    new MyAccountPage(driver);

            boolean targetPage =
                    myacc.isAccountExists();

            /*
             Positive Test Case
             Valid Login Expected
            */

            if(exp.equalsIgnoreCase("Valid"))
            {
                if(targetPage)
                {
                    logger.info(
                    "Login Passed with Valid Data");

                    myacc.clickLogout();

                    Assert.assertTrue(true);
                }

                else
                {
                    logger.error(
                    "Login Failed with Valid Data");

                    Assert.fail(
                    "Valid Login Failed");
                }
            }

            /*
             Negative Test Case
             Invalid Login Expected
            */

            else if(exp.equalsIgnoreCase("Invalid"))
            {
                if(targetPage)
                {
                    logger.error(
                    "Login Passed with Invalid Data");

                    myacc.clickLogout();

                    Assert.fail(
                    "Invalid Login Passed");
                }

                else
                {
                    logger.info(
                    "Login Failed as Expected");

                    Assert.assertTrue(true);
                }
            }

        }

        catch(Exception e)
        {
            logger.error(
            "Test Failed");

            logger.error(
            e.getMessage());

            Assert.fail(
            "Exception Occurred: "
            + e.getMessage());
        }

        Thread.sleep(1000);

        logger.info(
        "***** Finished TC003_LoginDDT *****");
    }
}