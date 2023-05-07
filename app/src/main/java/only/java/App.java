package only.java;

import only.java.exec.MyExecution2;
import only.java.exec.MyTestExec;
import only.java.lib.annotations.Application;
import only.java.lib.config.ApplicationRunner;
import only.java.lib.config.Initializer;
import only.java.lib.utils.ObjectMapper;
import only.java.service.AppService;
import only.java.lib.utils.InjectionUtils;
import only.java.service.impl.AppServiceImpl;

// TODO trabalhar melhor a annotation @Application
@Application
public class App {

    public static void main(String[] args) throws Exception {
        // TODO pensar um forma de inicializar completamente a aplicação pelo Initializer
        ApplicationRunner.run(Initializer.class, args);


        // Para usar a injecao por campo é necessario comentar os contructors
//        AppServiceImpl appService = InjectionUtils.getInstance(AppServiceImpl.class);
//        System.out.println(appService.sayHello("Kayky"));
//
//        MyTestExec executor = InjectionUtils.getInstance(MyTestExec.class);
//        executor.exec();

//        AppServiceImpl appService = InjectionUtils.getInstanceByConstructor(AppServiceImpl.class);
//        System.out.println(appService.sayHello("Kayky"));
//
//        System.out.println();

        // Multiplos niveis de injeção apresenta problemas para metodos com mais de uma injeção
//        MyTestExec executor = InjectionUtils.getInstanceByConstructor(MyTestExec.class);
//        executor.exec();

        MyExecution2 execution2 = InjectionUtils.getInstanceByConstructor(MyExecution2.class);
        execution2.execute();
    }

}
