package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.CategoryPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class ShopByCategoryModalComponent extends AbstractComponent {

    @FindBy(css = ".scnd")
    private List<WebElement> categories;

    public ShopByCategoryModalComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public CategoryPage clickRandomCategory() {
        try {
            int randomIndex = new Random().nextInt(categories.size());
            categories.get(randomIndex).click();
            logger.info("Random Category Clicked");
            return new CategoryPage(driver);
        } catch (Exception e) {
            logger.error("Error clicking on random category", e);
            return null;
        }
    }
}
