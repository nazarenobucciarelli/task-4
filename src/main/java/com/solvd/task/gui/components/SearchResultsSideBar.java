package com.solvd.task.gui.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SearchResultsSideBar extends AbstractComponent {

    @FindBy(css = "ul.x-refine__left__nav li ul.x-refine__left__nav > li")
    private List<WebElement> filterGroups;

    public SearchResultsSideBar(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public String selectRandomBrand() {
        try{
            List<WebElement> checkBoxes = filterGroups.get(0).findElements(By.cssSelector("a"));
            int randomIndex = new Random().nextInt(checkBoxes.size());
            WebElement checkBox = checkBoxes.get(randomIndex);
            String value = checkBox.getAttribute("href");
            String brandName = checkBox.findElement(By.cssSelector("input[aria-label]")).getAttribute("aria-label");
            while (value.contains("Unbranded")) {
                checkBox = checkBoxes.get(randomIndex);
                brandName = checkBox.findElement(By.cssSelector("input[aria-label]")).getAttribute("aria-label");
                checkBox.click();
                value = checkBox.getAttribute("href");
            }
            checkBox.click();
            logger.info("Selected brand: " + brandName);
            return  brandName;
        } catch (Exception e) {
            logger.error("Error while selecting brand", e);
            return null;
        }
    }
}