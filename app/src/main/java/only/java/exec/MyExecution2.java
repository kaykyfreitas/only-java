package only.java.exec;

import only.java.lib.annotations.Component;
import only.java.lib.annotations.Inject;
import only.java.repository.MyService;

@Component
public class MyExecution2 {

//    @Inject
    private MyTestExec myTestExec;

//    @Inject
    private MyService myService;

    public MyExecution2(MyTestExec myTestExec, MyService myService) {
        this.myTestExec = myTestExec;
        this.myService = myService;
    }

    public void execute() {
        System.out.println(myService.sayHello("Jurubeba"));
        myTestExec.exec();
//        myTestExec.exec();
    }
}
