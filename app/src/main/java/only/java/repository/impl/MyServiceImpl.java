package only.java.repository.impl;

import only.java.lib.annotations.Component;
import only.java.lib.annotations.Injectable;
import only.java.repository.MyService;

@Injectable @Component
public class MyServiceImpl implements MyService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

}
