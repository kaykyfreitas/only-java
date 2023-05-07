package only.java.service.impl;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.repository.AppRepository;
import only.java.service.AppService;

@Injectable
public class AppServiceImpl implements AppService {

    @Inject
    private AppRepository appRepository;

    public String service(String data) {
        return appRepository.save(data);
    }

}
