package only.java.lib.context;

import only.java.lib.annotations.Component;
import only.java.lib.exceptions.SimpleException;
import only.java.lib.utils.SimpleInjectionUtils;
import only.java.lib.utils.SimpleReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleContext {

    private final String basePath;
    private final List<Object> context = new ArrayList<>();

    public SimpleContext(String basePath) {
        this.basePath = basePath;
        List<Class<?>> instantiableClasses = SimpleReflectionUtils.findClassesWithAnnotation(basePath, Component.class);

        for (Class<?> clazz : instantiableClasses) {
            context.add(SimpleInjectionUtils.constructorInjection(clazz));
        }
    }

    public String getMainPackage() {
        return this.basePath;
    }

    public List<Object> getContext() {
        return this.context;
    }

    public void addToContext(Object object) {
        this.context.add(object);
    }

    public <D> D getInstanceFromContext(Class<D> clazz) {
        return (D) context.stream().filter(obj -> obj.getClass().equals(clazz)).findFirst()
                .orElseThrow(() -> new SimpleException("Can't find an instance of " + clazz.getName() + " in the context."));
    }

}
