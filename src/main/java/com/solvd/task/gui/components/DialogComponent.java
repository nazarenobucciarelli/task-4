package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DialogComponent extends AbstractComponent {

    @FindBy(css = ".confirm-dialog__confirm")
    private WebElement confirmButton;

    public DialogComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public void clickConfirmButton() {
        try {
            wait.until(ExpectedConditions.visibilityOf(confirmButton));
            confirmButton.click();
            logger.info("Confirm button clicked");
        } catch (Exception e) {
            logger.error("Error occurred while clicking confirm button", e);
        }
    }

}
