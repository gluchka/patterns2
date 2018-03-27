package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.pages.ElmirHeaderForm;
import com.epam.akhadova.patterns.pages.SearchPage;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Log4j
public class SmokeTest extends BaseTest {

    @BeforeTest
    public void setupComponents() {
        elmirHeaderForm = new ElmirHeaderForm(getDriver());
        searchPage = new SearchPage(getDriver());
    }


    @Test
    public void test1() {
        String searchFor = "Samsung";
        getDriver().get(getMainUrl());
        elmirHeaderForm.search(searchFor);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(searchFor));
    }


    @Test
    public void test2() {
        String searchFor = "Motorola";
        getDriver().get(getMainUrl());
        elmirHeaderForm.search(searchFor);
        Assert.assertTrue(searchPage.getListOfProductNames().stream().anyMatch(name -> name.contains(searchFor)));
    }


}
