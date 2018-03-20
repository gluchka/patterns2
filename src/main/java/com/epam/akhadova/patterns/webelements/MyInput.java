package com.epam.akhadova.patterns.webelements;

public class MyInput extends MyBaseElement {

    public void sendKeys(String value) {
        getWebElement().clear();
        getWebElement().sendKeys(value);
    }
}
