package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractEbayPage extends AbstractPage {

    @FindBy(css = "header")
    protected WebElement header;

    public AbstractEbayPage(WebDriver driver) {
        super(driver);
    }

    public Header getHeader() {
        return new Header(header, driver);
    }
}
