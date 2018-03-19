package com.epam.akhadova.patterns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


public class ElmirHeaderForm extends ElmirMainPage {
    public ElmirHeaderForm(WebDriver driver) {
        super(driver);
    }

    /*@FindBy(xpath = "//input[@type='search']")
    private MyWebElement searchInput;*/

    @FindBy(className = "submit")
    private MyButton searchButton;

    public void search(String request) {
        /*searchInput.sendKeys(request);*/
        searchButton.click("Search button");
    }
}
