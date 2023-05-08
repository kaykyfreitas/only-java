package only.java.lib.utils;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.lib.exceptions.SimpleInjectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimpleInjectionUtils {
    private static String basePath;

    private SimpleInjectionUtils() {}

    public static void setBasePath(String path) {
        basePath = path;
    }

    public static String getBasePath() {
        return basePath;
    }

    private static <D> D getInjectableInstance(Class<D> clazz) {
        try {
            for (Class<?>instatiableClass : SimpleReflectionUtils.getInstantiableClasses(basePath)) {
                if (instatiableClass.isAnnotationPresent(Injectable.class)) {
                    if (clazz.isInterface()) {
                        for (Class<?> clazzInterface : instatiableClass.getInterfaces()) {
                            if (clazzInterface.isAssignableFrom(clazz)) {
                                return (D) SimpleReflectionUtils.getInstance(instatiableClass);
                            }
                        }
                    } else {
                        return (D) SimpleReflectionUtils.getInstance(clazz);
                    }
                }
            }
            throw new SimpleInjectionException("Can't find an injection for class: " + clazz.getName());
        } catch (SimpleInjectionException e) {
            throw new SimpleInjectionException(e.getMessage());
        } catch (Exception e) {
            throw new SimpleInjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    public static <T> T fieldInjection(Class<T> clazz) throws Exception {
        try {
            T object = getInjectableInstance(clazz);

            Field[] declaredFields = object.getClass().getDeclaredFields();

            injectAnnotatedFields(object, declaredFields);

            return object;
        } catch (SimpleInjectionException e) {
            throw new SimpleInjectionException(e.getMessage());
        } catch (Exception e) {
            throw new SimpleInjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    private static <T> void injectAnnotatedFields(T object, Field[] declaredFields) throws Exception {
        try {
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();

                    Object innerObject = fieldInjection(type);

                    field.set(object, innerObject);
                    injectAnnotatedFields(innerObject, type.getDeclaredFields());
                }
            }
        } catch (SimpleInjectionException e) {
            throw new SimpleInjectionException(e.getMessage());
        } catch (Exception e) {
            throw new SimpleInjectionException("Can't find an injection for class: " + object.getClass().getName());
        }
    }

    // Constructor Injection

    public static <T> T constructorInjection(Class<T> clazz) {
        try {
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

            Constructor<?> onlyInjectableParametersConstructors = null;
            List<Class<?>> injectableParamsList = new ArrayList<>();
            List<Object> instanceLevel = new ArrayList<>();
            for (Constructor<?> constructor : declaredConstructors) {
                Integer numOfInjectableParameters = 0;
                Object instance = null;
                for (Type type : constructor.getParameterTypes()) {
                    Class<?> parameterClass = Class.forName(type.getTypeName());
                    if (parameterClass.isInterface()) {
                        parameterClass = getInterfaceImplementation(parameterClass);
                    }
                    if(parameterClass.isAnnotationPresent(Injectable.class)) {
                        numOfInjectableParameters++;
                        injectableParamsList.add(parameterClass);
                        instance = constructorInjection(parameterClass);
                    }
                }
                if (numOfInjectableParameters.equals(constructor.getParameterCount())) {
                    onlyInjectableParametersConstructors = constructor;
                    instanceLevel.add(instance);
                }

            }

            if (injectableParamsList.size() > 1) {
                Object[] params = injectableParamsList.stream().map(SimpleInjectionUtils::constructorInjection).toArray();
                return (T) onlyInjectableParametersConstructors.newInstance(params);
            } else if (injectableParamsList.size() == 1) {
                return (T) onlyInjectableParametersConstructors.newInstance(instanceLevel.get(0));
            } else {
                return (T) onlyInjectableParametersConstructors.newInstance();
            }


        } catch (SimpleInjectionException e) {
            throw new SimpleInjectionException(e.getMessage());
        } catch (Exception e) {
            throw new SimpleInjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    private static Class<?> getInterfaceImplementation(Class clazz) {
        if (clazz.isInterface()) {
            for (Class<?> instatiableClass : SimpleReflectionUtils.getInstantiableClasses(basePath)) {
                if (clazz.isAssignableFrom(instatiableClass)) {
                    return instatiableClass;
                }
            }
        }
        return clazz;
    }


}
