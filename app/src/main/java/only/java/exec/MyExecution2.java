package only.java.exec;

import only.java.repository.MyService;

public class MyExecution2 {

    private MyTestExec myTestExec;

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
