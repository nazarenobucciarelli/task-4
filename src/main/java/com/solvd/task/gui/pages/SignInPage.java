package com.solvd.task.gui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInPage extends AbstractPage{

    @FindBy(id = "userid")
    private WebElement userId;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(css = "button[data-testid='signin-continue-btn']")
    private WebElement signInContinueBtn;

    @FindBy(id = "sgnBt")
    private WebElement signInBtn;

    @FindBy(id = "signin-error-msg")
    private WebElement signInErrorMsg;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void typeUserId(String id) {
        try {
            userId.sendKeys(id);
            logger.info("User id entered: " + id);
        } catch (Exception e) {
            logger.error("Error while typing user id: " + e);
        }
    }

    public void clickSignInContinueBtn() {
        try{
            signInContinueBtn.click();
            logger.info("Continue button clicked");
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
        } catch (Exception e) {
            logger.error("Error while clicking continue button: " + e);
        }
    }

    public void typePassword(String password) {
        try {
            passwordInput.sendKeys(password);
            logger.info("Password entered: " + password);
        } catch (Exception e) {
            logger.error("Error while typing password: " + e);
        }
    }

    public void clickSignInBtn() {
        try {
            signInBtn.click();
            logger.info("Sign in button clicked");
            wait.until(ExpectedConditions.visibilityOf(signInErrorMsg));
        } catch (Exception e) {
            logger.error("Error while clicking Sign in button: " + e);
        }
    }

    public boolean isSignInErrorMsgDisplayed() {
        try {
            Thread.sleep(10000); // to solve captcha
            signInErrorMsg.isDisplayed();
            logger.info("Sign in error msg displayed");
            return true;
        } catch (Exception e) {
            logger.error("Error while displaying error msg: " + e);
            return false;
        }
    }
}
