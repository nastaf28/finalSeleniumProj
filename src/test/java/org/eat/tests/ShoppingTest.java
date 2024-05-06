package org.eat.tests;

import org.eat.base.BaseTestProject;
import org.eat.base.WebDriverFactory;
import org.eat.utilities.ExcelUtility;
import org.testng.Assert;
import org.testng.annotations.*;

public class ShoppingTest extends BaseTestProject  {

    @Test(groups = {"smoke"})
    public void addToCartFromHome()  {
        shop.addToCartFromHomeP();
        boolean result=shop.isItemAdded();
        Assert.assertTrue(result);

    }
    @Test(groups = {"regression"})
    public void addToCartFromProductD()  {

        shop.addToCartFromProductDetail();
        boolean result=shop.isItemAdded();
        Assert.assertTrue(result);

    }

    @Test(groups = {"regression"})
    public void returnToShoppingFromCArt() {
        shop.returnToShoppingFromCart();
        boolean result=shop.isUserReturnedToHomePage();
        Assert.assertTrue(result);
    }

    @Test(groups = {"smoke"})
    public void removeItemFromCartPAge()  {

        shop.removeItemFromCartPage();
        boolean result =shop.isItemRemovedFromCart();
        Assert.assertFalse(result);
    }

    @Test(groups = {"smoke"})
    public void removeItemFromHomePage()  {
        shop.returnToShoppingFromCart();
        shop.removeItemFromHomePage();
        boolean result =shop.isItemRemovedFromHomePage();
        Assert.assertTrue(result);
    }

    @Test(groups = {"regression"})
    public void multipleItemToAdd()  {
        shop.addingMultipleItems();
        boolean result=shop.isMultipleItemAdded();
        Assert.assertTrue(result);
        shop.checkout();
        boolean result1 = shop.isUserCheckedOut();
        Assert.assertTrue(result1);
    }

    @AfterClass(groups = {"smoke","regression"})
    public void cleanUP () {
        WebDriverFactory.getInstance().quitDriver();
    }

}
