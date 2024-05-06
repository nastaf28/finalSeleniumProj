package org.eat.pages;

import org.eat.base.CustomDriver;
import org.eat.utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends CustomDriver {

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebDriver driver;


    private String EMAIL_FIELD = "id=>user-name";
    private String PASSWORD_FIELD = "id=>password";
    private String LOGIN_BUTTON = "id=>login-button";
    private String INVALID_CREDENTIALS_ERROR_MESSAGE = "xpath=>//h3[text()]";



    public void SignInWith(String username, String password) {
        sendData(EMAIL_FIELD, username, "Email Field");
        sendData(PASSWORD_FIELD, password, "Password Field");
        clickElement(LOGIN_BUTTON, "Login Button");
    }



    public boolean verifyInvalidCredentialsErrorMessage() {
        boolean elementPresent = isElementPresent(INVALID_CREDENTIALS_ERROR_MESSAGE,
                "Invalid Credentials Error Message");
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        String actualErrorMessage = findText(INVALID_CREDENTIALS_ERROR_MESSAGE, "Invalid Credentials Error Message");
        boolean errorVerification = Util.verifyTextContains(actualErrorMessage, expectedErrorMessage);
        return (elementPresent && errorVerification);
    }




}

