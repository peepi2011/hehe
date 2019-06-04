package DAI1819.DadosExternos;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("SpringContext")
public class SpringContext implements ApplicationContextAware {

    @Autowired
    static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //store ApplicationContext reference to access required beans later on
        context = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
