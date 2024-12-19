package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractEbayPage extends AbstractPage {

    @FindBy(css = "header")
    protected WebElement header;


    public AbstractEbayPage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent getHeader() {
        return new HeaderComponent(header, driver);
    }

}
