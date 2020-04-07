package com.weborder.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.weborder.utilities.BrowserUtilities;
import com.weborder.utilities.ConfigurationReader;
import com.weborder.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.*;




public class AbstractBaseTest {

    protected WebDriver driver = Driver.getDriver();

    protected static ExtentReports extentReports;
    protected static ExtentHtmlReporter extentHtmlReporter;
    protected static ExtentTest extentTest;

    @BeforeTest
    public void beforeTest() {
        extentReports = new ExtentReports();
        String reportPath = "";
        // location of report file
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            reportPath = System.getProperty("user.dir") + "/test-output/report.html";
        } else {
            reportPath = System.getProperty("user.dir") + "\\test-output\\report.html";
        }

        extentHtmlReporter = new ExtentHtmlReporter(reportPath);
        extentReports.attachReporter(extentHtmlReporter);
        extentHtmlReporter.config().setReportName("WebOrders Automation");
    }

    @AfterTest
    public void afterTest() {
        extentReports.flush();

    }

    @BeforeMethod
    public void setup() {
        driver.get(ConfigurationReader.getProperty("url"));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void teardown(ITestResult testResult) {

        if(testResult.getStatus() == ITestResult.FAILURE) {
           String screenshotLocation = BrowserUtilities.getScreenshot(testResult.getName());
            try {
                extentTest.fail(testResult.getName()); //test name that failed
                extentTest.addScreenCaptureFromPath(screenshotLocation); //screenshot as an evidence
                extentTest.fail(testResult.getThrowable()); //error message
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to attach screenshot");
            }
        } else if(testResult.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(testResult.getName());
        } else if(testResult.getStatus() == ITestResult.SKIP) {
            extentTest.skip(testResult.getName());
        }
        BrowserUtilities.wait(3);
        Driver.closeDriver();

    }
}
