package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager
        implements ITestListener {

    public ExtentSparkReporter sparkReporter;

    public ExtentReports extent;

    public ExtentTest test;

    String repName;

    @Override
    public void onStart(
            ITestContext testContext)
    {
        String timeStamp =
                new SimpleDateFormat(
                "yyyy.MM.dd.HH.mm.ss")
                .format(new Date());

        repName =
                "Test-Report-"
                + timeStamp
                + ".html";

        sparkReporter =
                new ExtentSparkReporter(
                System.getProperty("user.dir")
                + "\\reports\\"
                + repName);

        sparkReporter.config()
                .setDocumentTitle(
                "OpenCart Automation Report");

        sparkReporter.config()
                .setReportName(
                "OpenCart Functional Testing");

        sparkReporter.config()
                .setTheme(
                Theme.DARK);

        extent =
                new ExtentReports();

        extent.attachReporter(
                sparkReporter);
    }

    @Override
    public void onTestSuccess(
            ITestResult result)
    {
        test =
        extent.createTest(
        result.getName());

        test.log(
        Status.PASS,
        result.getName()
        + " Passed");
    }

    @Override
    public void onTestFailure(
            ITestResult result)
    {
        test =
        extent.createTest(
        result.getName());

        test.log(
        Status.FAIL,
        result.getName()
        + " Failed");

        test.log(
        Status.INFO,
        result.getThrowable()
        .getMessage());
    }

    @Override
    public void onFinish(
            ITestContext testContext)
    {
        extent.flush();

        String path =
                System.getProperty("user.dir")
                + "\\reports\\"
                + repName;

        File reportFile =
                new File(path);

        try {

            Desktop.getDesktop()
                    .browse(
                    reportFile.toURI());

        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}