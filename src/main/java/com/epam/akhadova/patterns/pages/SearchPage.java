package com.epam.akhadova.patterns.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends ElmirMainPage {
    public SearchPage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "//div[@id='tovars']//a[@class='item-name']")
    public List<WebDriver> listOfProductNames;

    public List<String> getListOfProductNames() {
        List<String> list = new ArrayList<>();
        listOfProductNames.stream()
                .map(WebDriver::getTitle)
                .collect(Collectors.toList()).forEach(System.out::println);
        return list;
    }
}
