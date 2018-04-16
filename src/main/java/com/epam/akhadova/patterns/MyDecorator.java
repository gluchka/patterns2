package com.epam.akhadova.patterns;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyDecorator implements FieldDecorator {
    private ElementLocatorFactory locatorFactory;

    public MyDecorator(WebDriver webDriver) {
        locatorFactory = new DefaultElementLocatorFactory(webDriver);
    }

    @Override
    public Object decorate(ClassLoader classLoader, Field customElementField) {
        if (!WebElement.class.isAssignableFrom(customElementField.getType()) && customElementField.isAnnotationPresent(FindBy.class)) {
            ElementLocator locator = locatorFactory.createLocator(customElementField);

            if (List.class.isAssignableFrom(customElementField.getType())) {
                return proxyForListLocator(classLoader, locator).stream()
                        .map(webElement -> createCustomElement(webElement, customElementField.getType()))
                        .collect(Collectors.toList());
            } else {
                return createCustomElement(proxyForLocator(classLoader, locator), customElementField.getType());
            }
        }

        return null;
    }

    @SneakyThrows
    private Object createCustomElement(WebElement webElement, Class customElementClass) {
        Field webElementField = Arrays.stream(customElementClass.getSuperclass().getDeclaredFields())
                .filter(decorateField -> decorateField.getType().equals(WebElement.class))
                .findFirst()
                .orElseThrow(Exception::new);

        Object customElement = customElementClass.getConstructor().newInstance();
        boolean flag = webElementField.isAccessible();

        webElementField.setAccessible(true);
        webElementField.set(customElement, webElement);
        webElementField.setAccessible(flag);

        return customElement;
    }

    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(
                loader,
                new Class[] {WebElement.class, WrapsElement.class, Locatable.class},
                new LocatingElementHandler(locator)
        );
    }

    private List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        LocatingElementListHandler handler = new LocatingElementListHandler(locator);
        List proxy = (List)Proxy.newProxyInstance(loader, new Class[] {List.class}, handler);
        return proxy;
    }
}
