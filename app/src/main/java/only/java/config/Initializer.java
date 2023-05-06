package only.java.config;

import only.java.App;
import only.java.utils.InjectionUtils;
import only.java.utils.ReflectionUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Initializer {

    public static void start(String[] args) {
        InjectionUtils.setBasePath(getProjectPath());

        System.out.println("Init");
        List<Object> objects = instantiateObjects();
        ObjectsManagement objectsManagement = (ObjectsManagement) objects.stream()
                .filter(obj -> obj.getClass().getName().contains("ObjectsManagement")).findFirst()
                .orElseThrow(() -> new RuntimeException("Can't instantiate ObjectsManagement."));
        objectsManagement.addObjects(objects);

        System.out.println("Application is running");
    }

    public static List<Object> instantiateObjects() {
        List<Class<?>> classes = ReflectionUtils.getInstantiableClasses(getProjectPath());
        return classes.stream().map(ReflectionUtils::getInstance).toList();
    }

    protected static String getProjectPath() {
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("")).getFile());
        return file.getAbsolutePath();
    }

    protected static String getMainPackageName() {
          return App.class.getPackage().getName();
    }

}
