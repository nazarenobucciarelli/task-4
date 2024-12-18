package com.solvd.task.gui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public abstract class AbstractGUITest implements IGUITest{

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        WebDriver driver;
        String driverPath = System.getProperty("user.dir") + "/src/test/resources/";

        driver = switch (browser.toLowerCase()) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                yield new ChromeDriver();
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
                yield new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        driverThreadLocal.set(driver);
        getDriver().manage().window().maximize();
        getDriver().get("https://www.m.ebay.co.uk");
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
