package only.java.lib.utils;

import only.java.lib.exceptions.SimpleObjectMapperException;

public class SimpleObjectMapper {

    private SimpleObjectMapper() {}

    public static void copyProperties(Object source, Object target) {
        try {
            SimpleReflectionUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new SimpleObjectMapperException("Can't copy properties from " + source.getClass().getName() + " to " + target.getClass().getName() + ".");
        }
    }

    public static <D> D mapper(Object source, Class<D> targetClass) {
        try {
            return SimpleReflectionUtils.mapper(source, targetClass);
        } catch (Exception e) {
            throw new SimpleObjectMapperException("Can't map " + source.getClass().getName() + " to " + targetClass.getName() + ".");
        }
    }

}
