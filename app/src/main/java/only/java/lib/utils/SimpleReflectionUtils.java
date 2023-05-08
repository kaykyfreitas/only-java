package only.java.lib.utils;

import only.java.lib.exceptions.SimpleReflectionException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class SimpleReflectionUtils {

    private static final Logger logger = Logger.getLogger(SimpleReflectionUtils.class.getName());

    private static final String SET = "set";

    private static final String GET = "get";

    private SimpleReflectionUtils() {}

    public static void copyProperties(Object source, Object target) {
        try {
            mapObjects(source, target);
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils copyProperties execution");
        }
    }


    public static <D> D mapper(Object source, Class<D> targetClass) {
        try {
            D target = targetClass.getDeclaredConstructor().newInstance();

            mapObjects(source, target);
            return target;
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils mapper execution");
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
            throw new SimpleReflectionException("Error during ReflectionUtils mapObjects execution");
        }
    }

    private static Method getGetter(Field field, Class<?> clazz) {
        try {
            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().toLowerCase().startsWith(GET) && method.getName().toLowerCase().contains(field.getName()))
                    return method;
            return null;
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getGetter execution");
        }
    }

    private static Method getSetter(Field field, Class<?> clazz) {
        try {
            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().toLowerCase().startsWith(SET) && method.getName().toLowerCase().contains(field.getName()))
                    return method;
            return null;
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getSetter execution");
        }
    }

    public static List<Class<?>> findClassesWithAnnotation(String path, Class<? extends Annotation> annotation) {
        try {
            List<Class<?>> allClasses = findProjectClasses(path);
            return allClasses.stream().filter(clazz -> clazz.isAnnotationPresent(annotation)).toList();
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils findClassesWithAnnotation execution");
        }
    }

    public  static List<Class<?>> getInstantiableClasses(String path) {
        try {
            List<Class<?>> classes = findProjectClasses(path);
            List<Class<?>> instantiableClasses = new ArrayList<>();

            classes.forEach(clazz -> {
                if (!clazz.isInterface() && !clazz.isAnnotation())
                    instantiableClasses.add(clazz);
            });
            return instantiableClasses;
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getInstantiableClasses execution");
        }
    }

    public static List<Class<?>> findProjectClasses(String path) {
        try {
            List<Class<?>> classes = new ArrayList<>();
            List<String> packagesPath = getPackagesPath(path);
            packagesPath.forEach(packagePath -> {
                File file = new File(packagePath);
                if (file.isDirectory()) {
                    for (File f : Objects.requireNonNull(file.listFiles())) {
                        String fullClassName = f.getPath().substring(f.getPath().lastIndexOf("main")).substring(5);
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
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils findProjectClasses execution");
        }
    }

    public static List<String> getPackagesPath(String path) {
        try {
            List<String> directories = new ArrayList<>();
            enterInAllDirectoriesLevels(path, directories);
            directories.add(path);

            return directories;
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getPackagesPath execution");
        }
    }

    private static void enterInAllDirectoriesLevels(String path, List<String> directories) {
        try {
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
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils enterInAllDirectoriesLevels execution");
        }
    }

    public static Object getInstance(String className) {
        try {
            Class clazz = Class.forName(className);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getInstance execution");
        }
    }

    public static Object getInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new SimpleReflectionException("Error during ReflectionUtils getInstance execution");
        }
    }

    public static Class<?> getInterfaceImplementation(String path, Class clazz) {
        if (clazz.isInterface()) {
            for (Class<?> instatiableClass : getInstantiableClasses(path)) {
                if (clazz.isAssignableFrom(instatiableClass)) {
                    return instatiableClass;
                }
            }
        }
        return clazz;
    }

}

