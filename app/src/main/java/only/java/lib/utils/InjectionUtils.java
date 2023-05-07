package only.java.lib.utils;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.lib.config.Initializer;
import only.java.lib.exceptions.InjectionException;
import only.java.service.AppService;
import only.java.service.impl.AppServiceImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // Constructor Injection

    public static <T> T getInstanceByConstructor(Class<T> clazz) {
        System.out.println("<><><>"+clazz.getName());
        try {
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();

            Constructor<?> onlyInjectableParametersConstructors = null;
            List<Class<?>> injectableParamsList = new ArrayList<>();
            Object instanceLevel = null;
            for (Constructor<?> constructor : declaredConstructors) {
                Integer numOfInjectableParameters = 0;
                Object instance = null;
                for (Type type : constructor.getParameterTypes()) {
                    Class<?> parameterClass = Class.forName(type.getTypeName());
                    if (parameterClass.isInterface()) {
                        parameterClass = getInterfaceImplementation(parameterClass);
                    }
                    if(parameterClass.isAnnotationPresent(Injectable.class)) {
                        System.out.println("###"+parameterClass);
                        numOfInjectableParameters++;
                        injectableParamsList.add(parameterClass);
                        instance = getInstanceByConstructor(parameterClass);
//                        System.out.println("----" + instance.getName());
                    }
                }
                if (numOfInjectableParameters.equals(constructor.getParameterCount())) {
                    onlyInjectableParametersConstructors = constructor;
                    instanceLevel = instance;
//                    System.out.println(instance.getClass().getName());
                }

//                if(Objects.nonNull(instance))
//                    System.out.println("|> " + instance.getName());
            }

//            if (Objects.nonNull(instanceLevel))
//                System.out.println("|>|>|>|>|><|" + instanceLevel.getName());

            if (injectableParamsList.size() > 1) {
                System.out.println("PARAMS");
                injectableParamsList.forEach(System.out::println);
                System.out.println("END");
                Object[] params = injectableParamsList.stream().map(InjectionUtils::inject).toArray();
                return (T) onlyInjectableParametersConstructors.newInstance(params);
            } else if (injectableParamsList.size() == 1) {
                System.out.println("PARAM");
                injectableParamsList.forEach(System.out::println);
                System.out.println("END");
                System.out.println("INSTANCE LEVER >> " + instanceLevel.getClass().getName());
                System.out.println("CONSTRUCT >> " + onlyInjectableParametersConstructors.getParameterTypes()[0]);

//                AppService appService = instanceLevel;


                return (T) onlyInjectableParametersConstructors.newInstance(instanceLevel);
            } else {
                System.out.println("else");
                return (T) onlyInjectableParametersConstructors.newInstance();
            }


        } catch (InjectionException e) {
            throw new InjectionException(e.getMessage());
        } catch (Exception e) {
            System.out.println();
            System.out.println();
            System.out.println(e);
            throw new InjectionException("Can't find an injection for class: " + clazz.getName());
        }
    }

    public static Class<?> getInterfaceImplementation(Class clazz) {
        if (clazz.isInterface()) {
            for (Class<?> instatiableClass : ReflectionUtils.getInstantiableClasses(basePath)) {
                if (clazz.isAssignableFrom(instatiableClass)) {
                    return instatiableClass;
                }
            }
        }
        return clazz;
    }


}
