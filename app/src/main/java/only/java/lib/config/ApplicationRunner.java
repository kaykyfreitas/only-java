package only.java.lib.config;

import only.java.lib.annotations.Component;
import only.java.lib.context.SimpleContext;
import only.java.lib.exceptions.SimpleException;
import only.java.lib.utils.SimpleReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationRunner {

    private ApplicationRunner() {}

    public static SimpleContext run(Class<?> initializer, String... args) {
        try {
            Object app = initializer.getDeclaredConstructor().newInstance();

//            String mainPackage = ApplicationRunner.class.getModule().getPackages().stream().findFirst()
//                    .orElseThrow(() -> new SimpleException("Can't find package name of main Class."));

            Method startMethod = initializer.getMethod("start", String[].class);
            return (SimpleContext) startMethod.invoke(app, new Object[] { args });
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new SimpleException("Error during application execution: " + e.getMessage());
        }
    }

}
