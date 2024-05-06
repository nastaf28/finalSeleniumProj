package org.eat.pages;

import org.eat.base.CustomDriver;
import org.openqa.selenium.WebDriver;

public class ShoppingPage extends CustomDriver {

    public ShoppingPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    private String ADD_TO_CART_FromHomePage = "xpath=>//div[@class='inventory_list']/descendant::button[1]";

    private String ADD_TO_CART_FROM_PRODUCT_DETAIL = "xpath=>//button[text()='Add to cart']";
    private String CLICK_ON_PRODUCT = "xpath=>//div[@class='inventory_list']/descendant::button[2]";
    private String CART_ICON = "id=>shopping_cart_container";
    private String RETURN_TO_SHOPPING = "id=>continue-shopping";
    private String REMOVE_FROM_CART_PAGE = "xpath=>//div[@class='cart_list']/descendant::button[1]";

    private String REMOVE_FROM_HOME_PAGE = "xpath=>//div[@class='inventory_list']/descendant::button[2]";
    private String ADD_PRODUCT_1 = "xpath=>//div[@class='inventory_list']/descendant::button[3]";
    private String ADD_PRODUCT_2 = "xpath=>//div[@class='inventory_list']/descendant::button[4]";
    private String CHECKOUT = "id=>checkout";
    private String REMOVE_BUTTON="xpath=>//button[text()='Remove']";
    private String TITLE="xpath=>//span[@class='title']";
    private String CHECKOUT_TITLE="xpath=>//span[text()='Checkout: Your Information']";
    private String SPECIFIC_ITEM="id=>item_4_title_link";




    public void addToCartFromHomeP() {
        clickWhenReady(ADD_TO_CART_FromHomePage,"item added from home page",8);


    }
    public boolean isItemAdded() {
        boolean result = isElementPresent(REMOVE_BUTTON, "remove button present as indication of item added ");
        return result;
    }

    public void addToCartFromProductDetail() {

        clickWhenReady(CLICK_ON_PRODUCT,"item clicked for the next step",8);
       clickWhenReady(ADD_TO_CART_FROM_PRODUCT_DETAIL,"item added to cart from product detail",15);
    }
    public void returnToShoppingFromCart(){

       clickWhenReady(CART_ICON,"cart icon clicked",10);
       clickWhenReady(RETURN_TO_SHOPPING,"returned to shopping page from cart",8);
    }
    public boolean isUserReturnedToHomePage() {
        boolean result = isElementPresent(TITLE, "title present as indication of user returned to home page ");
        return result;
    }

    public void removeItemFromCartPage(){
       
        clickWhenReady(CART_ICON,"cart icon clicked to remove item",8);
        clickWhenReady(REMOVE_FROM_CART_PAGE,"remove product from cart page  ",10);
    }
    public void removeItemFromHomePage(){

        clickWhenReady(REMOVE_FROM_HOME_PAGE,"remove item from home page  ",12);
    }
    public void addingMultipleItems(){


        clickWhenReady(ADD_PRODUCT_1,"adding product 1 for checkout",11);
        clickWhenReady(ADD_PRODUCT_2,"adding product 2 for checkout",9);


    }

    public void checkout(){
        clickWhenReady(CART_ICON,"clicked on cart icon for checkout items",8);
        clickWhenReady(CHECKOUT,"click on checkout button for checkout items ",8);

    }

    public boolean isMultipleItemAdded() {
        boolean result = isElementPresent(ADD_PRODUCT_1, "product 1  present as indication of of multiple item added ");
        boolean result1 = isElementPresent(ADD_PRODUCT_2, "product 2  present as indication of of multiple item added ");
        return result&result1;
    }
    public boolean isUserCheckedOut() {
        boolean result = isElementPresent(CHECKOUT_TITLE ,"checkout title present as indication of user checked out  ");

        return result;
    }
    public boolean isItemRemovedFromHomePage() {
        boolean result = isElementPresent(ADD_TO_CART_FromHomePage,"add to cart button present as indication of item has been removed   ");

        return result;
    }
    public boolean isItemRemovedFromCart() {
        boolean result = isElementPresent(SPECIFIC_ITEM,"specific item  should not be present after removing it  ");

        return result;
    }





















}
