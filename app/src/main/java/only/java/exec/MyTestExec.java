package only.java.exec;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.service.AppService;

@Injectable
public class MyTestExec {

//    @Inject
    private AppService appService;

    public MyTestExec(AppService appService) {
        this.appService = appService;
    }

    public void exec() {
        System.out.println(appService.service("Kayky"));
        System.out.println(appService.sayHello("Kayky Freitas"));
        System.out.println(appService.mapper());
    }
}
