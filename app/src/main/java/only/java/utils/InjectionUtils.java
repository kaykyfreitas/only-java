package only.java.utils;

import only.java.repository.AppRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class InjectionUtils {

    private static Properties properties;

    private static Object object;

    static {
        try {
            properties = new Properties();
            File propertiesFile = new File("config.properties");
            FileReader fileReader = new FileReader(propertiesFile);
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static Object getInstance(String className) {
        Class clazz = null;

        AppRepository appRepository = null;

        try {
            clazz = Class.forName(className);
            appRepository = (AppRepository) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return appRepository;
    }

    public static Object getAppRepository() {
        String className = properties.getProperty("AppRepository");
        object = getInstance(className);
        return object;
    }

    private static Properties loadProperties() {
        try {
            File propertiesFile = new File("config.properties");
            FileReader fileReader = new FileReader(propertiesFile);
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
