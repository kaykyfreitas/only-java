package only.java.utils;

import only.java.annotations.Inject;
import only.java.annotations.Injectable;
import only.java.config.ObjectsManagement;
import only.java.repository.AppRepository;
import only.java.repository.impl.AppRepositoryImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

public class InjectionUtils {
    private static String basePath;

    public static void setBasePath(String path) {
        basePath = path;
    }


    public static <D> D inject(Class<D> clazz) {
        for (Class<?>instatiableClass : ReflectionUtils.getInstantiableClasses(basePath)) {
            if (instatiableClass.isAnnotationPresent(Injectable.class)) {
                if (clazz.isInterface()) {
                    for (Class<?> clazzInterface : instatiableClass.getInterfaces()) {
                        if (clazzInterface.isAssignableFrom(clazz)) {
                            return (D) ReflectionUtils.getInstance(instatiableClass);
                        }
                    }
                } else {
                    return (D) ReflectionUtils.getInstance(clazz);
                }
            }
        }
        return null;
    }

//    public static <D> D inject(Class<D> clazz) {
//        for (Object obj: ObjectsManagement.getObjects()) {
//            if (obj.getClass().isAnnotationPresent(Injectable.class)) {
//                if (clazz.isInterface()) {
//                    for (Class<?> clazzInterface : obj.getClass().getInterfaces()) {
//                        if (clazzInterface.isAssignableFrom(clazz)) {
//                            return (D) obj;
//                        }
//                    }
//                } else {
//                    if (obj.getClass().isAssignableFrom(clazz)) {
//                        return (D) obj;
//                    }
//                }
//            }
//        }
//        return null;
//    }

    public static <T> T getInstance(Class<T> clazz) throws Exception {
        T object = (T) inject(clazz);

        Field[] declaredFields = object.getClass().getDeclaredFields();

        injectAnnotatedFields(object, declaredFields);

        return object;
    }

//    private static <T> void injectAnnotatedFields(T object, Field[] declaredFields) throws Exception {
//        for (Field field : declaredFields) {
//            if (field.isAnnotationPresent(Inject.class)) {
//                System.out.println(field.getName());
//                field.setAccessible(true);
//                Class<?> type = field.getType();
//
//                System.out.println(type.getDeclaredConstructor());
//                Object innerObject = type.getDeclaredConstructor().newInstance();
//                System.out.println("-----------");
//                field.set(object, innerObject);
//                injectAnnotatedFields(innerObject, type.getDeclaredFields());
//            }
//        }
//    }

    private static <T> void injectAnnotatedFields(T object, Field[] declaredFields) throws Exception {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Class<?> type = field.getType();

                Object innerObject = getInstance(type);

                field.set(object, innerObject);
                injectAnnotatedFields(innerObject, type.getDeclaredFields());
            }
        }
    }

}
