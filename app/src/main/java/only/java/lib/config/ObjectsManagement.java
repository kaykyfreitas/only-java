package only.java.lib.config;

import java.util.ArrayList;
import java.util.List;

public class ObjectsManagement {

    private static final List<Object> objects = new ArrayList<>();

    protected void addObjects(List<Object> objectsList) {
        objects.addAll(objectsList);
    }

    public static List<Object> getObjects() {
        return objects;
    }

}
