package com.solvd.task.gui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Product extends AbstractComponent {

    @FindBy(css = ".s-item__title span")
    private WebElement title;

    @FindBy(css = ".s-item__price")
    private WebElement price;

    public Product(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String getTitle() {
        return this.title.getText();
    }

    public String getPrice() {
        return this.price.getText();
    }
}
