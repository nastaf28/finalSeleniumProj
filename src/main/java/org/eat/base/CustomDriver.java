package org.eat.base;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eat.utilities.Constants;
import org.eat.utilities.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CustomDriver {

    private static final Logger log = LogManager.getLogger(CustomDriver.class.getName());

    public WebDriver driver;

    public CustomDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void refresh() {
        driver.navigate().refresh();
        log.info("The current browser location was refreshed...");
    }

    public String getTitle() {
        String title = driver.getTitle();
        log.info("Title of the page is :: " + title);
        return title;
    }

    public String getURL() {
        String url = driver.getCurrentUrl();
        log.info("Current URL is :: " + url);
        return url;
    }

    public By getByType(String locator) {
        By by = null;
        String locatorType = locator.split("=>")[0];
        locator = locator.split("=>")[1];
        try {
            if (locatorType.contains("id")) {
                by = By.id(locator);
            } else if (locatorType.contains("name")) {
                by = By.name(locator);
            } else if (locatorType.contains("xpath")) {
                by = By.xpath(locator);
            } else if (locatorType.contains("css")) {
                by = By.cssSelector(locator);
            } else if (locatorType.contains("tag")) {
                by = By.tagName(locator);
            } else if (locatorType.contains("class")) {
                by = By.className(locator);
            } else if (locatorType.contains("link")) {
                by = By.linkText(locator);
            } else if (locatorType.contains("partiallink")) {
                by = By.partialLinkText(locator);
            } else {
                log.info("Locator Type :: " + locatorType + " :: not supported");
            }
        } catch (Exception e) {
            log.error("By type not found with :: " + locatorType);
        }
        return by;
    }

    public WebElement getElement(String locator, String info) {
        By byType = getByType(locator);
        WebElement element = null;
        try {
            element = driver.findElement(byType);
            log.info("Element :: " + info + " :: found with locator :: " + locator);
        } catch (Exception e) {
            log.error("Element :: " + info + " :: not found with :: " + locator);
            e.printStackTrace();
        }
        return element;
    }

    public List<WebElement> getElementList(String locator, String info) {
        List<WebElement> elementList = new ArrayList<>();
        By byType = getByType(locator);
        try {
            elementList = driver.findElements(byType);
            if (elementList.size() > 0) {
                log.info("Element list found with locator :: " + locator);
            } else {
                log.info("Element list not found with locator :: " + locator);
            }
        } catch (Exception e) {
            log.error("Element List not found with :: " + locator);
            e.printStackTrace();
        }
        return elementList;
    }

    public boolean isElementPresent(String locator, String info) {
        List<WebElement> elementList = getElementList(locator, info);
        int size = elementList.size();
        if (size > 0) {
            log.info("Element :: " + info + " :: present with locator :: " + locator);
            return true;
        } else {
            log.info("Element :: " + info + " :: not present with locator :: " + locator);
            return false;
        }
    }

    public void clickElement(String locator, String info) {
        WebElement element = getElement(locator, info);
        try {
            element.click();
            log.info("Clicked on :: " + info + " :: with locator :: " + locator);
        } catch (Exception e) {
            log.error("Cannot click on :: " + info + " :: with locator :: " + locator);
        }
    }

    public void clickWhenReady(String locator, String info, int timeout) {
        try {
            WebElement element = null;
            By byType = getByType(locator);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.info("Waiting for max :: " + timeout + " :: for element to be clickable");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(byType));
            element.click();
            log.info("Clicked on :: " + info + " :: with locator :: " + locator);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error("Element not clickable after " + timeout + " seconds");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public void sendData(String locator, String data, String info, boolean clear) {
        WebElement element = getElement(locator, info);
        try {
            if (clear) {
                element.clear();
            }
            element.sendKeys(data);
            log.info("Sent data :: " + data + " on :: " + info + " with locator :: " + locator);
        } catch (Exception e) {
            log.error("Cannot send data :: " + data + " on :: " + info + " with locator :: " + locator);
            // TODO: take screenshot
        }
    }

    public void sendData(String locator, String data, String info) {
        sendData(locator, data, info, true);
    }

    public String findText(String locator, String info) {
        log.info("Getting text of :: " + info + " :: with locator :: " + locator);
        String text = "";
        WebElement element = getElement(locator, info);
        text = element.getText();
        if (text.length() == 0) {
            text = element.getAttribute("innerText");
        }
        if (!text.isEmpty()) {
            log.info("Text of :: " + info + " :: with locator :: " + locator + " is :: " + text);
        } else {
            log.error("Text not found on " + info + " :: with locator :: " + locator);
        }
        return text;
    }

    public String takeScreenshot(String methodName, String browserName) {
        String path = Constants.SCREENSHOTS_DIRECTORY + Util.getScreenshotName(methodName, browserName);
        try {
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
