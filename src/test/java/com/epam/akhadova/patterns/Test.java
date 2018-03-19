package com.epam.akhadova.patterns;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Viktoriia_Akhadova on 3/19/2018.
 */
public class Test {
    @org.testng.annotations.Test
    public void test() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://elmir.ua");

        new ElmirHeaderForm(driver).search("Motorola Moto X Force 32Gb Black (SM4356AE7K7)");

        driver.quit();
    }
}
