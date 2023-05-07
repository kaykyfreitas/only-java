package only.java.lib.utils;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.lib.exceptions.InjectionException;

import java.lang.reflect.Field;

public class InjectionUtils {
    private static String basePath;

    private InjectionUtils() {}

    public static void setBasePath(String path) {
        basePath = path;
    }

    public static <D> D inject(Class<D> clazz) {
        try {
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
            throw new InjectionException("Can't find an injection for class: " + clazz.getName());
        } catch (InjectionException e) {
            throw new InjectionException(e.getMessage());
        } catch (Exception e) {
            throw new InjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    public static <T> T getInstance(Class<T> clazz) throws Exception {
        try {
            T object = inject(clazz);

            Field[] declaredFields = object.getClass().getDeclaredFields();

            injectAnnotatedFields(object, declaredFields);

            return object;
        } catch (InjectionException e) {
            throw new InjectionException(e.getMessage());
        } catch (Exception e) {
            throw new InjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    private static <T> void injectAnnotatedFields(T object, Field[] declaredFields) throws Exception {
        try {
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();

                    Object innerObject = getInstance(type);

                    field.set(object, innerObject);
                    injectAnnotatedFields(innerObject, type.getDeclaredFields());
                }
            }
        } catch (InjectionException e) {
            throw new InjectionException(e.getMessage());
        } catch (Exception e) {
            throw new InjectionException("Can't find an injection for class: " + object.getClass().getName());
        }
    }

}
