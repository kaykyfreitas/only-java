package only.java.service.impl;

import only.java.lib.annotations.Inject;
import only.java.lib.annotations.Injectable;
import only.java.mapper.AppMapper;
import only.java.repository.AppRepository;
import only.java.repository.MyService;
import only.java.service.AppService;

@Injectable
public class AppServiceImpl implements AppService {

    private String blablabla;

    @Inject
    private AppRepository appRepository;

    @Inject
    private MyService myService;

    @Inject
    private AppMapper appMapper;

    public AppServiceImpl(String blablabla) {
        this.blablabla = blablabla;
    }

    public AppServiceImpl(AppRepository appRepository, MyService myService, AppMapper appMapper) {
        this.appRepository = appRepository;
        this.myService = myService;
        this.appMapper = appMapper;
    }

    @Override
    public String service(String data) {
        return appRepository.save(data);
    }

    @Override
    public String sayHello(String name) {
        return myService.sayHello(name);
    }

    @Override
    public String mapper() {
        return appMapper.mapper();
    }

}
