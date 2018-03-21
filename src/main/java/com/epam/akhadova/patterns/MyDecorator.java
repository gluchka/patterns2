package com.epam.akhadova.patterns;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class MyDecorator implements FieldDecorator {
    private DefaultElementLocatorFactory locatorFactory;

    public MyDecorator(WebDriver webDriver) {
        locatorFactory = new DefaultElementLocatorFactory(webDriver);
    }

    @Override
    @SneakyThrows
    public Object decorate(ClassLoader classLoader, Field field) {
        Object customElement;
        if (!WebElement.class.isAssignableFrom(field.getType()) && field.getAnnotation(FindBy.class) == null) {
            return null;
        } else {
            Field webElement = Arrays.stream(field.getType().getSuperclass().getDeclaredFields())
                    .filter(decorateField -> decorateField.getType().equals(WebElement.class))
                    .findFirst()
                    .orElseThrow(Exception::new);
            customElement = field.getType().getConstructor().newInstance();
            ElementLocator locator = locatorFactory.createLocator(field);

            boolean flag = webElement.isAccessible();
            webElement.setAccessible(true);
            if (WebElement.class.isAssignableFrom(field.getType())) {
                webElement.set(customElement, proxyForLocator(classLoader, locator));
            } else if (List.class.isAssignableFrom(field.getType())) {

//                boolean flag = webElement.isAccessible();
//                webElement.setAccessible(true);
                webElement.set(customElement, proxyForListLocator(classLoader, locator));
//                webElement.setAccessible(flag);
            }
            webElement.setAccessible(flag);
        }
        return customElement;
    }


    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(
                loader,
                new Class[]{WebElement.class, WrapsElement.class, Locatable.class},
                new LocatingElementHandler(locator)
        );
    }

    protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementListHandler(locator);
        return (List<WebElement>) Proxy.newProxyInstance(
                loader,
                new Class[]{List.class},
                handler);
    }
}
