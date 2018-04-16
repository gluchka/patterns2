package com.epam.akhadova.patterns.pages;

import com.epam.akhadova.patterns.webelements.MyBaseElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
public class SearchPage extends ElmirMainPage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='tovars']//a[@class='item-name']")
    private List<MyBaseElement> listOfProductNames;

    public List<String> getListOfProductNames() {
        log.info("Get list of products name on a search page");
        return listOfProductNames.stream()
                .map(MyBaseElement::getText)
                .collect(Collectors.toList());
    }
}
