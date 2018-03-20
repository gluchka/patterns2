package com.epam.akhadova.patterns.webelements;

import lombok.AccessLevel;
import lombok.Getter;
import org.openqa.selenium.WebElement;

public abstract class MyBaseElement {
    private @Getter(AccessLevel.PROTECTED) WebElement webElement;
}
