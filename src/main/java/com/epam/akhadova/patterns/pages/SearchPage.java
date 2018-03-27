package com.epam.akhadova.patterns.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
public class SearchPage extends ElmirMainPage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='tovars']//a[@class='item-name']")
    public List<WebElement> listOfProductNames;

    public List<String> getListOfProductNames() {
        log.info("Get list of products name on a search page");
        return listOfProductNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
