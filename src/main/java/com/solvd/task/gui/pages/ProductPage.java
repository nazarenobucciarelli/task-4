package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.Dialog;
import com.solvd.task.gui.components.SelectOptionModal;
import com.solvd.task.gui.components.ShoppingCartOverlay;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends AbstractEbayPage {

    @FindBy(css = "div.x-atc-action")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@data-testid='x-msku-evo']/div[not(@hidden)]")
    private List<WebElement> selectOptionButtons;

    @FindBy(css = "h1.x-item-title__mainTitle span")
    private WebElement productName;

    @FindBy(css = "span.listbox-button--expanded")
    private WebElement selectOptionModal;

    @FindBy(css = "div.confirm-dialog__window")
    private WebElement confirmationDialog;

    @FindBy(css = "div[data-testid='ux-overlay'][aria-hidden='false']")
    private WebElement overlay;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void selectRandomOptions() {
        try {
            for (WebElement button : selectOptionButtons) {
                wait.until(ExpectedConditions.visibilityOf(button));
                SelectOptionModal selectModal = clickOptionButton(button);
                selectModal.selectRandomOption();
                wait.until(ExpectedConditions.invisibilityOf(selectOptionModal));
                logger.info("Random option selected");
            }
        } catch (Exception e) {
            logger.error("Error occurred while clicking random option", e);
        }
    }

    public SelectOptionModal clickOptionButton(WebElement button) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();
            logger.info("Options button clicked");
            wait.until(ExpectedConditions.visibilityOf(selectOptionModal));
            return new SelectOptionModal(selectOptionModal, driver);
        } catch (Exception e) {
            logger.error("Error occurred while clicking option button", e);
            return null;
        }
    }

    public ShoppingCartOverlay clickAddToCartButton() {
        try {
            addToCartButton.click();
            logger.info("Add to cart button clicked");
            wait.until(ExpectedConditions.visibilityOf(overlay));
            return new ShoppingCartOverlay(overlay,driver);
        } catch (Exception e) {
            logger.error("Error occurred while clicking add to cart button", e);
            return null;
        }
    }

    public boolean isAddToCartButtonPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addToCartButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
            if (addToCartButton.isDisplayed()) {
                logger.info("Add to cart button present");
            }
            return true;
        } catch (Exception e) {
            logger.error("Add to cart button is not present");
            return false;
        }
    }

    public String getProductName() {
        try {
            logger.info("Product name displayed: {}", productName.getText());
            return productName.getText();
        } catch (Exception e) {
            logger.error("Error getting product name", e);
            return null;
        }
    }

    public boolean isConfirmationDialogPresent() {
        try {
            boolean isDisplayed = confirmationDialog.isDisplayed();
            logger.info("Confirmation dialog present");
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Confirmation dialog is not present", e);
            return false;
        }
    }

    public Dialog getConfirmationDialog() {
        try {
            wait.until(ExpectedConditions.visibilityOf(confirmationDialog));
            Dialog dialog = new Dialog(confirmationDialog, driver);
            logger.info("Confirmation dialog obtained");
            return dialog;
        } catch (Exception e) {
            logger.error("Error getting confirmation dialog", e);
            return null;
        }
    }
}
