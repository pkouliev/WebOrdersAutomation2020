package com.weborder.pages;

import com.weborder.utilities.ConfigurationReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractBasePage {


    @FindBy(name = "ctl00$MainContent$username")
    private WebElement userName;

    @FindBy(name = "ctl00$MainContent$password")
    private WebElement password;

    public void login() {
        String usernameValue = ConfigurationReader.getProperty("username");
        String passwordValue = ConfigurationReader.getProperty("password");
        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);
    }

    public void login(String usernameValue, String passwordValue) {
        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);
    }


}
