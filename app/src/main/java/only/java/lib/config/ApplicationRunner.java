package only.java.lib.config;

import only.java.lib.exceptions.ApplicationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationRunner {

    private ApplicationRunner() {}

    public static void run(Class<?> appClass, String... args) {
        try {
            Object app = appClass.getDeclaredConstructor().newInstance();

            Method startMethod = appClass.getMethod("start", String[].class);
            startMethod.invoke(app, new Object[] { args });
        } catch (Exception e) {
            throw new ApplicationException("Error during application execution: " + e.getMessage());
        }
    }

}
