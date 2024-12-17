package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.HomeEbayPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LanguageSwitchModal extends AbstractComponent {

    @FindBy(css = "ul li")
    private List<WebElement> languageOption;

    public LanguageSwitchModal(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public HomeEbayPage clickOnRandomLanguageOption() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(languageOption));
            WebElement firstOption = languageOption.get(0);
            String lang =  firstOption.getDomAttribute("lang");
            firstOption.click();
            logger.info("Language option clicked: " + lang);
            return new HomeEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error while clicking random language option", e);
            return null;
        }
    }
}
