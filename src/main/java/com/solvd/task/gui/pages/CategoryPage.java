package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.ProductCategoryComponent;
import com.solvd.task.gui.models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryPage extends AbstractEbayPage {

    @FindBy(css = "nav[role='navigation']")
    private WebElement nav;

    @FindBy(css = "li.brwrvr__item-card--list")
    private List<ProductCategoryComponent> items;

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNavigationDisplayed() {
        return nav.isDisplayed();
    }

    public List<Product> getItems() {
        return items.stream()
                .map(productCategoryComponent ->
                        new Product(productCategoryComponent.getName(), productCategoryComponent.getPrice()))
                .collect(Collectors.toList());
    }
}
