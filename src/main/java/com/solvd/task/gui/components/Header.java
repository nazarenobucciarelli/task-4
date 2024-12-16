package com.solvd.task.gui.components;

import com.solvd.task.gui.pages.CategoryEbayPage;
import com.solvd.task.gui.pages.SearchResultsEbayPage;
import com.solvd.task.gui.pages.SignInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Header extends AbstractComponent {

    @FindBy(css = "#gh-ac")
    private WebElement searchBox;

    @FindBy(css = ".gh-spr")
    private WebElement searchButton;

    @FindBy(css = "#gh-shop-a")
    private WebElement shopByCategoryButton;

    @FindBy(id = "gh-cart-n")
    private WebElement cartNumber;

    @FindBy(css = "#gh-sbc-o")
    private WebElement modal;

    @FindBy(xpath = "//span[@id='gh-ug']/a")
    private WebElement signInButton;

    @FindBy(id = "gh-eb-Geo-a-default")
    private WebElement languageButton;

    @FindBy(id = "gh-eb-Geo-o")
    private WebElement languageSwitchModal;

    @FindBy(id = "gh-cat")
    private WebElement allCategoriesSelect;

    public Header(WebElement root, WebDriver driver) {
        super(root, driver);
    }

    public void typeSearchBox(String text) {
        try {
            searchBox.sendKeys(text);
            logger.info("Search box entered: " + text);
        } catch (Exception e) {
            logger.error("Error while typing search box: " + e);
        }
    }

    public SearchResultsEbayPage clickSearchButton() {
        try {
            searchButton.click();
            logger.info("Search button clicked");
            return new SearchResultsEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click search button", e);
            return null;
        }
    }

    public CategoryEbayPage clickSearchButtonByCategory() {
        try {
            searchButton.click();
            logger.info("Search button by category clicked");
            return new CategoryEbayPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click search button by category", e);
            return null;
        }
    }

    public ShopByCategoryModal clickShopByCategoryButton() {
        try {
            shopByCategoryButton.click();
            logger.info("Shop button clicked");
            wait.until(ExpectedConditions.visibilityOf(modal));
            return new ShopByCategoryModal(modal, driver);
        } catch (Exception e) {
            logger.error("Error trying to click shop by category button", e);
            return null;
        }
    }

    public int getCartNumber() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartNumber));
            int cartNum = Integer.parseInt(cartNumber.getText());
            logger.info("Cart number is: " + cartNum);
            return cartNum;
        } catch (Exception e) {
            logger.info("Cart number is: 0");
            return 0;
        }
    }

    public SignInPage clickSignInButton() {
        try {
            signInButton.click();
            logger.info("Sign in button clicked");
            Thread.sleep(20000); // To solve captcha
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userid")));
            return new SignInPage(driver);
        } catch (Exception e) {
            logger.error("Error trying to click sign in button", e);
            return null;
        }
    }

    public LanguageSwitchModal clickLanguageMenuButton() {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(languageButton));
            languageButton.click();
            logger.info("Language menu button clicked");
            return new LanguageSwitchModal(languageSwitchModal, driver);
        } catch (Exception e) {
            logger.error("Error trying to click language menu button", e);
            return null;
        }
    }

    public Select clickAllCategoriesSelect() {
        try{
            allCategoriesSelect.click();
            logger.info("All categories button clicked");
            return new Select(allCategoriesSelect, driver);
        } catch (Exception e) {
            logger.error("Error trying to click all categories button", e);
            return null;
        }
    }

}
