package org.eat.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createReportInstance() {
        String fileName = Util.getReportName();
        String directory = Constants.REPORTS_DIRECTORY;
        new File(directory).mkdir();
        String path = directory + fileName;

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Reports");
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.setSystemInfo("Organization", "EAT");
        extent.setSystemInfo("Browser", "Chrome");
        extent.attachReporter(htmlReporter);

        return extent;
    }

   // public static String getReportName() {
     //   Date d = new Date();
       // String fileName = "AutomationReport" + "_" + d.toString()
         //       .replace(":", "_").replace(" ", "_") + ".html";
        //return fileName;
    //}
}
