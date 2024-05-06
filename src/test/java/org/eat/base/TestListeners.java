package org.eat.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eat.utilities.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;

public class TestListeners implements ITestListener {

    private static ExtentReports extent = ExtentManager.createReportInstance();
    // Thread-safe
    // Refer to the Google Docs example in the recording, at around 1 hour 50 min mark
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    private static final Logger log = LogManager.getLogger(TestListeners.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        log.info("************************************************************");
        log.info("Test Method :: " + result.getMethod().getMethodName() + " Started");
        log.info("************************************************************");
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " +
                result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("************************************************************");
        log.info("Test Method :: " + result.getMethod().getMethodName() + " Completed Successfully");
        log.info("************************************************************");
        String message = "<b>Test Method " + result.getMethod().getMethodName() + " Successful</b>";
        Markup m = MarkupHelper.createLabel(message, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("************************************************************");
        log.info("Test Method :: " + result.getMethod().getMethodName() + " Failed");
        log.info("************************************************************");
        String methodName = result.getMethod().getMethodName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                "Exception Occurred, click to see the details:" +
                "</font></b></summary>" +
                exceptionMessage.replace(",", "<br>") + "</details> \n");

        String browserName = WebDriverFactory.getInstance().getBrowser();
        WebDriver driver = WebDriverFactory.getInstance().getDriver(browserName);
        CustomDriver cd = new CustomDriver(driver);
        String path = cd.takeScreenshot(result.getName(), browserName);
        try {
            extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            extentTest.get().fail("Test Failed, cannot attach screenshot");
        }

        String message = "<b>Test Method " + methodName + " Failed</b>";
        Markup m = MarkupHelper.createLabel(message, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, m);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message = "<b>Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
        Markup m = MarkupHelper.createLabel(message, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
    }

    @Override
    public void onStart(ITestContext context) {
//        System.out.println("Before <test> tag of xml file");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
