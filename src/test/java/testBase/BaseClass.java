package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties p;

    @BeforeMethod(alwaysRun = true)

    @Parameters({"browser","os"})

    public void setup(String br, String os)
            throws Exception
    {

        logger =
                LogManager.getLogger(this.getClass());

        // Load Config Properties
        FileInputStream fis =
                new FileInputStream(
                        System.getProperty("user.dir")
                                + "\\src\\test\\resources\\config.properties");

        p = new Properties();

        p.load(fis);

        // LOCAL EXECUTION
        if(p.getProperty("execution_env")
                .equalsIgnoreCase("local"))
        {

            if(br.equalsIgnoreCase("chrome"))
            {

                ChromeOptions options =
                        new ChromeOptions();

                driver =
                        new ChromeDriver(options);
            }
        }

        // REMOTE EXECUTION
        else if(p.getProperty("execution_env")
                .equalsIgnoreCase("remote"))
        {

            System.out.println(
                    "Connecting to Selenium Grid...");

            switch(br.toLowerCase())
            {

                case "chrome":

                    ChromeOptions chromeOptions =
                            new ChromeOptions();

                    chromeOptions.addArguments(
                            "--disable-dev-shm-usage");

                    chromeOptions.addArguments(
                            "--no-sandbox");

                    chromeOptions.addArguments(
                            "--disable-gpu");

                    chromeOptions.addArguments(
                            "--remote-allow-origins=*");

                    driver =
                            new RemoteWebDriver(

                                    URI.create(
                                            "http://localhost:4444")
                                            .toURL(),

                                    chromeOptions);

                    break;

                case "firefox":

                    FirefoxOptions firefoxOptions =
                            new FirefoxOptions();

                    driver =
                            new RemoteWebDriver(

                                    URI.create(
                                            "http://localhost:4444")
                                            .toURL(),

                                    firefoxOptions);

                    break;
            }

            System.out.println(
                    "Grid Connected Successfully");
        }

        // Wait
        Thread.sleep(3000);

        // Browser Settings
        driver.manage().timeouts()
                .implicitlyWait(
                        Duration.ofSeconds(10));

        driver.manage().window()
                .maximize();

        // Open Application
        driver.get(
                p.getProperty("appURL"));

        logger.info(
                "Application Launched");
    }

    @AfterMethod(alwaysRun = true)

    public void tearDown()
    {
        if(driver != null)
        {
            driver.quit();

            driver = null;

            logger.info(
                    "Browser Closed");
        }
    }

    // Screenshot Method
    public String captureScreen(
            String tname)
            throws IOException
    {

        String timeStamp =
                new SimpleDateFormat(
                        "yyyyMMddhhmmss")
                        .format(new Date());

        TakesScreenshot ts =
                (TakesScreenshot) driver;

        File sourceFile =
                ts.getScreenshotAs(
                        OutputType.FILE);

        String targetFilePath =
                System.getProperty("user.dir")
                        + File.separator
                        + "screenshots"
                        + File.separator
                        + tname + "_"
                        + timeStamp + ".png";

        File targetFile =
                new File(targetFilePath);

        targetFile.getParentFile()
                .mkdirs();

        FileUtils.copyFile(
                sourceFile,
                targetFile);

        return targetFilePath;
    }

    // Random String
    public String randomeString()
    {
        return RandomStringUtils
                .randomAlphabetic(5);
    }

    // Random Number
    public String randomeNumber()
    {
        return RandomStringUtils
                .randomNumeric(10);
    }

    // Random AlphaNumeric
    public String randomeAlphaNumberic()
    {
        return RandomStringUtils
                .randomAlphanumeric(8);
    }
}