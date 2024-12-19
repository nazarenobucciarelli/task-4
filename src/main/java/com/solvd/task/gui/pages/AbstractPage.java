package com.solvd.task.gui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class AbstractPage {
    protected WebDriver driver;

    protected WebDriverWait wait;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractPage.class);

    @FindBy(xpath = "//iframe[@data-hcaptcha-response]")
    public static WebElement captcha;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public static WebElement getCaptcha() {
        try {
            if (captcha.isDisplayed()) {
                logger.info("Captcha is visible");
            }
            return captcha;
        } catch (Exception e) {
            logger.info("Captcha is not visible :)");
            return null;
        }
    }
}
