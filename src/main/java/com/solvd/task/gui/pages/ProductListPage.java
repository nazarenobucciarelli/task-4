package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.SearchResultsLeftSideBar;
import com.solvd.task.gui.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProductListPage extends AbstractEbayPage {

    @FindBy(css = ".srp-results .s-item .s-item__wrapper")
    private List<WebElement> productElements;

    @FindBy(css = "div.srp-rail__left")
    private WebElement leftSideBar;

    public ProductListPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
    }

    public List<Product> getProducts() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
            List<Product> products = productElements.stream()
                    .map(product -> new Product(
                            product.findElement(By.cssSelector(".s-item__title")).getText(),
                            product.findElement(By.cssSelector(".s-item__price")).getText()
                    ))
                    .toList();
            logger.info("Products found");
            return products;
        } catch (Exception e) {
            logger.error("Error while getting products", e);
            return Collections.emptyList();
        }
    }

    public ProductPage clickOnRandomProduct() {
        try {
            wait.until(webDriver -> !productElements.isEmpty() && productElements.get(0).isDisplayed());
            int randomIndex = new Random().nextInt(productElements.size());
            productElements.get(randomIndex).click();
            logger.info("Clicked on Random Product");

            Set<String> windowHandles = driver.getWindowHandles();
            String currentWindowHandle = driver.getWindowHandle();

            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(currentWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.x-item-title__mainTitle")));

            return new ProductPage(driver);
        } catch (Exception e) {
            logger.error("Error while clicking random product", e);
            return null;
        }
    }

    public SearchResultsLeftSideBar getLeftSideBar() {
        return new SearchResultsLeftSideBar(leftSideBar, driver);
    }
}
