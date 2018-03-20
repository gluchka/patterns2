package com.epam.akhadova.patterns.webelements;

public class MyButton extends MyBaseElement {

    public void click(String name) {
        System.out.println("Click on button from" + MyButton.class);
        getWebElement().click();
    }

    public String getText() {
        return getWebElement().getText();
    }
}
