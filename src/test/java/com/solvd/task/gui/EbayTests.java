package com.solvd.task.gui;

import com.solvd.task.gui.components.*;
import com.solvd.task.gui.pages.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class EbayTests extends SeleniumGridTest {

    @Test(enabled = true)
    public void testSearchResults() {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox("football jerseys");
        SearchResultsEbayPage searchResultsPage = header.clickSearchButton();
        List<Product> products = searchResultsPage.getProducts();
        products.forEach(product -> {
            Assert.assertTrue(!product.getTitle().isEmpty() && !product.getPrice().isEmpty());
        });
    }

    @Test(enabled = true)
    public void testShoppingCartAdd() {
        List<String> productTitles = new ArrayList<>();

        ShoppingCartEbayPage shoppingCartPage = addProductToShoppingCart(productTitles, "t-shirts");
        Header shoppingCartHeader = shoppingCartPage.getHeader();

        Assert.assertEquals(shoppingCartHeader.getCartNumber(), 1);
        Assert.assertEquals(shoppingCartPage.getProductTitles().size(), 1);
    }

    public ShoppingCartEbayPage addProductToShoppingCart(List<String> productTitles, String search) {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox(search);
        SearchResultsEbayPage searchResultsPage = header.clickSearchButton();
        ProductEbayPage productPage = searchResultsPage.clickOnRandomProduct();
        boolean isAddToCartButtonPresent = productPage.isAddToCartButtonPresent();
        while (!isAddToCartButtonPresent) {
            getDriver().close();

            Set<String> windowHandles = getDriver().getWindowHandles();
            String mainWindowHandle = windowHandles.iterator().next();
            getDriver().switchTo().window(mainWindowHandle);

            productPage = searchResultsPage.clickOnRandomProduct();
            isAddToCartButtonPresent = productPage.isAddToCartButtonPresent();
        }
        productTitles.add(productPage.getProductName());

        productPage.selectRandomOptions();

        if (productPage.isConfirmDialogDisplayed()) {
            Dialog dialog = productPage.getConfirmDialog();
            dialog.clickConfirmButton();
        }

        return productPage.clickAddToCartButton();
    }

    @Test(enabled = true)
    public void testShoppingCartRemove() {
        List<String> productTitles = new ArrayList<>();

        ShoppingCartEbayPage shoppingCartPage = addProductToShoppingCart(productTitles, "t-shirt");
        Header shoppingCartHeader = shoppingCartPage.getHeader();
        shoppingCartPage.getCartProducts().forEach(CartProduct::clickRemoveButton);

        Assert.assertEquals(shoppingCartHeader.getCartNumber(), 0);
        shoppingCartPage.getProductTitles().forEach(productTitle -> {
            Assert.assertFalse(productTitles.contains(productTitle));
        });
    }

    @Test(enabled = true)
    public void testWrongLoginAttempt() {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        SignInPage signInPage = header.clickSignInButton();
        signInPage.typeUserId("invalidUserId");
        signInPage.clickSignInContinueBtn();
        signInPage.typePassword("invalidPassword");
        signInPage.clickSignInBtn();
        Assert.assertTrue(signInPage.isSignInErrorMsgDisplayed());
    }

    @Test(enabled = true)
    public void testSearchFilteringFunctionality() {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox("guitars");
        SearchResultsEbayPage searchResultsEbayPage = header.clickSearchButton();
        SearchResultsSideBar searchResultsSideBar = searchResultsEbayPage.getSideBar();
        String brandName = searchResultsSideBar.selectRandomBrand();
        AtomicInteger counter = new AtomicInteger();
        searchResultsEbayPage.getProducts().forEach(product -> {
            if (product.getTitle().toLowerCase().contains(brandName.toLowerCase())) {
                counter.getAndIncrement();
            }
        });
        logger.info("Number of products found with brand in title: " + counter.get());
        Assert.assertTrue(counter.get() > 10);
    }

    @Test(enabled = true)
    public void testLanguageSwitchFunctionality() {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        List<String> categoriesBefore = homePage.getCategories().stream().map(WebElement::getText).toList();
        LanguageSwitchModal languageSwitchModal = header.clickLanguageMenuButton();
        languageSwitchModal.clickOnRandomLanguageOption();
        List<String> categoriesAfter = homePage.getCategories().stream().map(WebElement::getText).toList();
        categoriesAfter.forEach(element -> {
            Assert.assertFalse(categoriesBefore.contains(element));
        });
    }

    @Test(enabled = true)
    public void testEverythingElseCategoryShowResults() {
        HomeEbayPage homePage = new HomeEbayPage(getDriver());
        Header header = homePage.getHeader();
        Select select = header.clickAllCategoriesSelect();
        select.clickOption(select.getOptions().size() - 1);
        CategoryEbayPage categoryEbayPage = header.clickSearchButtonByCategory();
        Assert.assertFalse(categoryEbayPage.getItems().isEmpty());
    }

}
