package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractEbayPage {
    private WebDriver driver;

    @FindBy(css = "header")
    protected WebElement header;

    public AbstractEbayPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public HeaderComponent getHeader() {
        return new HeaderComponent(header, driver);
    }

}
