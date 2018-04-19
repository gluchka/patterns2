package com.epam.akhadova.patterns;

import com.epam.akhadova.patterns.webelements.MyBaseElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viktoriia_Akhadova on 4/17/2018.
 */
public class CustomElementHandler<T> implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<T> clazz;

    public CustomElementHandler(ElementLocator locator, Class<T> clazz) {
        this.locator = locator;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        List<WebElement> elements = locator.findElements();
        List<T> customWebElements = new ArrayList<>();
        for (WebElement element : elements) {
            //createInstance of element
            customWebElements.add(createInstance(clazz, element));
        }
        try {
            return method.invoke(customWebElements, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static <T> T createInstance(Class<T> clazz, WebElement element) {
        try {
            return clazz.getConstructor(WebElement.class).newInstance(element);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
