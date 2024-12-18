package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartOverlay extends AbstractComponent {

    @FindBy(css = "a[data-testid=\"ux-call-to-action\"]")
    List<WebElement> actionButtons;

    public ShoppingCartOverlay(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public ShoppingCartPage clickOnSeeInBasketButton() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(actionButtons));
            Optional<WebElement> seeInBasket = actionButtons.stream()
                    .filter(webElement -> webElement.getText().equals("See in basket"))
                    .toList().stream().findFirst();
            if (seeInBasket.isPresent()) {
                seeInBasket.get().click();
            } else {
                throw new RuntimeException("See in basket button not found");
            }
            logger.info("See on basket button clicked");
            Thread.sleep(10000); // To solve captcha
            return new ShoppingCartPage(driver);
        } catch (Exception e) {
            logger.error("Couldn't click on basket button", e);
            return null;
        }
    }
}
