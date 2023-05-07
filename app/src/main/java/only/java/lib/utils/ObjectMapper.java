package only.java.lib.utils;

import only.java.lib.exceptions.ObjectMapperException;

public class ObjectMapper {

    private  ObjectMapper() {}

    public static void copyProperties(Object source, Object target) {
        try {
            ReflectionUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new ObjectMapperException("Can't copy properties from " + source.getClass().getName() + " to " + target.getClass().getName() + ".");
        }
    }

    public static <D> D mapper(Object source, Class<D> targetClass) {
        try {
            return ReflectionUtils.mapper(source, targetClass);
        } catch (Exception e) {
            throw new ObjectMapperException("Can't map " + source.getClass().getName() + " to " + targetClass.getName() + ".");
        }
    }

}
