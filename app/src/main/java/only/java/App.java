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


        // Para usar a injecao por campo é necessario comentar os contructors
//        AppServiceImpl appService = InjectionUtils.getInstanceByConstructor(AppServiceImpl.class);
//        System.out.println(appService.sayHello("Kayky"));

        AppServiceImpl appService = InjectionUtils.getInstanceByConstructor(AppServiceImpl.class);
        System.out.println(appService.sayHello("Kayky"));

        // Multiplos niveis de injeção apresenta problemas
//        MyTestExec executor = InjectionUtils.getInstance(MyTestExec.class);
//        executor.exec();
    }

}
