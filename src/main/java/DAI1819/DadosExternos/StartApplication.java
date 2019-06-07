package DAI1819.DadosExternos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.DadosExternos"})
@EnableScheduling
public class StartApplication {
        public static void main(String[] args) {                      
		ApplicationContext appContext = SpringApplication.run(StartApplication.class, args);
		SpringContext.context = appContext;
	}

}
