package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.Dialog;
import com.solvd.task.gui.components.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractEbayPage extends AbstractPage {

    @FindBy(css = "header")
    protected WebElement header;

    @FindBy(css = "div .confirm-dialog__window")
    protected WebElement confirmDialog;

    public AbstractEbayPage(WebDriver driver) {
        super(driver);
    }

    public Header getHeader() {
        return new Header(header, driver);
    }

    public Dialog getConfirmDialog() {
        return new Dialog(confirmDialog, driver);
    }

    public boolean isConfirmDialogDisplayed() {
        try {
            if (confirmDialog.isDisplayed()) {
                logger.info("Confirm dialog is displayed");
            }
            return true;
        } catch (Exception e) {
            logger.info("Confirm dialog is not displayed");
            return false;
        }
    }
}
