package com.orangehrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    static String projectPath = System.getProperty("user.dir");

    public static ExtentReports getinstance() {
        if (extent == null) {
            String reportPath = projectPath + "\\src\\test\\resources\\Reports\\ReportsOrangeHRMreport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}