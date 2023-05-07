package only.java;

import only.java.lib.annotations.Application;
import only.java.lib.config.ApplicationRunner;
import only.java.lib.config.Initializer;
import only.java.service.AppService;
import only.java.lib.utils.InjectionUtils;

@Application
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationRunner.run(Initializer.class, args);

        // Test
        AppService appService = InjectionUtils.getInstance(AppService.class);
        System.out.println(appService.service("Kayky"));
    }

}
