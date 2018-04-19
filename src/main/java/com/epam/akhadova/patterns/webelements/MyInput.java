package com.epam.akhadova.patterns.webelements;

import org.openqa.selenium.WebElement;

public class MyInput extends MyBaseElement {

    public MyInput(WebElement webElement) {
        super(webElement);
    }

    public void sendKeys(String value) {
        getWebElement().clear();
        getWebElement().sendKeys(value);
    }
}
