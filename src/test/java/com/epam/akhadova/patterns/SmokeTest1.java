package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.pages.ElmirHeaderForm;
import com.epam.akhadova.patterns.pages.SearchPage;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Log4j
public class SmokeTest1 extends BaseTest {

    @BeforeTest
    public void setupComponents() {
        elmirHeaderForm = new ElmirHeaderForm(getDriver());
        searchPage = new SearchPage(getDriver());
    }


    @Test
    public void test1() {
        String searchFor = "Motorola";
        getDriver().get(getMainUrl());
        elmirHeaderForm.search(searchFor);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(searchFor));
    }

}
