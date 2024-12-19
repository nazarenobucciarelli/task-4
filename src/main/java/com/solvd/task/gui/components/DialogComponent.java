package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DialogComponent extends AbstractComponent {

    @FindBy(css = ".confirm-dialog__confirm")
    private WebElement confirmButton;

    public DialogComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public ShoppingCartPage clickConfirmButton() {
        try {
            confirmButton.click();
            logger.info("Confirm button clicked");
            return new ShoppingCartPage(driver);
        } catch (Exception e) {
            logger.error("Error occurred while clicking confirm button", e);
            return null;
        }
    }

}
