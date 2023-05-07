package only.java;

import only.java.exec.MyTestExec;
import only.java.lib.annotations.Application;
import only.java.lib.config.ApplicationRunner;
import only.java.lib.config.Initializer;
import only.java.service.AppService;
import only.java.lib.utils.InjectionUtils;
import only.java.service.impl.AppServiceImpl;

@Application
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationRunner.run(Initializer.class, args);

        // Test
//        AppService appService = InjectionUtils.getInstance(AppService.class);
//        System.out.println(appService.service("Kayky"));
//        System.out.println(appService.sayHello("Kayky Freitas"));
//        System.out.println(appService.mapper());

//        AppServiceImpl appService = InjectionUtils.getInstanceByConstructor(AppServiceImpl.class);
//        System.out.println(appService.sayHello("Kayky"));

        MyTestExec executor = InjectionUtils.getInstanceByConstructor(MyTestExec.class);
        executor.exec();
    }

}
