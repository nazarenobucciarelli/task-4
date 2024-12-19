package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.CartProductComponent;
import com.solvd.task.gui.models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartPage extends AbstractEbayPage {

    @FindBy(css = "div.cart-bucket")
    private List<CartProductComponent> cartProducts;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public List<CartProductComponent> getCartProducts() {
        return cartProducts;
    }

    public List<Product> getProducts() {
        return getCartProducts().stream()
                .map(product -> new Product(product.getTitle(), null))
                .collect(Collectors.toList());
    }
}
