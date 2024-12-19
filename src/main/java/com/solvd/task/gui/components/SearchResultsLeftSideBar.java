package com.solvd.task.gui.components;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class SearchResultsLeftSideBar extends AbstractComponent {

    @FindBy(css = "ul.x-refine__left__nav li ul.x-refine__left__nav > li")
    private List<WebElement> filterGroups;

    public SearchResultsLeftSideBar(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String selectRandomBrand() {
        try {
            List<WebElement> checkBoxes = filterGroups.get(0).findElements(By.cssSelector("a"));
            int randomIndex = new Random().nextInt(checkBoxes.size());
            WebElement checkBox = checkBoxes.get(randomIndex);

            while (true) {
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(checkBox)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
                String value = checkBox.getDomProperty("href");

                WebElement inputElement = null;
                String brandName = null;
                try {
                    inputElement = checkBox.findElement(By.cssSelector("input[aria-label]"));
                    if (inputElement != null) {
                        brandName = inputElement.getDomAttribute("aria-label");
                    }
                } catch (NoSuchElementException ex) {
                    logger.error("Input element with aria-label not found", ex);
                    continue;
                }

                if (brandName == null) {
                    logger.error("Brand name is null");
                    continue;
                }

                if (!value.contains("Unbranded")) {
                    checkBox.click();
                    logger.info("Selected brand: {}", brandName);
                    return brandName;
                }

                checkBoxes = filterGroups.get(0).findElements(By.cssSelector("a"));
                randomIndex = new Random().nextInt(checkBoxes.size());
                checkBox = checkBoxes.get(randomIndex);
            }
        } catch (Exception e) {
            logger.error("Error while selecting brand", e);
            return null;
        }
    }

}

