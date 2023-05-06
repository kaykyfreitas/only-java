package only.java;

import only.java.annotations.Application;
import only.java.config.ApplicationRunner;
import only.java.config.Initializer;
import only.java.service.AppService;
import only.java.utils.InjectionUtils;

@Application
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationRunner.run(Initializer.class, args);

        // Test
        AppService appService = InjectionUtils.getInstance(AppService.class);
        System.out.println(appService.service("Kayky"));
    }

}
