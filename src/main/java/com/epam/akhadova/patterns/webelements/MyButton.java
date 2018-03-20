package com.epam.akhadova.patterns.webelements;

import lombok.extern.log4j.Log4j;

@Log4j
public class MyButton extends MyBaseElement {

    public void click() {
        getWebElement().click();
    }

    public String getText() {
        return getWebElement().getText();
    }
}
