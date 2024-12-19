package com.solvd.task.gui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductCategoryComponent extends AbstractComponent {

    @FindBy(css = "h3")
    private WebElement productName;

    @FindBy(css = ".brwrvr__item-card__signals__body")
    private WebElement price;

    public ProductCategoryComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getName() {
        try{
            String productName = this.productName.getText();
            logger.info("Product name is {}", productName);
            return productName;
        } catch (Exception e) {
            logger.error("Error while getting product name", e);
            return null;
        }
    }

    public String getPrice() {
        try{
            String productPrice = this.price.getText();
            logger.info("Product price is {}", productPrice);
            return productPrice;
        } catch (Exception e) {
            logger.error("Error while getting product price", e);
            return null;
        }
    }
}
