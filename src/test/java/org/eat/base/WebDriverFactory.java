package org.eat.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class WebDriverFactory {
    // Singleton
    // Only one instance of the class can exist at a time
    private static final WebDriverFactory instance = new WebDriverFactory();
    private WebDriverFactory() {
    }
    public static WebDriverFactory getInstance() {
        return instance;
    }
    private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();
    private static ThreadLocal<String> threadedBrowser = new ThreadLocal<>();
    public WebDriver getDriver(String browserName) {
        WebDriver driver = null;
        threadedBrowser.set(browserName);
        if (threadedDriver.get() == null) {
            if (browserName.equalsIgnoreCase("Firefox")) {
                driver = new FirefoxDriver();
            }
            if (browserName.equalsIgnoreCase("Chrome")) {
                driver = new ChromeDriver();
            }
            if (browserName.equalsIgnoreCase("Edge")) {
                driver = new EdgeDriver();
            }

            threadedDriver.set(driver);
            threadedDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            threadedDriver.get().manage().window().maximize();
        }
        return threadedDriver.get();
    }
    public String getBrowser() {
        return threadedBrowser.get();
    }
    public void quitDriver() {
        threadedDriver.get().quit();
        threadedDriver.set(null);
    }
}
