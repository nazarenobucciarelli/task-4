package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.CategoryPage;
import com.solvd.task.gui.pages.ProductListPage;
import com.solvd.task.gui.pages.SignInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static com.solvd.task.gui.pages.AbstractPage.getCaptcha;

public class HeaderComponent extends AbstractComponent {

    @FindBy(css = "#gh-ac")
    private WebElement searchBox;

    @FindBy(css = ".gh-spr")
    private WebElement searchButton;

    @FindBy(css = "#gh-shop-a")
    private WebElement shopByCategoryButton;

    @FindBy(id = "gh-minicart-hover")
    private WebElement cartButton;

    @FindBy(css = "#gh-sbc-o")
    private WebElement modal;

    @FindBy(xpath = "//span[@id='gh-ug']/a")
    private WebElement signInButton;

    @FindBy(id = "gh-cat")
    private WebElement categoriesSelect;

    @FindBy(id = "gh-as-a")
    private WebElement advancedButton;

    @FindBy(id = "gh-logo")
    private WebElement logo;

    @FindBy(xpath = "//span[@id='gh-ug']//a[contains(text(), 'register')]")
    private WebElement registerButton;

    @FindBy(id = "gh-eb-Alerts")
    private WebElement notificationButton;

    public HeaderComponent(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public void typeSearchBox(String text) {
        try {
            searchBox.sendKeys(text);
            logger.info("Search box entered: {}", text);
        } catch (Exception e) {
            logger.error("Error while typing search box: {}", String.valueOf(e));
        }
    }

    public ProductListPage clickSearchButton() {
        try {
            searchButton.click();
            logger.info("Search button clicked");
            return new ProductListPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click search button", e);
            return null;
        }
    }

    public CategoryPage clickSearchButtonByCategory() {
        try {
            searchButton.click();
            logger.info("Search button by category clicked");
            return new CategoryPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click search button by category", e);
            return null;
        }
    }

    public ShopByCategoryModalComponent clickShopByCategoryButton() {
        try {
            shopByCategoryButton.click();
            logger.info("Shop button clicked");
            wait.until(ExpectedConditions.visibilityOf(modal));
            return new ShopByCategoryModalComponent(modal, driver);
        } catch (Exception e) {
            logger.error("Error trying to click shop by category button", e);
            return null;
        }
    }

    public SignInPage clickSignInButton() {
        try {
            signInButton.click();
            logger.info("Sign in button clicked");
            if (getCaptcha() != null) {
                wait.until(ExpectedConditions.invisibilityOf(getCaptcha())); // to solve captcha
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userid")));
            return new SignInPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click sign in button", e);
            return null;
        }
    }

    public SelectComponent openAllCategories() {
        try {
            categoriesSelect.click();
            logger.info("All categories opened");
            return new SelectComponent(categoriesSelect, driver);
        } catch (Exception e) {
            logger.error("Error trying to open all categories", e);
            return null;
        }
    }

    public boolean areAllHeaderElementsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(
                    List.of(signInButton, shopByCategoryButton, categoriesSelect,
                            advancedButton, logo, registerButton, notificationButton, searchButton, searchBox, cartButton))
            );
            logger.info("All header elements displayed");
            return true;
        } catch (Exception e) {
            logger.error("Error trying to display all header elements", e);
            return false;
        }
    }

}
