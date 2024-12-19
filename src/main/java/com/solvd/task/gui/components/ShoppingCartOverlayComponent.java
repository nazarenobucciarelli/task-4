package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

import static com.solvd.task.gui.pages.AbstractPage.getCaptcha;

public class ShoppingCartOverlayComponent extends AbstractComponent {

    @FindBy(css = "a[data-testid=\"ux-call-to-action\"]")
    List<WebElement> actionButtons;

    public ShoppingCartOverlayComponent(WebElement root, WebDriver driver) {
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
            ;
            if (getCaptcha() != null) {
                wait.until(ExpectedConditions.invisibilityOf(getCaptcha())); // to solve captcha
            }
            return new ShoppingCartPage(driver);
        } catch (Exception e) {
            logger.error("Couldn't click on basket button", e);
            return null;
        }
    }
}
