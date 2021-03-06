package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.pages.ElmirHeaderForm;
import com.epam.akhadova.patterns.utils.PropertyLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected PropertyLoader propertyLoader = new PropertyLoader();
    protected WebDriver driver;
    protected ElmirHeaderForm elmirHeaderForm;
    private @Getter
    String mainUrl = "https://elmir.ua";

    @BeforeTest
    @Parameters
    protected void setup() {
        String browserType = propertyLoader.getProperty("browser");
        switch (browserType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("firefox");
                capabilities.setCapability("webdriver.firefox.driver", propertyLoader.getProperty("firefox.driver"));
                driver = new FirefoxDriver(capabilities);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
    }

    @AfterMethod
    protected void tearDown() {
//        driver.quit();
    }
}
