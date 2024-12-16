package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.ShoppingCartEbayPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Dialog extends AbstractComponent {

    @FindBy(css = ".confirm-dialog__confirm")
    private WebElement confirmButton;

    public Dialog(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public ShoppingCartEbayPage clickConfirmButton() {
        try {
            confirmButton.click();
            logger.info("Confirm button clicked");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-summary-call-to-action")));
            return new ShoppingCartEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error occurred while clicking confirm button", e);
            return null;
        }
    }
}
