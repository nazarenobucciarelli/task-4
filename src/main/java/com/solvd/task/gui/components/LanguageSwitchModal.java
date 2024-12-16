package com.solvd.task.gui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class LanguageSwitchModal extends AbstractComponent {

    @FindBy(css = "ul li a")
    private List<WebElement> languageOption;

    public LanguageSwitchModal(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public void clickOnRandomLanguageOption() {
        try {
            int randomIndex = new Random().nextInt(languageOption.size());
            languageOption.get(randomIndex).click();
            logger.info("Language option clicked");
        } catch (Exception e) {
            logger.error("Error while clicking random language option", e);
        }

    }
}
