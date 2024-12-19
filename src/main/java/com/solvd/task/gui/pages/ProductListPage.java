package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.ProductListComponent;
import com.solvd.task.gui.components.SearchResultsSideBar;
import com.solvd.task.gui.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductListPage extends AbstractEbayPage {

    private WebDriver driver;

    @FindBy(css = ".srp-results .s-item .s-item__image")
    private List<ProductListComponent> productElements;

    @FindBy(css = "div.srp-rail__left")
    private WebElement leftSideBar;

    public ProductListPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public List<Product> getProducts() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return productElements.stream()
                .map(product -> new Product(product.getTitle(),product.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductPage clickOnRandomProduct() {
        try {
            wait.until(webDriver -> !productElements.isEmpty() && productElements.get(0).getRoot().isDisplayed());
            int randomIndex = new Random().nextInt(productElements.size());
            productElements.get(randomIndex).getRoot().click();
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

    public SearchResultsSideBar getLeftSideBar() {
            return new SearchResultsSideBar(leftSideBar, driver);
    }
}
