package com.solvd.task.gui;

import com.solvd.task.gui.components.*;
import com.solvd.task.gui.enums.Category;
import com.solvd.task.gui.pages.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GUITests extends SeleniumGridTest {

    @Test(enabled = true)
    public void testSearchResults() {
        HomePage homePage = new HomePage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox("football jerseys");
        ProductListPage searchResultsPage = header.clickSearchButton();
        List<Product> products = searchResultsPage.getProducts();
        products.forEach(product -> {
            Assert.assertTrue(!product.getTitle().isEmpty() && !product.getPrice().isEmpty());
        });
    }

    @Test(enabled = true)
    public void testShoppingCartAdd() {
        List<String> productTitles = new ArrayList<>();

        ShoppingCartPage shoppingCartPage = addProductToShoppingCart(productTitles, "t-shirts");
        Header shoppingCartHeader = shoppingCartPage.getHeader();

        Assert.assertEquals(shoppingCartPage.getProductTitles().size(), 1);
    }

    public ShoppingCartPage addProductToShoppingCart(List<String> productTitles, String search) {
        HomePage homePage = new HomePage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox(search);
        ProductListPage searchResultsPage = header.clickSearchButton();
        ProductPage productPage = searchResultsPage.clickOnRandomProduct();
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

        ShoppingCartOverlay overlay = productPage.clickAddToCartButton();
        return overlay.clickOnSeeInBasketButton();
    }

    @Test(enabled = true)
    public void testShoppingCartRemove() {
        List<String> productTitles = new ArrayList<>();

        ShoppingCartPage shoppingCartPage = addProductToShoppingCart(productTitles, "t-shirt");
        Header shoppingCartHeader = shoppingCartPage.getHeader();
        shoppingCartPage.getCartProducts().forEach(CartProduct::clickRemoveButton);

        shoppingCartPage.getProductTitles().forEach(productTitle -> {
            Assert.assertFalse(productTitles.contains(productTitle));
        });
    }

    @Test(enabled = true)
    public void testWrongLoginAttempt() {
        HomePage homePage = new HomePage(getDriver());
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
        HomePage homePage = new HomePage(getDriver());
        Header header = homePage.getHeader();
        header.typeSearchBox("guitars");
        ProductListPage productListPage = header.clickSearchButton();
        SearchResultsSideBar searchResultsSideBar = productListPage.getSideBar();
        String brandName = searchResultsSideBar.selectRandomBrand();
        AtomicInteger counter = new AtomicInteger();
        productListPage.getProducts().forEach(product -> {
            if (product.getTitle().toLowerCase().contains(brandName.toLowerCase())) {
                counter.getAndIncrement();
            }
        });
        logger.info("Number of products found with brand in title: " + counter.get());
        Assert.assertTrue(counter.get() > 10);
    }

    @Test(enabled = true)
    public void testAreHeaderElementsDisplayed() {
        HomePage homePage = new HomePage(getDriver());
        Header header = homePage.getHeader();
        Assert.assertTrue(header.areAllHeaderElementsDisplayed());
    }

    @Test(enabled = true, dataProvider = "categories", dataProviderClass = DataProviders.class)
    public void testCategoryShowResults(Category category) {
        HomePage homePage = new HomePage(getDriver());
        Header header = homePage.getHeader();
        Select select = header.openAllCategories();
        select.clickOption(category);
        CategoryPage categoryPage = header.clickSearchButtonByCategory();
        Assert.assertFalse(categoryPage.getItems().isEmpty());
    }

}
