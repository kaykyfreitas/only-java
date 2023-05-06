package only.java.repository.impl;

import only.java.annotations.Injectable;
import only.java.repository.AppRepository;

@Injectable
public class AppRepositoryImpl implements AppRepository {

    @Override
    public String save(String data) {
        return "Saved: " + data;
    }

}
