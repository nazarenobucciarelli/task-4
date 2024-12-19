package com.solvd.task.gui.pages;

import com.solvd.task.gui.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryPage extends AbstractEbayPage {

    @FindBy(css = "nav[role='navigation']")
    private WebElement nav;

    @FindBy(css = "li.brwrvr__item-card--list")
    private List<WebElement> items;

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNavigationDisplayed() {
        return nav.isDisplayed();
    }

    public List<Product> getProducts() {
        try{
            wait.until(ExpectedConditions.visibilityOfAllElements(items));
            return items.stream().map(webElement -> new Product(
                    webElement.findElement(By.cssSelector("h3")).getText(),
                    webElement.findElement(By.cssSelector(".brwrvr__item-card__signals__body")).getText()
            )).collect(Collectors.toList());
        } catch (Exception e){
            logger.error("Error while displaying items: {}", String.valueOf(e));
            return Collections.emptyList();
        }
    }
}
