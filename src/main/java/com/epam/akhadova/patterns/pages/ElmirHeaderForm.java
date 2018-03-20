package com.epam.akhadova.patterns.pages;

import com.epam.akhadova.patterns.webelements.MyButton;
import com.epam.akhadova.patterns.webelements.MyInput;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Log4j
public class ElmirHeaderForm extends ElmirMainPage {
    public ElmirHeaderForm(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@type='search']")
    private MyInput searchInput;

    @FindBy(className = "submit")
    private MyButton searchButton;

    public void search(String searchWord) {
        log.info("Search by: " + searchWord);
        searchInput.sendKeys(searchWord);
        searchButton.click();
    }
}
