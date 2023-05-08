package only.java.mapper;

import only.java.lib.annotations.Component;
import only.java.lib.annotations.Injectable;

@Injectable @Component
public class AppMapper {

    public String mapper() {
        return "Mapper!!!";
    }
}
