package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.CategoryEbayPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class ShopByCategoryModal extends AbstractComponent {

    @FindBy(css = ".scnd")
    private List<WebElement> categories;

    public ShopByCategoryModal(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public CategoryEbayPage clickRandomCategory() {
        try {
            int randomIndex = new Random().nextInt(categories.size());
            categories.get(randomIndex).click();
            logger.info("Random Category Clicked");
            return new CategoryEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error clicking on random category", e);
            return null;
        }
    }
}
