package com.epam.akhadova.patterns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ElmirMainPage {

    public ElmirMainPage(WebDriver driver) {
        PageFactory.initElements(new MyDecorator(driver), this);
    }

}
