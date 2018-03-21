package com.epam.akhadova.patterns.webelements;

import lombok.Getter;
import org.openqa.selenium.WebElement;

public abstract class MyBaseElement {
    private @Getter
    WebElement webElement;
}
