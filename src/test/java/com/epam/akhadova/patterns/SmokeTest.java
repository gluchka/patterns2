package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.pages.ElmirHeaderForm;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Log4j
public class SmokeTest extends BaseTest {

    @BeforeTest
    public void setupComponents() {
        elmirHeaderForm = new ElmirHeaderForm(driver);
    }


    @Test
    public void test1() {
        String searchFor = "Motorola";
        driver.get(getMainUrl());
        elmirHeaderForm.search(searchFor);
        Assert.assertTrue(driver.getCurrentUrl().contains(searchFor));
    }


    @Test
    public void test2() {
        String searchFor = "Motorola Moto X Force 32Gb Black (SM4356AE7K7)";
        driver.get(getMainUrl());
        elmirHeaderForm.search(searchFor);
        Assert.assertTrue(driver.getCurrentUrl().contains(searchFor));
    }


}
