package only.java.lib.config;

import only.java.App;
import only.java.lib.exceptions.ApplicationException;
import only.java.lib.utils.InjectionUtils;
import only.java.lib.utils.ReflectionUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Initializer {

    public static void start(String[] args) {
        try {
            InjectionUtils.setBasePath(getProjectPath());

            List<Object> objects = instantiateObjects();
            ObjectsManagement objectsManagement = (ObjectsManagement) objects.stream()
                    .filter(obj -> obj.getClass().getName().contains("ObjectsManagement")).findFirst()
                    .orElseThrow(() -> new RuntimeException("Can't instantiate ObjectsManagement."));
            objectsManagement.addObjects(objects);

        } catch (Exception e) {
            throw new ApplicationException("Error during application initialization: " + e.getMessage());
        }
    }

    public static List<Object> instantiateObjects() {
        try {
            List<Class<?>> classes = ReflectionUtils.getInstantiableClasses(getProjectPath());
            return classes.stream().map(ReflectionUtils::getInstance).toList();
        } catch (Exception e) {
            throw new ApplicationException("Error during application objects instantiation: " + e.getMessage());
        }
    }

    protected static String getProjectPath() {
        try {
            ClassLoader classLoader = App.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("")).getFile());
            return file.getAbsolutePath();
        } catch (Exception e) {
            throw new ApplicationException("Error on getProjectPath: " + e.getMessage());
        }
    }

    protected static String getMainPackageName() {
          try {
              return App.class.getPackage().getName();
          } catch (Exception e) {
              throw new ApplicationException("Error on getMainPackageName: " + e.getMessage());
          }
    }

}
