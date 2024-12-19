package com.solvd.task.gui;

import com.solvd.task.gui.components.*;
import com.solvd.task.gui.enums.Category;
import com.solvd.task.gui.models.Product;
import com.solvd.task.gui.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GUITests extends BaseTest {

    @Test(enabled = true)
    public void testSearchResults() {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        headerComponent.typeSearchBox("football jerseys");
        ProductListPage searchResultsPage = headerComponent.clickSearchButton();
        List<Product> products = searchResultsPage.getProducts();
        products.forEach(product -> {
            Assert.assertTrue(!product.getTitle().isEmpty() &&
                    !product.getPrice().isEmpty(), "Not all products have a title and price");
        });
    }

    @Test(enabled = true)
    public void testShoppingCartAdd() {
        ShoppingCartPage shoppingCartPage = addProductToShoppingCart("t-shirts");
        Assert.assertEquals(shoppingCartPage.getCartProducts().size(), 1, "There should be one product");
    }

    @Test(enabled = true)
    public void testShoppingCartRemove() {
        ShoppingCartPage shoppingCartPage = addProductToShoppingCart("t-shirt");
        shoppingCartPage.getCartProducts().forEach(CartProductComponent::clickRemoveButton);
        Assert.assertEquals(shoppingCartPage.getCartProducts().size(), 0, "There should be 0 products");
    }

    @Test(enabled = true)
    public void testWrongLoginAttempt() {
        SignInPage signInPage = login("invalidUserId", "invalidPassword");
        Assert.assertTrue(signInPage.isSignInErrorMsgDisplayed(), "Sign in error message was not displayed");
    }

    @Test(enabled = true)
    public void testSearchFilteringFunctionality() {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        headerComponent.typeSearchBox("guitars");
        ProductListPage productListPage = headerComponent.clickSearchButton();
        SearchResultsLeftSideBar searchResultsLeftSideBar = productListPage.getLeftSideBar();
        String brandName = searchResultsLeftSideBar.selectRandomBrand();
        AtomicInteger counter = new AtomicInteger();
        productListPage.getProducts().forEach(productListComponent -> {
            if (productListComponent.getTitle().toLowerCase().contains(brandName.toLowerCase())) {
                counter.getAndIncrement();
            }
        });
        logger.info("Number of products found with brand in title: {}", counter.get());
        Assert.assertTrue(counter.get() > 10, "Number of products found with the brand on their title " +
                "was lower than 10");
    }

    @Test(enabled = true)
    public void testAreHeaderElementsDisplayed() {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        Assert.assertTrue(headerComponent.areAllHeaderElementsDisplayed(), "Not all header elements were displayed");
    }

    @Test(enabled = true, dataProvider = "categories", dataProviderClass = DataProviders.class)
    public void testCategoryShowResults(Category category) {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        SelectComponent selectComponent = headerComponent.openAllCategories();
        selectComponent.clickOption(category);
        CategoryPage categoryPage = headerComponent.clickSearchButtonByCategory();
        Assert.assertFalse(categoryPage.getProducts().isEmpty(), "Category " + category.getDisplayName() +
                " didn't show items");
        logger.info("Number of products found in category: {}", categoryPage.getProducts().size());
    }

    // Helper methods

    public ShoppingCartPage addProductToShoppingCart(String search) {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        headerComponent.typeSearchBox(search);
        ProductListPage searchResultsPage = headerComponent.clickSearchButton();
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

        productPage.selectRandomOptions();

        ShoppingCartOverlayComponent overlay = productPage.clickAddToCartButton();

        if (productPage.isConfirmationDialogDisplayed()) {
            DialogComponent dialogComponent = productPage.getConfirmationDialog();
            dialogComponent.clickConfirmButton();
        }

        return overlay.clickOnSeeInBasketButton();
    }

    public SignInPage login(String username, String password) {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent headerComponent = homePage.getHeader();
        SignInPage signInPage = headerComponent.clickSignInButton();
        signInPage.typeUserId(username);
        signInPage.clickSignInContinueBtn();
        signInPage.typePassword(password);
        signInPage.clickSignInBtn();
        return signInPage;
    }
}
