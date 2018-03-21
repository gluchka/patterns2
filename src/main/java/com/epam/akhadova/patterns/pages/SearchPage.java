package com.epam.akhadova.patterns.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends ElmirMainPage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='tovars']//a[@class='item-name']")
    public List<WebElement> listOfProductNames;

    public List<String> getListOfProductNames() {
        return listOfProductNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
