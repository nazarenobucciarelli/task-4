package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.CartProductComponent;
import com.solvd.task.gui.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartPage extends AbstractEbayPage {

    @FindBy(css = "div.cart-bucket")
    private List<WebElement> cartProducts;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<CartProductComponent> getCartProducts() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cartProducts));
            logger.info("Cart products found");
            return cartProducts.stream()
                    .map(webElement -> new CartProductComponent(webElement, driver))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Cart products not found");
            return Collections.emptyList();
        }
    }

}
