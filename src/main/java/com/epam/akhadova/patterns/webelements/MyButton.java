package com.epam.akhadova.patterns.webelements;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebElement;

@Log4j
public class MyButton extends MyBaseElement {

    public MyButton(WebElement webElement) {
        super(webElement);
    }

    public void click() {
        getWebElement().click();
    }
}
