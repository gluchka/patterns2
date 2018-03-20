package com.epam.akhadova.patterns.pages;

import com.epam.akhadova.patterns.MyDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ElmirMainPage {

    public ElmirMainPage(WebDriver driver) {
        PageFactory.initElements(new MyDecorator(driver), this);
    }

}
