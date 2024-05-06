package org.eat.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eat.base.CustomDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static final Logger log = LogManager.getLogger(Util.class.getName());

    public static String getScreenshotName(String methodName, String browserName) {
        String date = getCurrentDateTime();
        StringBuilder name = new StringBuilder()
                .append(browserName + "_")
                .append(methodName + "_")
                .append(date)
                .append(".png");
        return name.toString();
    }

    public static String getReportName() {
        String date = getCurrentDateTime();
        StringBuilder name = new StringBuilder()
                .append("AutomationReport_")
                .append(date)
                .append(".html");
        return name.toString();
    }

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i=0; i<length; i++) {
            int index = (int)(Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public static String getRandomString() {
        return getRandomString(8);
    }

    public static int getRandomNumber(int min, int max) {
        return -1;
    }

    public static String getCurrentDateTime(String format) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String formattedDate = formatter.format(date);
        log.info("Date with format :: " + format + " :: " + formattedDate);
        return formattedDate;
    }

    public static String getCurrentDateTime() {
        String date = getCurrentDateTime("MM/dd/yyyy HH:mm:ss").replace("/", "_");
        date = date.replace(":", "_").replace(" ", "_");
        log.info("Current Date :: " + date);
        return date;
    }

    public static boolean verifyTextContains(String actualText, String expText) {
        log.info("Actual Text from Web Application UI :: " + actualText);
        log.info("Expected Text from Web Application UI :: " + expText);
        if (actualText.toLowerCase().contains(expText.toLowerCase())) {
            log.info("### Verification Contains !!!");
            return true;
        } else {
            log.info("### Verification DOES NOT Contains !!!");
            return false;
        }
    }

    public static boolean verifyTextMatch(String actualText, String expText) {
        log.info("Actual Text from Web Application UI :: " + actualText);
        log.info("Expected Text from Web Application UI :: " + expText);
        if (actualText.equals(expText)) {
            log.info("### Verification Contains !!!");
            return true;
        } else {
            log.info("### Verification DOES NOT Contains !!!");
            return false;
        }
    }

    public static boolean verifyListContains(String actualText, String expText) {
        return false;
    }

    public static boolean verifyListMatch(String actualText, String expText) {
        return false;
    }
}
