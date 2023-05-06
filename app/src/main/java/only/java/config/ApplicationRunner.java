package only.java.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationRunner {


    public static void run(Class<?> appClass, String... args) {
        try {
            Object app = appClass.getDeclaredConstructor().newInstance();

            Method startMethod = appClass.getMethod("start", String[].class);
            startMethod.invoke(app, new Object[] { args });
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            System.out.println("Can't start application: " + e.getMessage());
        }
    }

}
