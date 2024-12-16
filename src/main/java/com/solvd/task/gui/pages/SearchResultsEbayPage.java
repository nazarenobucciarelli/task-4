package com.solvd.task.gui.pages;

import com.solvd.task.gui.components.Product;
import com.solvd.task.gui.components.SearchResultsSideBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchResultsEbayPage extends AbstractEbayPage {

    @FindBy(css = ".srp-results .s-item")
    private List<WebElement> productElements;

    @FindBy(css = "div.srp-rail__left")
    private WebElement sideBar;

    public SearchResultsEbayPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfAllElements(productElements));
    }

    public List<Product> getProducts() {
        return productElements.stream()
                .map(product -> new Product(product, driver))
                .collect(Collectors.toList());
    }

    public ProductEbayPage clickOnRandomProduct() {
        try {
            wait.until(webDriver -> !productElements.isEmpty() && productElements.get(0).isDisplayed());
            int randomIndex = new Random().nextInt(productElements.size());
            productElements.get(randomIndex).findElement(By.cssSelector(".s-item__title")).click();
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

            return new ProductEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error while clicking random product", e);
            return null;
        }
    }

    public SearchResultsSideBar getSideBar() {
        try{
            SearchResultsSideBar searchResultsSideBar =  new SearchResultsSideBar(sideBar, driver);
            logger.info("Search results sidebar found");
            return searchResultsSideBar;
        } catch (Exception e) {
            logger.error("Error while getting search results sidebar", e);
            return null;
        }
    }
}
