package only.java;

import only.java.exec.MyExecution2;
import only.java.lib.annotations.Application;
import only.java.lib.config.ApplicationRunner;
import only.java.lib.config.Initializer;
import only.java.lib.context.SimpleContext;

// TODO trabalhar melhor a annotation @Application
@Application
public class App {

    public static void main(String[] args) {
        SimpleContext context = ApplicationRunner.run(Initializer.class, args);

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

//        MyExecution2 execution1 = InjectionUtils.fieldInjection(MyExecution2.class);
//        execution1.execute();

//        System.out.println();
//
//        MyExecution2 execution2 = SimpleInjectionUtils.constructorInjection(MyExecution2.class);
//        execution2.execute();

//        SimpleContext sc = new SimpleContext(SimpleInjectionUtils.getBasePath());
//        MyExecution2 myExecution2 = sc.getInstanceFromContext(MyExecution2.class);


        MyExecution2 myExecution2 = context.getInstanceFromContext(MyExecution2.class);

        myExecution2.execute();
    }

}
