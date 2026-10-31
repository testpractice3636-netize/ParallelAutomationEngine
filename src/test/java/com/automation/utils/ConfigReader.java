package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("CRITICAL CONFIG ERROR: Failed to load config.properties! " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("CRITICAL CONFIG ERROR: Key [" + key + "] not found in config.properties!");
        }
        return value.trim();
    }
}
