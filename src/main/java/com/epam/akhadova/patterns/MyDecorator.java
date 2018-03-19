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

public class MyDecorator implements FieldDecorator {
    private DefaultElementLocatorFactory locatorFactory;

    public MyDecorator(WebDriver webDriver) {
        locatorFactory = new DefaultElementLocatorFactory(webDriver);
    }

    @Override
    @SneakyThrows
    public Object decorate(ClassLoader classLoader, Field field) {
        Field webElement = field.getType().getSuperclass().getDeclaredField("webElement");
        Object customElement = field.getType().getConstructor().newInstance();

        webElement.setAccessible(true);
        webElement.set(customElement, proxyForLocator(classLoader, locatorFactory.createLocator(field)));
        webElement.setAccessible(false);

        return customElement;
    }

    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(
                loader,
                new Class[] {WebElement.class, WrapsElement.class, Locatable.class},
                new LocatingElementHandler(locator)
        );
    }
}
