package com.solvd.task.gui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoryEbayPage extends AbstractEbayPage {

    @FindBy(css = "nav[role='navigation']")
    private WebElement nav;

    @FindBy(css = "li.brwrvr__item-card--list")
    private List<WebElement> items;

    public CategoryEbayPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNavigationDisplayed() {
        return nav.isDisplayed();
    }

    public List<WebElement> getItems() {
        try{
            List<WebElement> items = this.items;
            logger.info("Number of items displayed: " + items.size());
            return items;
        } catch (Exception e){
            logger.error("Error while getting items displayed: ", e);
            return null;
        }
    }
}
