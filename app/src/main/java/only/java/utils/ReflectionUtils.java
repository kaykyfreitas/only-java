package only.java.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReflectionUtils {

    private static final Logger logger = Logger.getLogger(ReflectionUtils.class.getName());

    private static final String SET = "set";

    private static final String GET = "get";

    private ReflectionUtils() {}

    public static void copyProperties(Object source, Object target) {
        try {
            mapObjects(source, target);
        } catch (Exception e) {
            logger.throwing("ReflectionUtils", "copyProperties(Object source, Object target)", e);
        }
    }

    public static <D> D mapper(Object source, Class<D> targetClass) {
        D target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();

            mapObjects(source, target);
            return target;
        } catch (Exception e) {
            logger.throwing("ReflectionUtils", "mapper(Object source, Class<D> targetClass)", e);
            return null;
        }
    }

    private static void mapObjects(Object source, Object target) {
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                Method getter = getGetter(field, source.getClass());
                Method setter = getSetter(field, target.getClass());

                if (getter == null || setter == null) continue;

                setter.invoke(target, getter.invoke(source));
            }
        } catch (Exception e) {
            logger.throwing("ReflectionUtils", "mapObjects(Object source, Object target)", e);
        }
    }

    private static Method getGetter(Field field, Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods())
            if (method.getName().toLowerCase().startsWith(GET) && method.getName().toLowerCase().contains(field.getName()))
                return method;
        return null;
    }

    private static Method getSetter(Field field, Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods())
            if (method.getName().toLowerCase().startsWith(SET) && method.getName().toLowerCase().contains(field.getName()))
                return method;
        return null;
    }

    public static List<Class<?>> findClassesWithAnnotation(String path, Class<? extends Annotation> annotation) {
        List<Class<?>> allClasses = findProjectClasses(path);
        return allClasses.stream().filter(clazz -> clazz.isAnnotationPresent(annotation)).toList();
    }

    public static List<Class<?>> findProjectClasses(String path) {
        List<Class<?>> classes = new ArrayList<>();
        List<String> packagesPath = getPackagesPath(path);
        packagesPath.forEach(packagePath -> {
            File file = new File(packagePath);
            if (file.isDirectory()) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    String fullClassName = f.getPath().substring(f.getAbsolutePath().lastIndexOf("main")).substring(5);
                    fullClassName = fullClassName.replace("/", ".").replace(".class", "");
                    try {
                        classes.add(Class.forName(fullClassName));
                    } catch (Exception e) {
                        logger.throwing("ReflectionUtils", "Class<?> findProjectClasses(String path)", e);
                    }
                }
            }
        });
        return classes;
    }

    public static List<String> getPackagesPath(String path) {
        List<String> directories = new ArrayList<>();
        enterInAllDirectoriesLevels(path, directories);
        directories.add(path);

        return directories;
    }

    private static void enterInAllDirectoriesLevels(String path, List<String> directories) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (f.isDirectory()) {
                    directories.add(f.getPath());
                    String updatedPath = path + "/" + f.getName();
                    enterInAllDirectoriesLevels(updatedPath, directories);
                }
            }
        }
    }

}

