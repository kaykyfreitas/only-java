package only.java.service.impl;

import only.java.annotations.Injectable;

@Injectable
public class AppServiceImpl {

    public String service(String data) {
        return "Hello " + data;
    }

}
