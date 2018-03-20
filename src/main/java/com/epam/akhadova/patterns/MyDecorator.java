package com.epam.akhadova.patterns;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class MyDecorator implements FieldDecorator {
    private DefaultElementLocatorFactory locatorFactory;

    public MyDecorator(WebDriver webDriver) {
        locatorFactory = new DefaultElementLocatorFactory(webDriver);
    }

    @Override
    @SneakyThrows
    public Object decorate(ClassLoader classLoader, Field field) {
        Field webElement = Arrays.stream(field.getType().getSuperclass().getDeclaredFields())
                .filter(decorateField -> decorateField.getType().equals(WebElement.class))
                .findFirst()
                .orElseThrow(Exception::new);

        Object customElement = field.getType().getConstructor().newInstance();

        boolean flag = webElement.isAccessible();
        webElement.setAccessible(true);
        webElement.set(customElement, proxyForLocator(classLoader, locatorFactory.createLocator(field)));
        webElement.setAccessible(flag);

        return customElement;
    }

    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(
                loader,
                new Class[]{WebElement.class, WrapsElement.class, Locatable.class},
                new LocatingElementHandler(locator)
        );
    }
}
