package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.pages.ElmirHeaderForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {
    WebDriver driver;

    @BeforeClass
    public void preconditions() {
        WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
    }

    @Test
    public void test() {

        driver.get("https://elmir.ua");

        new ElmirHeaderForm(driver).search("Motorola Moto X Force 32Gb Black (SM4356AE7K7)");

        driver.quit();
    }
}
