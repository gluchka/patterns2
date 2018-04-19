package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.webelements.MyBaseElement;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class MyDecorator extends DefaultFieldDecorator {
    private ElementLocatorFactory locatorFactory;

    public MyDecorator(WebDriver webDriver) {
        super(new DefaultElementLocatorFactory(webDriver));
        locatorFactory = new DefaultElementLocatorFactory(webDriver);
    }

    @Override
    public Object decorate(ClassLoader classLoader, Field customElementField) {
        if (!WebElement.class.isAssignableFrom(customElementField.getType()) && customElementField.isAnnotationPresent(FindBy.class)) {
            ElementLocator locator = locatorFactory.createLocator(customElementField);

            if (List.class.isAssignableFrom(customElementField.getType())) {
                return proxyForListLocator(classLoader, locator, getParametrizedClazz(customElementField));
//                return proxyForListLocator(classLoader, locator).stream()
//                        .map(webElement -> createCustomElement(webElement, customElementField.getType()))
//                        .collect(Collectors.toList());
            } else {
                return createCustomElement(proxyForLocator(classLoader, locator), customElementField.getType());
            }
        }

        return null;
    }

    private Class getParametrizedClazz(Field field) {
        return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    @SneakyThrows
    private Object createCustomElement(WebElement webElement, Class customElementClass) {
        Field webElementField = Arrays.stream(customElementClass.getSuperclass().getDeclaredFields())
                .filter(decorateField -> decorateField.getType().equals(WebElement.class))
                .findFirst()
                .orElseThrow(Exception::new);

        Object customElement = customElementClass.getConstructor(WebElement.class).newInstance(webElement);
        boolean flag = webElementField.isAccessible();

        webElementField.setAccessible(true);
        webElementField.set(customElement, webElement);
        webElementField.setAccessible(flag);

        return customElement;
    }

    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(
                loader,
                new Class[]{WebElement.class, WrapsElement.class, Locatable.class},
                new LocatingElementHandler(locator)
        );
    }

//    protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
//        LocatingElementListHandler handler = new LocatingElementListHandler(locator);
//        List proxy = (List)Proxy.newProxyInstance(loader, new Class[] {List.class}, handler);
//        return proxy;
//    }


    private <T> List<T> proxyForListLocator(ClassLoader loader, ElementLocator locator, Class<T> clazz) {
        if (clazz.getSuperclass().isAssignableFrom(MyBaseElement.class)) {
            InvocationHandler handler = new CustomElementHandler<>(locator, clazz);
            return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
        }
        return null;
    }
}
