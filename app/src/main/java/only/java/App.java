package only.java;

import only.java.annotations.Injectable;
import only.java.utils.ReflectionUtils;

public class App {

    public static void main(String[] args) {

        String path = "/home/kayky/projects/java/only-java/app/build/classes/java/main/only/java";

        var i = ReflectionUtils.getPackagesPath(path);
        i.forEach(System.out::println);
        System.out.println("Size >> " + i.size());

        System.out.println();

        var o = ReflectionUtils.findProjectClasses(path);
        o.forEach(System.out::println);
        System.out.println("Size >> " + o.size());

        System.out.println();
        System.out.println();

        var e = ReflectionUtils.findClassesWithAnnotation(path, Injectable.class);
        e.forEach(System.out::println);
        System.out.println(e.size());

    }

}
