package com.epam.akhadova.patterns.webelements;

import lombok.Getter;
import org.openqa.selenium.WebElement;

public abstract class MyBaseElement {
    private @Getter
    WebElement webElement;

    public MyBaseElement() {
    }

    public MyBaseElement(WebElement webElement) {
        this.webElement = webElement;
    }


    public String getText() {
        return this.getWebElement().getText();
    }

}
