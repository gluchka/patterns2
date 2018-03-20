package com.epam.akhadova.patterns.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static final String PROPERTIES_FILE = "./src/test/resources/testing.properties";
    private static Properties properties;

    private static Properties getInstance() {
        if (null == properties) {
            properties = new Properties();
            try (InputStream is = new FileInputStream(PROPERTIES_FILE)) {
                properties.load(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }

    public String getProperty(String property) {
        properties = getInstance();
        return properties.getProperty(property);
    }

}
