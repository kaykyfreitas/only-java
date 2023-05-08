package only.java.lib.config;

import only.java.App;
import only.java.lib.annotations.Component;
import only.java.lib.context.SimpleContext;
import only.java.lib.exceptions.SimpleException;
import only.java.lib.utils.SimpleInjectionUtils;
import only.java.lib.utils.SimpleReflectionUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Initializer {

    public static SimpleContext start(String[] args) {
        try {
            SimpleInjectionUtils.setBasePath(getProjectPath());
            return new SimpleContext(SimpleInjectionUtils.getBasePath());
        } catch (Exception e) {
            throw new SimpleException("Error during application initialization: " + e.getMessage());
        }
    }

    public static List<Object> instantiateObjects() {
        try {
            List<Class<?>> classes = SimpleReflectionUtils.getInstantiableClasses(getProjectPath());
            return classes.stream().map(SimpleReflectionUtils::getInstance).toList();
        } catch (Exception e) {
            throw new SimpleException("Error during application objects instantiation: " + e.getMessage());
        }
    }

    protected static String getProjectPath() {
        try {
            ClassLoader classLoader = App.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("")).getFile());
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new SimpleException("Error on getProjectPath: " + e.getMessage());
        }
    }

    protected static String getMainPackageName() {
          try {
              return App.class.getPackage().getName();
          } catch (Exception e) {
              throw new SimpleException("Error on getMainPackageName: " + e.getMessage());
          }
    }

}
