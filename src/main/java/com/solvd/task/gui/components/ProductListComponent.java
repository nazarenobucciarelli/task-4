package com.solvd.task.gui.components;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductListComponent extends AbstractComponent {

    @FindBy(css = ".s-item__title span")
    private WebElement title;

    @FindBy(css = ".s-item__price")
    private WebElement price;

    public ProductListComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getTitle() {
        try {
            String titleText = title.getText();
            logger.info("Title text is: {}", titleText);
            return titleText;
        } catch (Exception e) {
            logger.error("Error while getting title", e);
            return null;
        }
    }

    public String getPrice() {
        try{
            String priceText = price.getText();
            logger.info("Price  is: {}", priceText);
            return priceText;
        } catch (Exception e) {
            logger.error("Error while getting Price", e);
            return null;
        }
    }

}

