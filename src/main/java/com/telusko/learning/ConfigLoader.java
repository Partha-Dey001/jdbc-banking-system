package com.telusko.learning;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try {
            InputStream inStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties");
            if (inStream == null) {
                throw new IOException("config.properties not found in classpath");
            }
            props.load(inStream);
        } catch (IOException e) {
            System.out.println("Error loading config.properties");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
