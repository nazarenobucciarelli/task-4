package com.solvd.task.gui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public abstract class SeleniumGridTest implements IGUITest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);

        URL hubUrl;
        try {
            hubUrl = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Hub URL is invalid.", e);
        }

        WebDriver driver = new RemoteWebDriver(hubUrl, capabilities);
        driverThreadLocal.set(driver);

        getDriver().get("https://www.m.ebay.co.uk");
    }


    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            WebDriver driver = getDriver();
            takeScreenshot(driver, "screenshots/" + result.getName() + "-" + LocalDateTime.now() + ".png");
        }
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
        }
    }

}
