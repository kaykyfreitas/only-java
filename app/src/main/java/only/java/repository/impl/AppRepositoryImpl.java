package only.java.repository.impl;

import only.java.lib.annotations.Component;
import only.java.lib.annotations.Injectable;
import only.java.repository.AppRepository;

@Injectable @Component
public class AppRepositoryImpl implements AppRepository {

    @Override
    public String save(String data) {
        return "Saved: " + data;
    }

}
