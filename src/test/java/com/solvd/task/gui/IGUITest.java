package com.solvd.task.gui;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface IGUITest {
    Logger logger = LoggerFactory.getLogger(IGUITest.class);

    WebDriver getDriver();

    void setUp(String browser);

    void tearDown(ITestResult result);

    default void takeScreenshot(WebDriver driver, String filePath) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(screenshot.toPath(), Path.of(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
