package only.java.exec;

import only.java.lib.annotations.Inject;
import only.java.service.AppService;

public class MyTestExec {

    @Inject
    private AppService appService;

    public void exec() {
        System.out.println(appService.service("Kayky"));
        System.out.println(appService.sayHello("Kayky Freitas"));
        System.out.println(appService.mapper());
    }
}
