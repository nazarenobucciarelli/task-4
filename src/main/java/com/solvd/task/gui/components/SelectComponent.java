package com.solvd.task.gui.components;

import com.solvd.task.gui.enums.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class SelectComponent extends AbstractComponent {

    @FindBy(css = "option")
    private List<WebElement> options;

    public SelectComponent(WebElement root, WebDriver driver) {
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

    public void clickOption(Category category) {
        try {
            Optional<WebElement> opt = options.stream()
                    .filter(option -> option.getText().equals(category.getDisplayName()))
                    .findFirst();
            if (opt.isPresent()) {
                opt.get().click();
                logger.info("Selected option: " + category.getDisplayName());
            } else {
                throw new RuntimeException("Option not found");
            }
        } catch (Exception e) {
            logger.error("Error while clicking random option: " + e);
        }
    }
}
