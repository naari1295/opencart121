package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

import testBase.BaseClass;

public class TC002_LoginTest
        extends BaseClass {

    @Test(
        groups={"Sanity","Master"}
    )

    public void verify_login()
    {
        logger.info(
        "***** Starting Login Test *****");

        try {

            // Home Page
            HomePage hp =
                    new HomePage(driver);

            hp.clickMyAccount();

            hp.clickLogin();

            // Login Page
            LoginPage lp =
                    new LoginPage(driver);

            // DEBUG
            System.out.println(
            "Email is: "
            + p.getProperty("email"));

            System.out.println(
            "Password is: "
            + p.getProperty("password"));

            lp.setEmail(
                    p.getProperty("email"));

            lp.setPassword(
                    p.getProperty("password"));

            lp.clickLogin();

            // My Account Page
            MyAccountPage myacc =
                    new MyAccountPage(driver);

            boolean status =
                    myacc.isAccountExists();

            Assert.assertTrue(status);

            logger.info(
            "Login Passed");
        }

        catch(Exception e)
        {
            logger.error(
            "Login Failed");

            logger.error(
            e.getMessage());

            Assert.fail(
            "Test Failed: "
            + e.getMessage());
        }

        logger.info(
        "***** Finished Login Test *****");
    }
}