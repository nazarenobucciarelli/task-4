package com.solvd.task.gui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Select extends AbstractComponent {

    @FindBy(css = "option")
    private List<WebElement> options;

    public Select(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public List<WebElement> getOptions() {
        try {
            return options;
        } catch (Exception e) {
            logger.error("Error while getting options", e);
            return null;
        }
    }

    public void clickOption(Integer index) {
        try {
            options.get(index).click();
            logger.info("Selected option: " + options.get(index).getText());
        } catch (Exception e) {
            logger.error("Error while clicking random option: " + e);
        }
    }
}
